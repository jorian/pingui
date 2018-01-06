package controller;

import com.google.gson.Gson;
import com.sun.javafx.scene.control.skin.TableHeaderRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import utils.BarterRPC;

import java.io.IOException;
import java.util.Arrays;


public class Trade {
    private BarterRPC barterRPC;

    @FXML private GridPane contentPane;
    @FXML private TableView<Orders.Asks> orderbookAsks;
    @FXML private TableView<Orders.Bids> orderbookBids;
    @FXML private Button getOrderbookBtn;
    @FXML private TableColumn avgaskvolume;
    @FXML private TableColumn avgbidvolume;
    @FXML private TextField relCoin;
    @FXML private TextField baseCoin;
    @FXML private TextField buyAmount;
    @FXML private TextField buyPrice;


    public Trade() throws Exception {
        barterRPC = new BarterRPC();
    }


    public void initialize() {
        AnchorPane.setTopAnchor(contentPane, 0.0);
        AnchorPane.setBottomAnchor(contentPane, 0.0);
        AnchorPane.setLeftAnchor(contentPane, 0.0);
        AnchorPane.setRightAnchor(contentPane, 0.0);

        orderbookAsks.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        orderbookBids.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        orderbookBids.setSelectionModel(null);
        orderbookAsks.setSelectionModel(null);


        orderbookBids.widthProperty().addListener((source, oldWidth, newWidth) -> {
            TableHeaderRow header = (TableHeaderRow) orderbookBids.lookup("TableHeaderRow");
            header.reorderingProperty().addListener((observable, oldValue, newValue) -> header.setReordering(false));
        });

        orderbookAsks.widthProperty().addListener((source, oldWidth, newWidth) -> {
            TableHeaderRow header = (TableHeaderRow) orderbookAsks.lookup("TableHeaderRow");
            header.reorderingProperty().addListener((observable, oldValue, newValue) -> header.setReordering(false));
        });


        // DEBUG
        baseCoin.appendText("CRYPTO");
        relCoin.appendText("KMD");
    }

    public void getOrderbook(Event e) throws Exception {
        String response = barterRPC.orderbook(baseCoin.getText(),relCoin.getText());

        Orders orders = new Gson().fromJson(response, Orders.class);

        System.out.println(orders.asks.length);
        System.out.println(orders.bids.length);

        ObservableList<Orders.Asks> asksList = FXCollections.observableList(Arrays.asList(orders.asks));
        ObservableList<Orders.Bids> bidsList = FXCollections.observableList(Arrays.asList(orders.bids));

        orderbookAsks.getItems().clear();
        orderbookAsks.getItems().addAll(asksList);

        orderbookBids.getItems().clear();
        orderbookBids.getItems().addAll(bidsList);

        avgaskvolume.setText("Avg volume (" + orders.bids[0].coin + ")");
        avgbidvolume.setText("Avg volume (" + orders.asks[0].coin + ")");
    }

    public static class Orders {
        Bids[] bids;
        Asks[] asks;


        public static class Bids {
            private String coin;
            private String address;
            private double price;
            private int numutxos;
            private double avevolume;
            private double maxvolume;
            private double depth;
            private String pubkey;
            private int age;
            private double zcredits;

            public String getCoin() {
                return coin;
            }

            public String getAddress() {
                return address;
            }

            public double getPrice() {
                return price;
            }

            public int getNumutxos() {
                return numutxos;
            }

            public double getAvevolume() {
                return avevolume;
            }

            public double getMaxvolume() {
                return maxvolume;
            }

            public double getDepth() {
                return depth;
            }

            public String getPubkey() {
                return pubkey;
            }

            public int getAge() {
                return age;
            }

            public double getZcredits() {
                return zcredits;
            }
        }

        public static class Asks {
            private String coin;
            private String address;
            private double price;
            private int numutxos;
            private double avevolume;
            private double maxvolume;
            private double depth;
            private String pubkey;
            private int age;
            private double zcredits;

            public String getCoin() {
                return coin;
            }

            public String getAddress() {
                return address;
            }

            public double getPrice() {
                return price;
            }

            public int getNumutxos() {
                return numutxos;
            }

            public double getAvevolume() {
                return avevolume;
            }

            public double getMaxvolume() {
                return maxvolume;
            }

            public double getDepth() {
                return depth;
            }

            public String getPubkey() {
                return pubkey;
            }

            public int getAge() {
                return age;
            }

            public double getZcredits() {
                return zcredits;
            }
        }
    }

    public void buyBtn(Event e) throws IOException {
        String response = barterRPC.buy(baseCoin.getText(),relCoin.getText(), Double.valueOf(buyAmount.getText()), Double.valueOf(buyPrice.getText()));
    }

    public static class BuyResponse {
        private String result;


        public static class Swaps {
            private double requestID;
            private double quoteID;

        }

        public static class NetAmounts {}

        public static class Pending {}
    }

}
