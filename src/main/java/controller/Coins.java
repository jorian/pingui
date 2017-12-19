package controller;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;


public class Coins {

    @FXML private GridPane contentPane;
    @FXML private TableView<Orders.Asks> orderbookTable;
    @FXML private Button getOrderbookBtn;

    public Coins () {

    }


    public void initialize() {
        AnchorPane.setTopAnchor(contentPane,0.0);
        AnchorPane.setBottomAnchor(contentPane,0.0);
        AnchorPane.setLeftAnchor(contentPane,0.0);
        AnchorPane.setRightAnchor(contentPane,0.0);

        orderbookTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void getOrderbook(Event e) throws Exception {
        System.out.println(e.getEventType());

        String url = "http://127.0.0.1:7783";

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","application/json");

        String postJSONData = "{\"userpass\":\"fe63ebd14198df8207f3c8bf20a644cf4407afb961c5520610bbb02e2cde060a\",\"method\":\"orderbook\",\"base\":\"CHIPS\",\"rel\":\"KMD\"}";

        connection.setDoOutput(true);
        DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());

        dataOutputStream.writeBytes(postJSONData);
        dataOutputStream.flush();
        dataOutputStream.close();

        System.out.println(connection.getResponseCode());

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        );
        String output;
        StringBuilder response = new StringBuilder();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }

        in.close();

        System.out.println(response.toString());

//        JsonElement gsonElement = new JsonParser().parse(response.toString());
//        JsonObject jsonObject = gsonElement.getAsJsonObject();

        Orders orders = new Gson().fromJson(response.toString(), Orders.class);

        System.out.println(orders.asks.length);
        System.out.println(orders.bids.length);

        ObservableList<Orders.Asks> asksList = FXCollections.observableList(Arrays.asList(orders.asks));

        orderbookTable.getItems().addAll(asksList);
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

}
