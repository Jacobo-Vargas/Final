package com.example.mercadouq.services;

import com.example.mercadouq.controller.*;
import com.example.mercadouq.entities.*;
import com.example.mercadouq.entities.enums.Categoria;
import com.example.mercadouq.entities.enums.Estado;
import com.example.mercadouq.entities.enums.Genero;
import com.example.mercadouq.entities.enums.TipoPais;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service

public class MercadoUtilService {

    @Autowired
    private PaisController paisController;

    @Autowired
    private ClienteController clienteController;

    @Autowired
    private DetalleFacturaController detalleFacturaController;

    @Autowired
    private ProductoController productoController;

    @Autowired
    private FacturaController facturaController;

    @Autowired
    private PremioController premioController;

    @Autowired
    private ObsequiController obsequiController;

    public List<Cliente> cargarClientesDesdeCSV(MultipartFile file) {
        List<Cliente> listaClientes = new ArrayList<>();
        String linea;
        if (file.isEmpty()) {
            return listaClientes;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(","); // Separar los datos por coma

                Long cedula = Long.valueOf(datos[0]);
                String nombre = datos[1];
                String apellido = datos[2];
                String direccion = datos[3];
                Pais pais = paisController.obtenerPais(Long.valueOf(datos[4])).getBody();
                Long telefono = Long.valueOf(datos[5]);
                int edad = Integer.parseInt(datos[6]);
                Genero genero = Genero.valueOf(datos[7]);

                Cliente cliente = new Cliente(cedula, nombre, apellido, direccion, pais, telefono, edad, genero);
                listaClientes.add(cliente);
            }
        } catch (IOException ignored) {
        }
        return listaClientes;
    }

    public List<Pais> cargarPaisesDesdeCSV(MultipartFile file) {
        List<Pais> listaPaises = new ArrayList<>();
        String linea;
        if (file.isEmpty()) {
            return listaPaises;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                Long id = Long.valueOf(datos[0]);
                String nombre = datos[1];
                TipoPais tipoPais = TipoPais.valueOf(datos[2]);

                listaPaises.add(new Pais(id, nombre, tipoPais));
            }
        } catch (IOException ignored) {
        }
        return listaPaises;
    }

    public List<Producto> cargarProductosDesdeCSV(MultipartFile file) {
        List<Producto> listaProductos = new ArrayList<>();
        String linea;
        if (file.isEmpty()) {
            return listaProductos;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                Long id = Long.valueOf(datos[0]);
                String nombre = datos[1];
                double precio = Double.parseDouble(datos[2]);
                Categoria categoria = Categoria.valueOf(datos[3]);

                listaProductos.add(new Producto(id, nombre, precio, categoria));

            }
        } catch (IOException ignored) {
        }
        return listaProductos;
    }

    public List<Factura> cargarFacturasDesdeCSV(MultipartFile file) {
        List<Factura> listaFacturas = new ArrayList<>();
        String linea;
        if (file.isEmpty()) {
            return null;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                Long id = Long.valueOf(datos[0]);
                Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(datos[1]);
                Cliente cliente = (Cliente) clienteController.obtenerById(Long.parseLong(datos[2])).getBody();

                List<DetalleFactura> listaDetalles = detalleFacturaController.findDetallesFacturasByIdFactura(id);


                if (listaDetalles != null) {
                    Factura factura = new Factura(id, fecha, cliente);
                    factura.setTotal(factura.calcularTotalFactura(listaDetalles));
                    listaFacturas.add(factura);
                } else {
                    Factura factura = new Factura(id, fecha, cliente);
                    factura.setTotal(factura.calcularTotalFactura(null));
                    listaFacturas.add(factura);
                }

            }
        } catch (Exception ignored) {
        }
        return listaFacturas;
    }

    public List<DetalleFactura> cargarDetallesFacturaDesdeCsv(MultipartFile file) throws IOException {
        List<DetalleFactura> lista = new ArrayList<>();
        String linea;
        if (file.isEmpty()) {
            return lista;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                Long id = Long.valueOf(datos[0]);
                Long idFactura = Long.valueOf(datos[1]);
                Producto p = (Producto) productoController.obtenerProductoById(Long.valueOf(datos[2])).getBody();
                int cantidad = Integer.parseInt(datos[3]);

                DetalleFactura detalle = new DetalleFactura(id, idFactura, Objects.requireNonNull(p), cantidad);
                lista.add(detalle);
            }
        }
        return lista;
    }

    private void actualizarValorFacturaIndividual(List<DetalleFactura> detallesFactura, Factura factura) {
        double precio = factura.calcularTotalFactura(detallesFactura);
        factura.setTotal(precio);
        facturaController.actualizarPrecioFacturaById(factura);
    }

    public void actualizarValorTotalFacturas() {
        List<Factura> facturas = facturaController.obtenerFacturas();

        for (Factura factura : facturas) {
            List<DetalleFactura> detalles = detalleFacturaController.findDetallesFacturasByIdFactura(factura.getId());
            actualizarValorFacturaIndividual(detalles, factura);
        }
    }

    /**
     * Se llama desde un endpoint se escogen premiados, se asigna premio y cuando están se encolan
     */
    public void escogerPremiados() {
        List<Factura> listaDeFacturas = facturaController.getFactOrderByClient();
        for (Factura factura : listaDeFacturas) {
            if (cumpleCondiciones(factura)) {
                Premio premio = new Premio(factura, escogerObsequio(factura.getId()));
                premio.setEstado(Estado.ESPERA);
                premioController.registrarPremio(premio);
            }
        }
        encolarPremios();
    }

    private Obsequio escogerObsequio(Long idFactura) {

        /* Obtengo las facturas asociadas al cliente de la factura actual*/
        List<Factura> facturas = facturaController.obtenerFacturasByIdClient(facturaController.obtenerFacturaById(idFactura).getCliente().getCedula());
        /* De las facturas obtenidas se extraen las que sean menores a un año y se ordenan de forma tal que las más recientes quedan primero*/
        List<Factura> tenidasEnCuenta = facturas.stream().filter(factura -> compararFecha(factura.getFecha())).sorted(Comparator.comparing(Factura::getFecha).reversed()).toList();
        /* Se obtiene el número de las facturas a tener en cuenta para revisar el premio*/
        int facturasARevisar = (int) (tenidasEnCuenta.size() * 0.1);

        int tecnologia = 0;
        int cosmeticos = 0;
        int electrodomesticos = 0;

        /*Se recorre la cantidad de facturas a tener en cuenta*/
        for (int i = 0; i < facturasARevisar ; i++) {
            /* Se obtiene los detalles de la factura para hacer el conteo de cuál fue el producto que más compró*/
            List<DetalleFactura> listaDetalles = detalleFacturaController.findDetallesFacturasByIdFactura(tenidasEnCuenta.get(0).getId());

            for(DetalleFactura detalle: listaDetalles){
                if(detalle.getProducto().getCategoria().equals(Categoria.TECNOLOGIA)){
                    tecnologia++;
                } else if (detalle.getProducto().getCategoria().equals(Categoria.COSMETICOS)){
                    cosmeticos++;
                } else if (detalle.getProducto().getCategoria().equals(Categoria.ELECTRODOMESTICOS)){
                    electrodomesticos++;
                }
            }

            if(tecnologia > cosmeticos && tecnologia > electrodomesticos){

                /*Tomar obsequio de la pila*/
                Queue<Obsequio> pilaTecnologia = obsequiController.getObsequiosByCategoria(Categoria.TECNOLOGIA);
                Obsequio obsequio = pilaTecnologia.peek();
                obsequiController.eliminarObsequio(obsequio.getId());
                return pilaTecnologia.poll();
            }


        }
        return null;
    }

    private boolean cumpleCondiciones(Factura factura) {
        boolean fechaValida = comprobarFecha(factura.getFecha());
        boolean precioMayor = factura.getTotal() > 1000000;
        boolean categoria = comprobarCategoria(factura.getId());
        return fechaValida && precioMayor && categoria;
    }

    private boolean comprobarFecha(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
        return diaSemana != Calendar.WEDNESDAY && diaSemana != Calendar.MONDAY;
    }

    private boolean comprobarCategoria(Long idFactura) {
        List<DetalleFactura> lista = detalleFacturaController.findDetallesFacturasByIdFactura(idFactura);
        for (DetalleFactura detalle : lista) {
            if (detalle.getProducto().getCategoria().equals(Categoria.ALIMENTOS)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Este método debería devolver un informe con los datos del orden de despacho o al final cuando se carguen al avión
     * cambiar estado de premio a ENCOLADO y posteriormente a ENVIADO cuando se despache
     */
    private void encolarPremios() {

    }

    /**
     * @return true si la fecha pasada por parámetro es de hace más de un año
     */
    public boolean compararFecha(Date date){
       LocalDate beforeYear = LocalDate.now().minusYears(1);
       Date fechaNueva = Date.from(beforeYear.atStartOfDay(ZoneId.systemDefault()).toInstant());
       return date.before(fechaNueva);
    }
}