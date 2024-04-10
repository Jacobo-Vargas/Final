package com.example.mercadouq;

import com.example.mercadouq.controller.ProductoController;
import com.example.mercadouq.entities.Producto;
import org.hibernate.annotations.Check;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestProducto {
    @Autowired
    private ProductoController productoController;

    @Test
    void registrarProductos(){

//        try {
//            InputStream inputStream = new FileInputStream("src/main/resources/fileCsv/productos.csv");
//            MockMultipartFile file = new MockMultipartFile("file", "productos.csv", "text/csv", inputStream);
//            ResponseEntity<?> response = productoController.registrarProductos(file);
//
//            // Verificar que la respuesta no es nula
//            assertNotNull(response, "La respuesta no debería ser nula");
//
//            // Verificar el código de estado de la respuesta (por ejemplo, 200 para éxito)
//            assertEquals(HttpStatus.OK, response.getStatusCode(), "El código de estado debería ser OK");
//
//            Object responseBody = response.getBody();
//            if(responseBody instanceof ArrayList<?>){
//
//                @SuppressWarnings("unchecked")
//                List<String> requestBody = (ArrayList<String>) response.getBody();
//                System.out.println(requestBody);
//            }
//
//        } catch (FileNotFoundException e) {
//            System.out.println("No se encontró el archivo.");
//        } catch (IOException e) {
//            System.out.println("No se pudo convertir a Multipart.");
//        }
    }
    @Test
    void registrarConArchivoVacio(){
//        try {
//            InputStream inputStream = new FileInputStream("src/main/resources/fileCsv/productosVacio.csv");
//            MockMultipartFile file = new MockMultipartFile("file", "productos.csv", "text/csv", inputStream);
//            ResponseEntity<?> response = productoController.registrarProductos(file);
//
//            // Verificar el código de estado de la respuesta (por ejemplo, 200 para éxito)
//            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "El código de estado debería ser BAD_REQUEST");
//            System.out.println(response.getBody());
//
//
//        } catch (FileNotFoundException e) {
//            System.out.println("No se encontró el archivo.");
//        } catch (IOException e) {
//            System.out.println("No se pudo convertir a Multipart.");
//        }
    }
    @Test
    void obtenerProducto(){
        ResponseEntity<?> response = productoController.obtenerProducto("Arroz");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Resultado Correcto" );
        System.out.println(response.getBody());
    }

    @Test
    void obtenerProductoNoExiste(){
        ResponseEntity<?> response = productoController.obtenerProducto("Nada");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "No existe." );
        System.out.println(response.getBody());
    }

}
