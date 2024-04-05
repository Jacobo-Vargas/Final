package com.example.mercadouq.services;

import com.example.mercadouq.controller.PaisController;
import com.example.mercadouq.entities.Cliente;
import com.example.mercadouq.entities.Producto;
import com.example.mercadouq.entities.enums.Categoria;
import com.example.mercadouq.entities.enums.Genero;
import com.example.mercadouq.entities.Pais;
import com.example.mercadouq.entities.enums.TipoPais;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class MercadoUtilService {

    @Autowired
    private PaisController paisController;

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

    public List<Pais> cargarPaisesDesdeCSV(MultipartFile file) throws Exception{
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

    public List<Producto> cargarProductosDesdeCSV(MultipartFile file) throws Exception{
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
}
