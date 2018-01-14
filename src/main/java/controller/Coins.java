package controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import model.CoinsList;
import utils.BarterRPC;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import static utils.BarterRPC.barterRPC;

public class Coins {

    public Button disableCoin;
    @FXML ToggleGroup toggleGroup;
    @FXML GridPane contentPane;
    @FXML ComboBox<String> comboBox;
    @FXML ListView<String> activeCoinsListView;
    @FXML ImageView qrcodeview;

    private ObservableList<String> observableList;
    private ObservableList<String> comboBoxCoinsListener;
    private Main mainController;


    public void setMain(Main main) { this.mainController = main; }

    public void loadCoinsFile() {
        if (mainController != null) {
            for (CoinsList.Coin coin : mainController.getCoinsList().getCoins()) {
                comboBoxCoinsListener.add(coin.getCoin());
            }
            comboBox.getItems().addAll(comboBoxCoinsListener);
            Collections.sort(comboBox.getItems());
        }
    }

    public void initialize() {
        observableList = FXCollections.observableArrayList();
        comboBoxCoinsListener = FXCollections.observableArrayList();

        comboBox.setEditable(true);

        activeCoinsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newvalue) -> {
            try {
                qrcodeview.setPreserveRatio(true);
                qrcodeview.setImage(createQRImage(newvalue));
            } catch (WriterException e) {
                e.printStackTrace();
            }
        });

        barterRPC = new BarterRPC();
    }

    public void enableCoin(ActionEvent actionEvent) {

        String comboBoxValue = comboBox.getValue();
        try {
            JsonObject jsonObject = new JsonParser().parse(new String(Files.readAllBytes(Paths.get("src/main/resources/assets/electrum.json")))).getAsJsonObject();

            JsonArray ipList = jsonObject.getAsJsonArray(comboBoxValue);
            ArrayList<String> coinIPList = new ArrayList<>();

            for (JsonElement j : ipList) {

                Set<String> ip = j.getAsJsonObject().keySet();
                coinIPList.addAll(ip);
            }

            activeCoinsListView.setItems(observableList);
            comboBox.getItems().remove(comboBox.getValue());

            // Select Electrum button as default
            toggleGroup.selectToggle(toggleGroup.getToggles().get(0));

            String response = barterRPC.enableElectrum(comboBoxValue, coinIPList.get(0),ipList.get(0).getAsJsonObject().get(coinIPList.get(0)).getAsString());

            observableList.add(comboBoxValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disableCoin(ActionEvent actionEvent) {
        if (activeCoinsListView.getSelectionModel().getSelectedItem() != null) {
            System.out.println("is niet null");
            comboBox.getItems().add(activeCoinsListView.getSelectionModel().getSelectedItem());
            Collections.sort(comboBox.getItems());
            activeCoinsListView.getItems().remove(activeCoinsListView.getSelectionModel().getSelectedItem());
        }
    }

    private WritableImage createQRImage(String address) throws WriterException {
        MatrixToImageConfig imageConfig = new MatrixToImageConfig(MatrixToImageConfig.BLACK,0x009E9E9E);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(new QRCodeWriter().encode(address,BarcodeFormat.QR_CODE,230,230),imageConfig);
        return SwingFXUtils.toFXImage(bufferedImage,null);
    }
}



