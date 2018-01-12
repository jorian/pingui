package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

import java.util.Collections;

public class Coins {

    public Button disableCoin;
    @FXML GridPane contentPane;
    @FXML ComboBox<String> comboBox;
    @FXML ListView<String> activeCoinsListView;
    @FXML
    ImageView qrcodeview;

    private ObservableList<String> observableList;

    public void initialize() {
        observableList = FXCollections.observableArrayList();
        Collections.sort(comboBox.getItems());

        activeCoinsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
        });



        Image image = new Image(QRCode.from("1AwDWu5rZKyGMUu16gf9Kow8ohnKmc7tGH").to(ImageType.PNG).withSize(230,230).file().toURI().toString());
        qrcodeview.setImage(image);
        qrcodeview.setPreserveRatio(true);
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
}



