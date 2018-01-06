package controller;

import com.google.gson.*;
import com.sun.javafx.scene.control.skin.TableHeaderRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import utils.BarterRPC;

import java.io.IOException;
import java.util.Arrays;


public class Trade {
    private BarterRPC barterRPC;
    private Pending pendingSwap;

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
    @FXML private Label buyTotal;


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

        buyTotal.setDisable(true);


        buyAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals("") && !buyPrice.getText().equals("")) {
                double dBuyPrice = Double.valueOf(buyPrice.getText());
                buyTotal.setText(String.valueOf(Double.valueOf(newValue) * dBuyPrice));
            }
        });

        buyPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals("") && !buyAmount.getText().equals("")) {
                double dBuyAmount = Double.valueOf(buyAmount.getText());
                buyTotal.setText(String.valueOf(Double.valueOf(newValue) * dBuyAmount));
            }
        });


        // DEBUG
        baseCoin.appendText("CRYPTO");
        relCoin.appendText("KMD");
    }

    public void getOrderbook(Event e) throws Exception {
        String response = barterRPC.orderbook(baseCoin.getText(),relCoin.getText());

        // If something goes wrong with the request, the response will be empty.
        if (!response.equals("")) {
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

        // If something goes wrong with the request, the response will be empty.
        if (!response.equals("")) {
            JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
            if (jsonObject == null) {
                System.out.println("json is NULL");
            } else {
                if (jsonObject.get("result").toString().equals("success")) {
                    JsonObject jsonPending = jsonObject.getAsJsonObject("pending");

                    pendingSwap = new Gson().fromJson(jsonPending, Pending.class);

                    System.out.println("Expiration: " + pendingSwap.expiration);
                }
            }
        }


    }

    public static class Pending {
        private long expiration;
        private int timeleft;
        private long tradeid;
        private long requestid;
        private long quoteid;
        private String bob;
        private String base;
        private double basevalue;
        private String alice;
        private String rel;
        private double relvalue;
        private long aliceid;


        public long getExpiration() {
            return expiration;
        }

        public void setExpiration(long expiration) {
            this.expiration = expiration;
        }

        public int getTimeleft() {
            return timeleft;
        }

        public void setTimeleft(int timeleft) {
            this.timeleft = timeleft;
        }

        public long getTradeid() {
            return tradeid;
        }

        public void setTradeid(long tradeid) {
            this.tradeid = tradeid;
        }

        public long getRequestid() {
            return requestid;
        }

        public void setRequestid(long requestid) {
            this.requestid = requestid;
        }

        public long getQuoteid() {
            return quoteid;
        }

        public void setQuoteid(long quoteid) {
            this.quoteid = quoteid;
        }

        public String getBob() {
            return bob;
        }

        public void setBob(String bob) {
            this.bob = bob;
        }

        public String getBase() {
            return base;
        }

        public void setBase(String base) {
            this.base = base;
        }

        public double getBasevalue() {
            return basevalue;
        }

        public void setBasevalue(double basevalue) {
            this.basevalue = basevalue;
        }

        public String getAlice() {
            return alice;
        }

        public void setAlice(String alice) {
            this.alice = alice;
        }

        public String getRel() {
            return rel;
        }

        public void setRel(String rel) {
            this.rel = rel;
        }

        public double getRelvalue() {
            return relvalue;
        }

        public void setRelvalue(double relvalue) {
            this.relvalue = relvalue;
        }

        public long getAliceid() {
            return aliceid;
        }

        public void setAliceid(long aliceid) {
            this.aliceid = aliceid;
        }
    }

    public static class BuyResponse {
        private String result;


        public static class Swaps {

            public static class Swap {
                private double requestID;
                private double quoteID;
            }
        }

        public static class NetAmounts {}

        public static class Pending {}
    }

}
