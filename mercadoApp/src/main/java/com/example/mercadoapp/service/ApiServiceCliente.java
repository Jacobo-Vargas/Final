package com.example.mercadoapp.service;
import com.example.mercadoapp.dto.ClienteDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ApiServiceCliente {

    public List<ClienteDTO> obtenerClientes() throws IOException {
        URL url = new URL("http://localhost:8080/obtenerClientes");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", setCredentials());
        String datos = getDataRequest(con);
        List<ClienteDTO> clientesDTO = new Gson().fromJson(datos, new TypeToken<List<ClienteDTO>>() {}.getType());
        con.disconnect();
        return clientesDTO;
    }

    public List<Long> registrarClientes(List<ClienteDTO> lista) throws IOException {
        URL url = new URL("http://localhost:8080/registrarClientes");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", setCredentials());
        con.setDoOutput(true);

        // Convertir la lista de ClienteDTO a JSON y enviarla al servidor
        try (OutputStream outputStream = con.getOutputStream()) {
            Gson gson = new Gson();
            String jsonInputString = gson.toJson(lista);
            outputStream.write(jsonInputString.getBytes());
        }

        // Leer la respuesta del servidor
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        String datos = response.toString();

        // Convertir la respuesta JSON a una lista de Long
        List<Long> clientesDTO = new Gson().fromJson(datos, new TypeToken<List<Long>>() {}.getType());

        con.disconnect();
        return clientesDTO;
    }


    private String setCredentials() throws IOException {
        String username = "admin";
        String password = "admin";
        String authString = username + ":" + password;
        return  "Basic " + Base64.getEncoder().encodeToString(authString.getBytes());
    }

    private String getDataRequest(HttpURLConnection con){

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.toString();
    }
}
