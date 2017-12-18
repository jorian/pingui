package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;



public class Coins {

    @FXML private GridPane contentPane;
    @FXML private TableView orderbookTable;

    public Coins () {

    }


    public void initialize() {
        AnchorPane.setTopAnchor(contentPane,0.0);
        AnchorPane.setBottomAnchor(contentPane,0.0);
        AnchorPane.setLeftAnchor(contentPane,0.0);
        AnchorPane.setRightAnchor(contentPane,0.0);

        orderbookTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

}
