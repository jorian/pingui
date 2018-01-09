package utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.prefs.Preferences;

public class BarterRPC {
    private URL url;
    private HttpURLConnection connection;
    private String userpass = "";

    public static BarterRPC barterRPC;
    private Preferences prefs;

    public BarterRPC() throws Exception {
        url = new URL("http://127.0.0.1:7783");
        prefs = Preferences.userRoot();
    }

    public String orderbook(String base, String rel) throws IOException {

        String postJSONData = "{" +
                "\"userpass\":\""+prefs.get("userpass", "")+"\"," +
                "\"method\":\"orderbook\"," +
                "\"base\":\"" + base + "\"," +
                "\"rel\":\"" + rel + "\"" +
                "}";

        System.out.println("Method call: " + postJSONData);

        return postRequest(postJSONData);
    }

    public String getCoin(String coin) {
        String postJSONData = "{" +
                "\"userpass\":\""+prefs.get("userpass", "")+"\"," +
                "\"method\":\"getcoin\"," +
                "\"coin\":\"CHIPS\"" +
                "}";
        return postRequest(postJSONData);
    }

    public String buy(String base, String rel, double relvolume, double price) throws IOException {
        String postJSONData = "{" +
                "\"userpass\":\"" + userpass + "\"," +
                "\"method\":\"buy\"," +
                "\"base\":\"" + base + "\"," +
                "\"rel\":\"" + rel + "\"," +
                "\"relvolume\":" + relvolume + "," +
                "\"price\":" + price +
                "}";

        System.out.println("Method call: " + postJSONData);

        return postRequest(postJSONData);
    }


    public String postRequest(String postJSONData) {

        try {
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            System.out.println(postJSONData);

            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());

            dataOutputStream.writeBytes(postJSONData);
            dataOutputStream.flush();
            dataOutputStream.close();

            System.out.println(connection.getResponseCode());

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String output;
            StringBuilder response = new StringBuilder();

            while ((output = in.readLine()) != null) {
                response.append(output);
            }

            in.close();


            System.out.println(response.toString());

            connection.disconnect();

            return response.toString();

        } catch (ConnectException c) {
            System.err.println("Connection refused. marketmaker might not be running");
            return "";
        } catch (Exception e) {
            System.err.println("General exception: " + e.getMessage());
            return "";
        }
    }

    public void setUserpass(String userpass) {
        prefs.put("userpass", userpass);
        this.userpass = userpass;
    }
}
