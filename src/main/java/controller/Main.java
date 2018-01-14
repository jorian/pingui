package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import model.CoinsList;
import model.ElectrumServers;

import java.io.IOException;

public class Main {

    private ElectrumServers electrumServers;
    private CoinsList coinsList;

    private Coins coinsController;
    private Trade tradeController;
    private FAQ faqController;
    private Passphrase passphraseController;
    private TradeHistory tradeHistoryController;

    @FXML BorderPane rootPane;
    @FXML public RadioButton coinsBtn;
    @FXML public RadioButton exchangeBtn;
    @FXML public RadioButton faqBtn;
    @FXML public RadioButton logoutBtn;
    @FXML public RadioButton tradeHistoryBtn;
    @FXML public ToggleGroup menuToggle;

    private Parent coins;
    private Parent trade;
    private Parent passphrase;
    private Parent faq;
    private Parent tradeHistory;

    public void initialize() {
        electrumServers = new ElectrumServers();
        coinsList = new CoinsList();

        fxmlSetup();

        menuToggle.getToggles().forEach(toggle -> {
            ((RadioButton) toggle).getStyleClass().remove("radio-button");
            ((RadioButton) toggle).getStyleClass().add("toggle-button");
            ((RadioButton) toggle).setDisable(true);
        });

        menuToggle.selectedToggleProperty().addListener((observableValue, oldToggle, newToggle) -> {
            ToggleButton tb = ((ToggleButton) menuToggle.getSelectedToggle());

            switch (tb.getId()) {
                case "coinsBtn":
                    rootPane.setCenter(coins);
                    break;
                case "exchangeBtn":
                    rootPane.setCenter(trade);
                    break;
                case "faqBtn":
                    rootPane.setCenter(faq);
                    break;
                case "logoutBtn":
                    rootPane.setCenter(passphrase);
                    break;
                case "tradeHistoryBtn":
                    rootPane.setCenter(tradeHistory);
                    break;
            }
        });


        rootPane.setCenter(passphrase);
    }

    private void fxmlSetup() {

        try {
            FXMLLoader passphraseLoader = new FXMLLoader(getClass().getResource("/fxml/scenes/passphrase.fxml"));
            passphrase = passphraseLoader.load();
            passphraseController = passphraseLoader.getController();
            passphraseController.setMain(this);

            FXMLLoader coinsLoader = new FXMLLoader(getClass().getResource("/fxml/scenes/coins.fxml"));
            coins = coinsLoader.load();
            coinsController = coinsLoader.getController();
            coinsController.setMain(this);

            FXMLLoader tradeLoader = new FXMLLoader(getClass().getResource("/fxml/scenes/trade.fxml"));
            trade = tradeLoader.load();
            tradeController = tradeLoader.getController();

            FXMLLoader faqLoader = new FXMLLoader(getClass().getResource("/fxml/scenes/faq.fxml"));
            faq = faqLoader.load();
            faqController = faqLoader.getController();

            FXMLLoader tradeHistoryLoader = new FXMLLoader(getClass().getResource("/fxml/scenes/tradeHistory.fxml"));
            tradeHistory = tradeHistoryLoader.load();
            tradeHistoryController = tradeHistoryLoader.getController();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void disableButtons() {
        menuToggle.getToggles().forEach(toggle -> {
            ((RadioButton) toggle).setDisable(false);
        });
    }

    void loadCoinsFile() {
        coinsController.loadCoinsFile();
    }

    CoinsList getCoinsList() {
        return coinsList;
    }
}
