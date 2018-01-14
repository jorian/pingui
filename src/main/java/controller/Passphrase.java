package controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.CoinsList;
import utils.SessionStorage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static utils.BarterRPC.barterRPC;

public class Passphrase {

    public static Process process;
    public Main mainController;

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
    }

    public void setMain(Main main) {
        mainController = main;
    }

    public void login() throws Exception {

        SessionStorage sessionStorage = new SessionStorage();

        if (passphraseField.getText().isEmpty()) {
            passphraseField.setPromptText("No passphrase entered! Enter passphrase here");
            passphraseField.setStyle("-fx-prompt-text-fill: #d7d5d2");
        } else {
            sessionStorage.setPassphrase(passphraseField.getText());

            // TODO
            Task taskMarketMaker = new Task() {
                @Override
                protected Object call() throws Exception {
                    startMarketmaker();
                    return null;
                }
            };

            new Thread(taskMarketMaker).start();

//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main/main.fxml"));
//            Parent root = loader.load();

            mainController.menuToggle.selectToggle(mainController.coinsBtn);
            mainController.disableButtons();


        }

    }

    private void startMarketmaker() throws Exception {
        String passphrase = SessionStorage.getPassphrase();

        // Contents of coins file:
        String coinsFileContent = new String(Files.readAllBytes(Paths.get("/home/n41r0j/pingui/src/main/resources/assets/coins.json")));
//        System.out.println(coinsFileContent);

        String params = "{\"gui\":\"pingui\"," +
                "\"client\":1," +
                "\"userhome\":\"" + System.getenv("HOME") + "\"," +
                "\"passphrase\":\""+ passphrase +"\"," +
                "\"coins\":" + coinsFileContent + "}\r\n";

//        System.out.println("complete params: " + params);

        // TODO
//         Starts marketmaker process:
        process = new ProcessBuilder("/home/n41r0j/pingui/src/main/resources/assets/linux64/marketmaker", params)
                .inheritIO()
                .start();//        System.out.println(process.waitFor());
//
//        BufferedReader bri = new BufferedReader(new InputStreamReader(process.getInputStream()));
//
//        String line;
//        while ((line = bri.readLine()) != null) {
//            System.out.println(line);
//            line = "";
//        }

        // TODO
        TimeUnit.SECONDS.sleep(2);

        String postJSONData = "{" +
                "\"userpass\":\"userpass\"," +
                "\"method\":\"getcoin\"," +
                "\"coin\":\"KMD\"" +
                "}";
        String response = barterRPC.postRequest(postJSONData);

        if (!response.equals("") && !response.contains("error")) {
            JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
            if (jsonObject == null) {
                System.out.println("json is NULL");
            } else {
                String _userpass = jsonObject.get("userpass").getAsString();
                JsonArray coinsArray = jsonObject.getAsJsonArray("coins");
                coinsArray.forEach(jsonElement -> {
                    Gson gson = new Gson();
                    CoinsList coinsList = mainController.getCoinsList();
                    CoinsList.Coin coin = gson.fromJson(jsonElement, CoinsList.Coin.class);
                    coinsList.addToCoinsList(coin);

                    System.out.println(coin.toString());
                });
                mainController.loadCoinsFile();
                barterRPC.setUserpass(_userpass);
            }
        }
    }
}
