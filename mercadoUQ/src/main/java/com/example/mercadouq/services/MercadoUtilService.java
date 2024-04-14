package com.example.mercadouq.services;

import com.example.mercadouq.controller.*;
import com.example.mercadouq.entities.*;
import com.example.mercadouq.entities.enums.Categoria;
import com.example.mercadouq.entities.enums.Estado;
import com.example.mercadouq.entities.enums.Genero;
import com.example.mercadouq.entities.enums.TipoPais;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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
    private ObsequioController obsequioController;

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
        for (int i = 0; i < facturasARevisar; i++) {
            /* Se obtiene los detalles de la factura para hacer el conteo de cuál fue el producto que más compró*/
            List<DetalleFactura> listaDetalles = detalleFacturaController.findDetallesFacturasByIdFactura(tenidasEnCuenta.get(i).getId());

            for (DetalleFactura detalle : listaDetalles) {
                if (detalle.getProducto().getCategoria().equals(Categoria.TECNOLOGIA)) {
                    tecnologia++;
                } else if (detalle.getProducto().getCategoria().equals(Categoria.COSMETICOS)) {
                    cosmeticos++;
                } else if (detalle.getProducto().getCategoria().equals(Categoria.ELECTRODOMESTICOS)) {
                    electrodomesticos++;
                }
            }
            if (tecnologia > cosmeticos && tecnologia > electrodomesticos) {
                /*Tomar obsequio de la pila*/
                Queue<Obsequio> pilaTecnologia = obsequioController.getObsequiosByCategoria(Categoria.TECNOLOGIA);
                Obsequio obsequio = pilaTecnologia.peek();
                obsequioController.eliminarObsequio(obsequio.getId());
                return pilaTecnologia.poll();
            } else if (electrodomesticos > tecnologia && electrodomesticos > cosmeticos) {
                /*Tomar obsequio de la pila*/
                Queue<Obsequio> pilaElectrodomesticos = obsequioController.getObsequiosByCategoria(Categoria.ELECTRODOMESTICOS);
                Obsequio obsequio = pilaElectrodomesticos.peek();
                obsequioController.eliminarObsequio(obsequio.getId());
                return pilaElectrodomesticos.poll();
            } else {
                /*Tomar obsequio de la pila*/
                Queue<Obsequio> pilaCosmeticos = obsequioController.getObsequiosByCategoria(Categoria.COSMETICOS);
                Obsequio obsequio = pilaCosmeticos.peek();
                obsequioController.eliminarObsequio(obsequio.getId());
                return pilaCosmeticos.poll();
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
     * @return true si la fecha pasada por parámetro es de hace más de un año
     */
    private boolean compararFecha(Date date) {
        LocalDate beforeYear = LocalDate.now().minusYears(1);
        Date fechaNueva = Date.from(beforeYear.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date.before(fechaNueva);
    }

    /**
     * Se llama desde un endpoint se escogen premiados, se asigna premio se registran en la BD
     * y se cambia estado en 'ESPERA'
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
    }

    /**
     * Este método devuelve la lista de los premios encolados y cambia el estado en la BD
     * ha 'ENCOLADO'
     */
    @SuppressWarnings("unchecked")
    private Queue<Premio> encolarPremios() {
        /*Para encolar los premios y asi simular el envío vamos a traer los registros de los premios de la base de datos*/
        List<Premio> premiados = (List<Premio>) premioController.getPremioByEstado(Estado.ESPERA).getBody();
        Queue<Premio> ordenPremiados = new PriorityQueue<>(new OrdenadorPremiosService());

        if(premiados != null){
            for (Premio p: premiados) {
                p.setEstado(Estado.ENCOLADO);
                /*Se actualiza el estado del premio en la BD*/
                premioController.actualizarEstadoEncolado(p);
                ordenPremiados.add(p);
            }
        }
        return ordenPremiados;
    }

    /**
     * Este método devuelve la lista de premios asignados en el avion, se cambia
     * estado en la BD ha 'ENVIADO'
     */
    @SuppressWarnings("unchecked")
    public List<Premio> enviarPremios(int cantidadAviones, int premiosPorAvion){
        Queue<Premio> encoladosAEnviar = encolarPremios();
        ArrayList<Premio> resultado = new ArrayList<>();

        int numeroAvion = 1;
        int premiosEnAvion = 0;
        boolean avionesDisponibles = true;
        while(avionesDisponibles){

            Premio p =  encoladosAEnviar.peek();
            if(!resultado.contains(p)){
                Premio premio = encoladosAEnviar.poll();
                premio.setNumeroAvion(numeroAvion);
                premio.setEstado(Estado.ENVIADO);
                resultado.add(premio);
                // aquí se actualiza en la BD ha estado 'enviado'
                premioController.actualizarEstadoEncolado(premio);
                premiosEnAvion++;
            }

            if(premiosEnAvion == premiosPorAvion){
                numeroAvion++;
            }
            if(numeroAvion > cantidadAviones){
                avionesDisponibles = false;
            }
        }
        return resultado;
    }
}