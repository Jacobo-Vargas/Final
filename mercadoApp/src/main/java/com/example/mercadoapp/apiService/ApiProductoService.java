package com.example.mercadoapp.apiService;

import com.example.mercadoapp.dto.FacturaDTO;
import com.example.mercadoapp.dto.ProductoDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApiProductoService {

    private final OkHttpClient client;

    public ApiProductoService() {
        this.client = new OkHttpClient();
    }

    public List<ProductoDTO> obtenerProductos() throws IOException {

        ArrayList<ProductoDTO> respuesta = new ArrayList<>();
        respuesta.ensureCapacity(200);

        URL url = new URL("http://localhost:8080/obtenerProductos");
        Request request = new Request.Builder().url(url).get().build(); // crea la solicitud GET

        try(Response response = client.newCall(request).execute()){
            if(!response.isSuccessful()){
                System.out.println(response.message());
            }
            String responseBody = response.body().string();
            respuesta = new Gson().fromJson(responseBody, new TypeToken<List<ProductoDTO>>() {}.getType());
        }catch (Exception e) {
            System.out.println("Error en la petición.");
        }
        return respuesta;
    }

    public List<String> registrarProductos(File file) throws MalformedURLException {
        ArrayList<String> respuesta = new ArrayList<>();
        respuesta.ensureCapacity(500);

        URL url = new URL("http://localhost:8080/registrarProductos"); // url del endpoint


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
            if(!responseBody.isEmpty()){
                respuesta = new Gson().fromJson(responseBody, new TypeToken<List<String>>() {
                }.getType());
            }

        } catch (Exception e) {
            System.out.println("Error en la petición.");
        }
        return respuesta;
    }
}
