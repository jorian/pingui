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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;

import java.awt.image.BufferedImage;
import java.util.Collections;

public class Coins {

    public Button disableCoin;
    @FXML GridPane contentPane;
    @FXML ComboBox<String> comboBox;
    @FXML ListView<String> activeCoinsListView;
    @FXML ImageView qrcodeview;

    private ObservableList<String> observableList;

    public void initialize() {
        observableList = FXCollections.observableArrayList();
        Collections.sort(comboBox.getItems());

        activeCoinsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newvalue) -> {
            try {
                qrcodeview.setPreserveRatio(true);
                qrcodeview.setImage(createQRImage(newvalue));
            } catch (WriterException e) {
                e.printStackTrace();
            }
        });
    }

    public void enableCoin(ActionEvent actionEvent) {

        String string = comboBox.getValue();
        observableList.add(string);

        activeCoinsListView.setItems(observableList);

        comboBox.getItems().remove(comboBox.getValue());

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



