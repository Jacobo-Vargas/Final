package com.example.mercadouq;

import com.example.mercadouq.controller.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MercadoUqApplicationTests {

	@Autowired
	private PaisController paisController;
	@Autowired
	private ClienteController clienteController;
	@Autowired
	private FacturaController facturaController;
	@Autowired
	DetalleFacturaController detalleFacturaController;

	@Autowired
	private ProductoController productoController;

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
	@Test
	void registrarProductos(){

		try {
			InputStream inputStream = new FileInputStream("src/main/resources/fileCsv/productos.csv");
			MockMultipartFile file = new MockMultipartFile("file", "productos.csv", "text/csv", inputStream);
			ResponseEntity<?> response = productoController.registrarProductos(file);

			// Verificar que la respuesta no es nula
			assertNotNull(response, "La respuesta no debería ser nula");

			// Verificar el código de estado de la respuesta (por ejemplo, 200 para éxito)
			assertEquals(HttpStatus.OK, response.getStatusCode(), "El código de estado debería ser OK");

			Object responseBody = response.getBody();
			if(responseBody instanceof ArrayList<?>){

				@SuppressWarnings("unchecked")
				List<String> requestBody = (ArrayList<String>) response.getBody();
				System.out.println(requestBody);
			}

		} catch (FileNotFoundException e) {
			System.out.println("No se encontró el archivo.");
		} catch (IOException e) {
			System.out.println("No se pudo convertir a Multipart.");
		}
	}
	@Test
	void registrarClientes(){
		try{

			InputStream inputStream = new FileInputStream("src/main/resources/fileCsv/clientes.csv");
			MockMultipartFile file = new MockMultipartFile("file", "clientes.csv", "text/csv", inputStream);
			ResponseEntity<?> response = clienteController.registrarClientes(file);

			assertEquals(HttpStatus.OK, response.getStatusCode(), Objects.requireNonNull(response.getBody()).toString());


		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	@Test
	void registrarFactura(){

		try{

			InputStream inputStream = new FileInputStream("src/main/resources/fileCsv/facturas.csv");
			MockMultipartFile file = new MockMultipartFile("file", "facturas.csv", "text/csv", inputStream);
			ResponseEntity<?> response = facturaController.registrarFacturas(file);

			assertEquals(HttpStatus.OK, response.getStatusCode(), Objects.requireNonNull(response.getBody()).toString());


		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	@Test
	void cargarDetalles(){
		try{
			InputStream inputStream = new FileInputStream("src/main/resources/fileCsv/detallesFactura.csv");
			MockMultipartFile file = new MockMultipartFile("file", "detallesFactura.csv","text/csv", inputStream);

			ResponseEntity<?> response = detalleFacturaController.registrarDetallesFacturas(file);
			assertEquals(HttpStatus.OK, response.getStatusCode(), Objects.requireNonNull(response.getBody()).toString());

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


}
