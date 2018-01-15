package controller;

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
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import model.CoinsList;
import model.ElectrumServers;
import utils.BarterRPC;

import java.awt.image.BufferedImage;
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

    private ElectrumServers electrumServers;
    private Set<String> coinsWithElectrumServers;

    private Main mainController;


    public void setMain(Main main) { this.mainController = main; }

    /*
    We have to load this coins file later on in the process, because of a still unknown NPE on start up. maincontroller
    is null, for some reason. Therefore, the coins from the response are loaded in startMarketMaker method in Passphrase
     */
    public void loadCoinsFile() {
        if (mainController != null) {
            for (CoinsList.Coin coin : mainController.getCoinsList().getCoins()) {
                comboBoxCoinsListener.add(coin.getCoin());
            }
            comboBox.getItems().addAll(comboBoxCoinsListener);
            Collections.sort(comboBox.getItems());
        }
    }

    public void loadElectrumEnabledCoins() {

        electrumServers = mainController.getElectrumServers();
        coinsWithElectrumServers = electrumServers.getCoinsWithElectrumServers();
    /*
     Check if Electrum is possible on selected coin and disable togglebutton if not:
     - disable Electrum radio button
     - set Native selected
     - set Electrum selected if Electrum is possible (Electrum should always be default)

     NOTE: we can only check if Native is installed upon enabling the coin in native. That will return
     a response and show us whether the coin is Natively installed.
      */
        comboBox.getSelectionModel().selectedItemProperty().addListener(observable -> {
            String selectedValue = comboBox.getSelectionModel().getSelectedItem();
            if (selectedValue != null && !selectedValue.isEmpty()) {
                ((ToggleButton) toggleGroup.getToggles().get(0)).setDisable(!coinsWithElectrumServers.contains(selectedValue));
                toggleGroup.getToggles().get(1).setSelected(!coinsWithElectrumServers.contains(selectedValue));
                toggleGroup.getToggles().get(0).setSelected(coinsWithElectrumServers.contains(selectedValue));
            } else {
                ((ToggleButton) toggleGroup.getToggles().get(0)).setDisable(false);
                toggleGroup.getToggles().get(0).setSelected(true);
            }
        });
    }

    public void initialize() {
        observableList = FXCollections.observableArrayList();
        comboBoxCoinsListener = FXCollections.observableArrayList();

        comboBox.setEditable(true);

        // Create QR code:
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

        String selectedValue = comboBox.getValue();
        ArrayList<ElectrumServers.Electrum> electrums = electrumServers.getServers().get(selectedValue);

        //TODO
        String response = barterRPC.enableElectrum(selectedValue, electrums.get(0).getIpaddr(),electrums.get(0).getPort());

        if (!response.contains("error")) {
            activeCoinsListView.setItems(observableList);
            comboBox.getItems().remove(comboBox.getValue());

            observableList.add(selectedValue);
            comboBox.valueProperty().set(null);
        } else {
            System.err.println("Something went wrong: " + response);
        }
    }

    public void disableCoin(ActionEvent actionEvent) {
        String selectedValue = activeCoinsListView.getSelectionModel().getSelectedItem();
        if (selectedValue != null) {
            System.out.println("a coin is selected from active coins listview");

            //put selected coin back in combobox and sort combobox
            comboBox.getItems().add(activeCoinsListView.getSelectionModel().getSelectedItem());
            Collections.sort(comboBox.getItems());

            // disable electrum:
            // send empty ip address for selected coin to disable.
            // port is still needed (probably to determine which electrum to enable.
            // TODO what to do when different ports are used? remember which Electrum is active.
            System.out.println(barterRPC.enableElectrum(selectedValue, "", electrumServers.getServers().get(selectedValue).get(0).getPort()));

            //remove the coin from the active coins listview:
            activeCoinsListView.getItems().remove(activeCoinsListView.getSelectionModel().getSelectedItem());
        }
    }

    private WritableImage createQRImage(String address) throws WriterException {
        if (address == null || address.isEmpty())
            return null;
        MatrixToImageConfig imageConfig = new MatrixToImageConfig(MatrixToImageConfig.BLACK,0x009E9E9E);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(new QRCodeWriter().encode(address,BarcodeFormat.QR_CODE,230,230),imageConfig);
        return SwingFXUtils.toFXImage(bufferedImage,null);
    }
}



