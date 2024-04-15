package com.example.mercadoapp.apiService;

import com.example.mercadoapp.dto.ClienteDTO;
import com.example.mercadoapp.dto.DetalleFacturaDTO;
import com.example.mercadoapp.dto.FacturaDTO;
import com.example.mercadoapp.dto.PaisDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApiDetalleFacturaService {

    private final OkHttpClient client;

    public ApiDetalleFacturaService() {
        this.client = new OkHttpClient();
    }

    public List<DetalleFacturaDTO> obtenerDetalleFaturas() throws IOException {

        ArrayList<DetalleFacturaDTO> respuesta = new ArrayList<>();
        respuesta.ensureCapacity(200);

        URL url = new URL("http://localhost:8080/obtenerDetalleFacturas");
        Request request = new Request.Builder().url(url).get().build(); // crea la solicitud POST

        try(Response response = client.newCall(request).execute()){
            if(!response.isSuccessful()){
                System.out.println(response.message());
            }
            String responseBody = response.body().string();
            respuesta = new Gson().fromJson(responseBody, new TypeToken<List<DetalleFacturaDTO>>() {}.getType());
        }catch (Exception e) {
            System.out.println("Error en la petición.");
        }
        return respuesta;
    }

    public List<String> registrarDetalleFacturas(File file) throws MalformedURLException {
        ArrayList<String> respuesta = new ArrayList<>();
        respuesta.ensureCapacity(500);

        URL url = new URL("http://localhost:8080/registrarDetallesFacturas"); // url del endpoint


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
