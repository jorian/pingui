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

import java.io.IOException;

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

    public void login(Event e) throws IOException {

        SessionStorage sessionStorage = new SessionStorage();

        if (passphraseField.getText().isEmpty()) {
            passphraseField.setPromptText("No passphrase entered! Enter passphrase here");
            passphraseField.setStyle("-fx-prompt-text-fill: #97433c");
        } else {

            sessionStorage.setPassphrase(passphraseField.getText());

//        startMarketmaker();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main/main.fxml"));
            Parent root = loader.load();
            Main main = loader.getController();

            main.disableButtons(false);
            ContentController.setPane(ContentController.Pane.TRADE);

            StageManager.setRoot(root);
        }

    }

    private void startMarketmaker() throws IOException {
        String pass = SessionStorage.getPassphrase();
        process = new ProcessBuilder("/home/n41r0j/pingui/src/main/resources/assets/marketmaker",
                "{\"gui\":\"nogui\",\"client\":1, \"userhome\":\"/${HOME#/}\", \"passphrase\":\"" + SessionStorage.getPassphrase() + "\", \"coins\":$coins}").inheritIO().start();

    }
}
