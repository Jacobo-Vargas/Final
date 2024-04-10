package com.example.mercadouq;

import com.example.mercadouq.controller.PaisController;
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
public class TestPaises {

    @Autowired
    private PaisController paisController;

    @Test
    void registrarPaises(){

        try{

            InputStream inputStream = new FileInputStream("src/main/resources/fileCsv/paises.csv");
            MockMultipartFile file = new MockMultipartFile("file", "paises.csv", "text/csv", inputStream);
            ResponseEntity<?> response = paisController.registrarPaises(file);

            assertEquals(HttpStatus.OK, response.getStatusCode(), Objects.requireNonNull(response.getBody()).toString());


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
