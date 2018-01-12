package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Program extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main/main.fxml"));
        primaryStage.setTitle("Pingui");
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(450);

        Scene scene = new Scene(root,1200,650);

        primaryStage.setOnCloseRequest(windowEvent -> {
            try {
                Runtime.getRuntime().exec("pkill -15 marketmaker");
                System.out.println("marketmaker killed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        scene.getStylesheets().add("css/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
