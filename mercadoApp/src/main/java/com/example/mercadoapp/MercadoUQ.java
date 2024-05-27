package com.example.mercadoapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class MercadoUQ extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MercadoUQ.class.getResource("principal.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("MercadoUQ");
        stage.setScene(scene);

        // Agregar el archivo CSS a la escena
        scene.getStylesheets().add( MercadoUQ.class.getResource("css/styles.css").toExternalForm());
      //  stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
      //  Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/image-removebg-preview.png")));
     //   stage.getIcons().add(icon);+
        stage.show();
    }
}
