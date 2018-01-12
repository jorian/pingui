package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Control;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import utils.ContentController;

import java.io.IOException;

public class Main {

    @FXML private BorderPane contentPane;

    @FXML public ToggleButton coinsBtn;
    @FXML public ToggleButton exchangeBtn;
    @FXML public ToggleButton faqBtn;
    @FXML public ToggleButton logoutBtn;
    @FXML public ToggleButton tradeHistoryBtn;
    @FXML private ToggleGroup menu;


    private Coins coinsController = new Coins();

    public void initialize() {


        loadScreen(ContentController.Pane.COINS.getResourceLocation(),coinsController);

//        disableButtons(true);



    }

    public void disableButtons(boolean bool) {
        coinsBtn.setDisable(bool);
        exchangeBtn.setDisable(bool);
        faqBtn.setDisable(bool);
        logoutBtn.setDisable(bool);
        tradeHistoryBtn.setDisable(bool);
    }

    public void changeScreen(Event e) throws IOException {
        Control control = ((Control)e.getTarget());
        String id = control.getId();

        System.out.println(control.getId());
        System.out.println(ContentController.getPane());

        switch (id) {
            case "coinsBtn":
                if (!ContentController.getPane().equals(ContentController.Pane.COINS)) {
                    //ContentController.setPane(ContentController.Pane.COINS);
                    loadScreen(ContentController.Pane.COINS.getResourceLocation(),coinsController);

                    coinsBtn.setSelected(true);
                }
                break;
            case "exchangeBtn":
                if (!ContentController.getPane().equals(ContentController.Pane.EXCHANGE))
                    ContentController.setPane(ContentController.Pane.EXCHANGE);
                exchangeBtn.setSelected(true);
                break;
            case "faqBtn" :
                if (!ContentController.getPane().equals(ContentController.Pane.FAQ))
                    ContentController.setPane(ContentController.Pane.FAQ);
                faqBtn.setSelected(true);
                break;
            case "logoutBtn" :
                logoutBtn.setSelected(true);
                ContentController.setPane(ContentController.Pane.PASSPHRASE);
                break;
            case "tradeHistoryBtn" :
            if (!ContentController.getPane().equals(ContentController.Pane.TRADEHISTORY))
                ContentController.setPane(ContentController.Pane.TRADEHISTORY);
            faqBtn.setSelected(true);
            break;
        }
    }

    private void loadScreen(String resource, Object controller) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
            loader.setController(controller);
            contentPane.setCenter(loader.load());

        } catch (IOException exc) {
            exc.printStackTrace();
            contentPane.setCenter(null);
        }
    }
}
