package com.example.mercadoapp.service;

import com.example.mercadoapp.dto.ClienteDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;

public class ApiServiceCliente {

    private final OkHttpClient client;

    public ApiServiceCliente() {
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

    public List<Long> registrarClientes(List<ClienteDTO> lista) throws MalformedURLException {

        ArrayList<Long> respuesta = new ArrayList<>();
        respuesta.ensureCapacity(500);

        URL url = new URL("http://localhost:8080/registrarClientes"); // url del endpoint
        String mensaje = new Gson().toJson(lista); // mensaje que se le envía al endpoint
        RequestBody body = RequestBody.create(mensaje, MediaType.parse("application/json")); // crea el cuerpo de la solicitud
        Request request = new Request.Builder().url(url).post(body).build(); // crea la solicitud POST

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
        
    }


    private String setCredentials() {
        String username = "admin";
        String password = "admin";
        String authString = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(authString.getBytes());
    }


}
