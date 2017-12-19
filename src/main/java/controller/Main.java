package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import utils.ContentController;
import utils.StageManager;

import java.io.IOException;

public class Main {

    @FXML private AnchorPane contentPane;

    @FXML private ToggleButton coinsBtn;
    @FXML private ToggleButton tradeBtn;
    @FXML private ToggleButton faqBtn;
    @FXML private ToggleGroup menu;

    public void initialize() {
        StageManager.setPane(contentPane);
    }

    public void click() {
        System.out.println("Test");
    }

    public void changeScreen(Event e) throws IOException {
        Control control = ((Control)e.getTarget());
        String id = control.getId();

        System.out.println(control.getId());
        System.out.println(ContentController.getPane());

//        if (ContentController.getPane())

        switch (id) {
            case "coinsBtn":
                if (!ContentController.getPane().equals(ContentController.Pane.COINS))
                    ContentController.setPane(ContentController.Pane.COINS);
                coinsBtn.setSelected(true);
                break;
            case "tradeBtn": {
                if (!ContentController.getPane().equals(ContentController.Pane.TRADE))
                    ContentController.setPane(ContentController.Pane.TRADE);
                tradeBtn.setSelected(true);
                break;
            }
        }
    }

    public void toCoinsScreen() {

    }
}
