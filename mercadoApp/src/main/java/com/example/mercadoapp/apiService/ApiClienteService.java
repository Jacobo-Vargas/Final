package com.example.mercadoapp.apiService;

import com.example.mercadoapp.dto.ClienteDTO;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;

public class ApiClienteService {

    private final OkHttpClient client;

    public ApiClienteService() {
        this.client = new OkHttpClient();
    }

    public List<ClienteDTO> obtenerClientes() throws IOException {
        ArrayList<ClienteDTO> respuesta = new ArrayList<>();
        respuesta.ensureCapacity(500);

        URL url = new URL("http://localhost:8080/obtenerClientes");
        Request request = new Request.Builder().url(url).get().build(); // crea la solicitud POST

        try(Response response = client.newCall(request).execute()){
            if(!response.isSuccessful()){
                System.out.println(response.message());
            }
            String responseBody = response.body().string();
            respuesta = new Gson().fromJson(responseBody, new TypeToken<List<ClienteDTO>>() {}.getType());
        }catch (Exception e) {
            System.out.println("Error en la petición.");
        }
        return respuesta;
    }

    public List<Long> registrarClientes(File file) throws MalformedURLException {
        ArrayList<Long> respuesta = new ArrayList<>();
        respuesta.ensureCapacity(500);

        URL url = new URL("http://localhost:8080/registrarClientes"); // url del endpoint


        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(file, MediaType.parse("text/csv")))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build(); // crea la solicitud POST

        // ejecutar la solicitud y obtener la respuesta
        try (Response response = client.newCall(request).execute()) {
            // verificar si la solicitud fue exitosa
            if (!response.isSuccessful()) {
                System.out.println(response.message());
                return respuesta;
            }
            String responseBody = response.body().string();
            respuesta = new Gson().fromJson(responseBody, new TypeToken<List<Long>>() {
            }.getType());
        } catch (Exception e) {
            System.out.println("Error en la petición.");
        }
        return respuesta;
    }

    public String actualizarClientes(List<ClienteDTO> lista){
        return null;
    }

    public String eliminarById(Long id) throws MalformedURLException {
        URL url = new URL("http://localhost:8080/eliminarCliente/" + id); // url del endpoint
        Request request = new Request.Builder().url(url).get().build();
        try(Response response = client.newCall(request).execute()){
            return response.body().string();
        }catch (Exception e) {
            return "Error en el servidor";
        }
    }

    public ClienteDTO obtenerById(Long id) throws MalformedURLException {
        ClienteDTO resultado = null;
        URL url = new URL("http://localhost:8080/eliminarCliente/" + id); // url del endpoint
        Request request = new Request.Builder().url(url).get().build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                resultado = new Gson().fromJson(response.body().string(), new TypeToken<ClienteDTO>() {}.getType());
            } else {
                System.err.println("Error al obtener el cliente. Código de estado: " + response.code() + response.body());
            }
        } catch (IOException e) {
            System.err.println("Error de E/S al obtener el cliente: " + e.getMessage());
        }
        return resultado;
    }


    private String setCredentials() {
        String username = "admin";
        String password = "admin";
        String authString = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(authString.getBytes());
    }


}
