package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import utils.ContentController;
import utils.SessionStorage;
import utils.StageManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Passphrase {

    public static Process process;

    @FXML private GridPane contentPane;
    @FXML private TextField passphraseField;

    public void initialize() throws IOException {

        AnchorPane.setRightAnchor(contentPane,0.0);
        AnchorPane.setLeftAnchor(contentPane,0.0);
        AnchorPane.setTopAnchor(contentPane,0.0);
        AnchorPane.setBottomAnchor(contentPane,0.0);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main/main.fxml"));
        Parent root = loader.load();
        Main main = loader.getController();

        main.disableButtons(true);

        StageManager.setRoot(root);
    }

    public void login(Event e) throws IOException, InterruptedException {

        SessionStorage sessionStorage = new SessionStorage();

        if (passphraseField.getText().isEmpty()) {
            passphraseField.setPromptText("No passphrase entered! Enter passphrase here");
            passphraseField.setStyle("-fx-prompt-text-fill: #b9322f");
        } else {

            sessionStorage.setPassphrase(passphraseField.getText());

            startMarketmaker();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main/main.fxml"));
            Parent root = loader.load();
            Main main = loader.getController();

            main.disableButtons(false);
            ContentController.setPane(ContentController.Pane.TRADE);

            StageManager.setRoot(root);
        }

    }

    private void startMarketmaker() throws IOException, InterruptedException {
        String pass = SessionStorage.getPassphrase();
        process = new ProcessBuilder("/assets/marketmaker",
                "{\"gui\":\"pingui\",\"client\":1, \"userhome\":\"" + System.getenv("HOME") + "/\", \"passphrase\":\"" + SessionStorage.getPassphrase() + "\", \"coins\":assets/coins.json}").start();
//        System.out.println(process.waitFor());

        BufferedReader bri = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = bri.readLine()) != null) {
            System.out.println(line);
        }

        System.out.println(System.getenv("HOME"));
    }
}
