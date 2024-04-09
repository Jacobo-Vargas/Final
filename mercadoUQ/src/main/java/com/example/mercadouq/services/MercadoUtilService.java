package com.example.mercadouq.services;

import com.example.mercadouq.controller.ClienteController;
import com.example.mercadouq.controller.DetalleFacturaController;
import com.example.mercadouq.controller.PaisController;
import com.example.mercadouq.controller.ProductoController;
import com.example.mercadouq.entities.*;
import com.example.mercadouq.entities.enums.Categoria;
import com.example.mercadouq.entities.enums.Genero;
import com.example.mercadouq.entities.enums.TipoPais;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@SuppressWarnings("unchecked")
public class MercadoUtilService {

    @Autowired
    private PaisController paisController;

    @Autowired
    private ClienteController clienteController;

    @Autowired
    private DetalleFacturaController detalleFacturaController;

    @Autowired
    private ProductoController productoController;

    public List<Cliente> cargarClientesDesdeCSV(MultipartFile file) {
        List<Cliente> listaClientes = new ArrayList<>();
        String linea;
        if(file.isEmpty()){
            return listaClientes;
        }
        try( BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))){
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(","); // Separar los datos por coma

                Long cedula = Long.valueOf(datos[0]);
                String nombre = datos[1];
                String apellido = datos[2];
                String direccion = datos[3];
                Pais pais =  paisController.obtenerPais(Long.valueOf(datos[4])).getBody();
                Long telefono = Long.valueOf(datos[5]);
                int edad = Integer.parseInt(datos[6]);
                Genero genero = Genero.valueOf(datos[7]);

                Cliente cliente = new Cliente(cedula,nombre,apellido,direccion,pais,telefono,edad,genero);
                listaClientes.add(cliente);
            }
        } catch (IOException ignored) {
        }
        return listaClientes;
    }

    public List<Pais> cargarPaisesDesdeCSV(MultipartFile file){
        List<Pais> listaPaises = new ArrayList<>();
        String linea;
        if(file.isEmpty()){
            return listaPaises;
        }
        try(BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))){
            while((linea = br.readLine()) != null){
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

    public List<Producto> cargarProductosDesdeCSV(MultipartFile file){
        List<Producto> listaProductos = new ArrayList<>();
        String linea;
        if(file.isEmpty()){
            return listaProductos;
        }
        try(BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))){
            while((linea = br.readLine()) != null){
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

    public List<Factura> cargarFacturasDesdeCSV(MultipartFile file){
        List<Factura> listaFacturas = new ArrayList<>();
        String linea;
        if(file.isEmpty()){
            return null;
        }
        try(BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))){
            while((linea = br.readLine()) != null){
                String[] datos = linea.split(",");

                Long id = Long.valueOf(datos[0]);
                Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(datos[1]);
                Cliente cliente = (Cliente)  clienteController.obtenerById(Long.parseLong(datos[2])).getBody();

                List<DetalleFactura> listaDetalles = (List<DetalleFactura>) detalleFacturaController.findFacturasById(id).getBody();

                Factura factura = new Factura(id,fecha,cliente,listaDetalles);
                listaFacturas.add(factura);
            }
        } catch (Exception ignored) {
        }
        return listaFacturas;
    }

    public List<DetalleFactura> cargarDetallesFacturaDesdeCsv(MultipartFile file) throws IOException {
        List<DetalleFactura> lista = new ArrayList<>();
        String linea ;
        if(file.isEmpty()){
            return null;
        }
        try(BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))){
            while ((linea = br.readLine()) != null){
                String[] datos = linea.split(",");

                Long id = Long.valueOf(datos[0]);
                Long idFactura = Long.valueOf(datos[1]);
                Producto p = (Producto) productoController.obtenerProductoById(Long.valueOf(datos[2])).getBody();
                int cantidad = Integer.parseInt(datos[3]);

                DetalleFactura detalle = new DetalleFactura(id, idFactura, p, cantidad );
                lista.add(detalle);
            }
        }
        return lista;
    }
}
