package com.example.mercadouq.services;

import com.example.mercadouq.entities.Cliente;
import com.example.mercadouq.entities.enums.Genero;
import com.example.mercadouq.entities.Pais;
import com.example.mercadouq.entities.enums.TipoPais;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class MercadoUtilService {

    public static List<Cliente> loadClientesDesdeCSV(MultipartFile archivo) throws Exception {


        String linea;

        List<Cliente> listaClientes = new ArrayList<>();

        if(archivo.isEmpty()){
            return listaClientes;
        }
        try( BufferedReader br = new BufferedReader(new InputStreamReader(archivo.getInputStream()))){

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(","); // Separar los datos por coma

                // Crear un nuevo cliente con los datos del CSV y guardarlo en la lista

                Long cedula = Long.valueOf(datos[0]);
                String nombre = datos[1];
                String apellido = datos[2];
                String direccion = datos[3];

                String[] paisStr = datos[4].split("-");
                Pais pais = new Pais();
                pais.setNombre(paisStr[0]);
                pais.setTipoPais(TipoPais.valueOf(paisStr[1]));

                Long telefono = Long.valueOf(datos[5]);
                int edad = Integer.parseInt(datos[6]);
                Genero genero = Genero.valueOf(datos[7]);

                Cliente cliente = new Cliente(cedula,nombre,apellido,direccion,pais,telefono,edad,genero);

                listaClientes.add(cliente);

            }

        } catch (IOException e) {
            throw new Exception("No se pudo leer el archivo.");
        }

        return listaClientes;
    }
}
