package controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import utils.ContentController;
import utils.SessionStorage;
import utils.StageManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static utils.BarterRPC.barterRPC;

public class Passphrase {

    public static Process process;

    @FXML private GridPane contentPane;
    @FXML private TextField passphraseField;
    @FXML private Label loadingLabel;

    public void initialize() throws IOException {
        AnchorPane.setRightAnchor(contentPane,0.0);
        AnchorPane.setLeftAnchor(contentPane,0.0);
        AnchorPane.setTopAnchor(contentPane,0.0);
        AnchorPane.setBottomAnchor(contentPane,0.0);

        passphraseField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                try {
                    login();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main/main.fxml"));
        Parent root = loader.load();
        Main main = loader.getController();

        main.disableButtons(true);

        StageManager.setRoot(root);
    }

    public void login() throws Exception {

        loadingLabel.setManaged(true);

        SessionStorage sessionStorage = new SessionStorage();

        if (passphraseField.getText().isEmpty()) {
            passphraseField.setPromptText("No passphrase entered! Enter passphrase here");
            passphraseField.setStyle("-fx-prompt-text-fill: #d7d5d2");
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

    private void startMarketmaker() throws Exception {
        String passphrase = SessionStorage.getPassphrase();

        // Contents of coins file:
        String coinsFileContent = new String(Files.readAllBytes(Paths.get("/home/n41r0j/pingui/src/main/resources/assets/coins.json")));
        System.out.println(coinsFileContent);

        String params = "{\"gui\":\"pingui\"," +
                "\"client\":1," +
                "\"userhome\":\"" + System.getenv("HOME") + "\"," +
                "\"passphrase\":\""+ passphrase +"\"," +
                "\"coins\":" + coinsFileContent + "}\r\n";

        System.out.println("complete params: " + params);

        // Starts marketmaker process:
        process = new ProcessBuilder("/home/n41r0j/pingui/src/main/resources/assets/linux64/marketmaker", params)
                .inheritIO().start();//        System.out.println(process.waitFor());

        BufferedReader bri = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = bri.readLine()) != null) {
            System.out.println(line);
            line = "";
        }

        TimeUnit.SECONDS.sleep(5);

        String postJSONData = "{" +
                "\"userpass\":\"userpass\"," +
                "\"method\":\"orderbook\"," +
                "\"coin\":\"KMD\"" +
                "}";
        String response = barterRPC.postRequest(postJSONData);

        if (!response.equals("") && !response.contains("error")) {
            JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
            if (jsonObject == null) {
                System.out.println("json is NULL");
            } else {
                String _userpass = jsonObject.get("userpass").getAsString();
                System.out.println(_userpass);
                barterRPC.setUserpass(_userpass);
            }
        }
    }
}
