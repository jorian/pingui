package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class Main {

    @FXML BorderPane rootPane;

    private Coins coinsController;
    private Trade tradeController;

    @FXML public RadioButton coinsBtn;
    @FXML public RadioButton exchangeBtn;
    @FXML public RadioButton faqBtn;
    @FXML public RadioButton logoutBtn;
    @FXML public RadioButton tradeHistoryBtn;
    @FXML public ToggleGroup menuToggle;

    private Parent coins;
    private Parent trade;

    public void initialize() {
        fxmlSetup();

        menuToggle.getToggles().forEach(toggle -> {
            ((RadioButton) toggle).getStyleClass().remove("radio-button");
            ((RadioButton) toggle).getStyleClass().add("toggle-button");
        });

        menuToggle.selectedToggleProperty().addListener((observableValue, oldToggle, newToggle) -> {
            ToggleButton tb = ((ToggleButton) menuToggle.getSelectedToggle());

            switch (tb.getId()) {
                case "coinsBtn":
                    //changeScreen("/main/java/resources/fxml/coins.fxml", coinsController);
                    rootPane.setCenter(coins);
                    break;
                case "exchangeBtn":
                    //changeScreen("/main/java/resources/fxml/trade.fxml", tradeController);
                    rootPane.setCenter(trade);
                    break;
            }
        });

    }

    private void fxmlSetup() {

        try {
            FXMLLoader coinsLoader = new FXMLLoader(getClass().getResource("/main/java/resources/fxml/coins.fxml"));
            coins = coinsLoader.load();
            coinsController = coinsLoader.getController();

            FXMLLoader tradeLoader = new FXMLLoader(getClass().getResource("/main/java/resources/fxml/trade.fxml"));
            trade = tradeLoader.load();
            tradeController = tradeLoader.getController();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
