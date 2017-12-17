package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.GridPane;

public class Main {

    @FXML private GridPane contentPane;

    @FXML private Button coinsBtn;
    @FXML private Button tradeBtn;
    @FXML private Button faqBtn;

    public void click() {
        System.out.println("Test");
    }

    public void changeScreen(Button b) {

        switch (b.getId()) {
            case "faqBtn" :
                contentPane =
        }

    }

    public void toCoinsScreen() {

    }
}
