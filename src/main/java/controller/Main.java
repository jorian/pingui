package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;
import utils.ContentController;
import utils.StageManager;

import java.io.IOException;

public class Main {

    @FXML private AnchorPane contentPane;

    @FXML private Button coinsBtn;
    @FXML private Button tradeBtn;
    @FXML private Button faqBtn;

    public void initialize() {
        StageManager.setPane(contentPane);
    }

    public void click() {
        System.out.println("Test");
    }

    public void changeScreen(Event e) throws IOException {
        String id = ((Control)e.getTarget()).getId();
        switch (id) {
            case "coinsBtn":
                ContentController.setPane(ContentController.Pane.COINS);
                break;
            case "tradeBtn": {
                ContentController.setPane(ContentController.Pane.TRADE);
            }
        }
    }

    public void toCoinsScreen() {

    }
}
