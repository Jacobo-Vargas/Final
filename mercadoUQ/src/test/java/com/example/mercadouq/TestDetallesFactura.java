package com.example.mercadouq;

import com.example.mercadouq.controller.DetalleFacturaController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestDetallesFactura {

    @Autowired
    DetalleFacturaController detalleFacturaController;

    @Test
    void cargarDetalles(){
//        try{
//            InputStream inputStream = new FileInputStream("src/main/resources/fileCsv/detallesFactura.csv");
//            MockMultipartFile file = new MockMultipartFile("file", "detallesFactura.csv","text/csv", inputStream);
//
//            ResponseEntity<?> response = detalleFacturaController.registrarDetallesFacturas(file);
//            assertEquals(HttpStatus.OK, response.getStatusCode(), Objects.requireNonNull(response.getBody()).toString());
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
    @Test
    void cargarDetallesError(){
        try{
            InputStream inputStream = new FileInputStream("src/main/resources/fileCsv/detallesFacturaVacio.csv");
            MockMultipartFile file = new MockMultipartFile("file", "detallesFacturaVacio.csv","text/csv", inputStream);

            ResponseEntity<?> response = detalleFacturaController.registrarDetallesFacturas(file);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), Objects.requireNonNull(response.getBody()).toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
