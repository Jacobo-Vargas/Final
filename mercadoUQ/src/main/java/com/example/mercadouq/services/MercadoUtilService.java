package com.example.mercadouq.services;

import com.example.mercadouq.controller.ClienteController;
import com.example.mercadouq.controller.DetalleFacturaController;
import com.example.mercadouq.controller.PaisController;
import com.example.mercadouq.entities.*;
import com.example.mercadouq.entities.enums.Categoria;
import com.example.mercadouq.entities.enums.Genero;
import com.example.mercadouq.entities.enums.TipoPais;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

@Service
public class MercadoUtilService {

    @Autowired
    private PaisController paisController;

    @Autowired
    private ClienteController clienteController;

    @Autowired
    private DetalleFacturaController detalleFacturaController;

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
        } catch (IOException e) {
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
        } catch (IOException e) {
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
        } catch (IOException e) {
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
                Date fecha = new SimpleDateFormat("dd-MM-yyyy").parse(datos[1]);
                Cliente cliente = (Cliente)  clienteController.obtenerById(Long.parseLong(datos[2])).getBody();
                List<DetalleFactura> listaDetalles = (List<DetalleFactura>) detalleFacturaController.findFacturasById(id).getBody();

                Factura factura = new Factura(id,fecha,cliente,listaDetalles);
                listaFacturas.add(factura);
            }
        } catch (Exception e) {
        }
        return listaFacturas;
    }
}
