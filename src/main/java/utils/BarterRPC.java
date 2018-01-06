package utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BarterRPC {
    private URL url;
    private HttpURLConnection connection;

    public BarterRPC() throws Exception {
        url = new URL("http://127.0.0.1:7783");
    }

    public String orderbook(String base, String rel) throws IOException {
        String postJSONData = "{" +
                "\"userpass\":\"423556bfd014ce25934b72d34060c79e25b1f60eaef1d0010e4c3fc809943b7e\"," +
                "\"method\":\"orderbook\"," +
                "\"base\":\"" + base + "\"," +
                "\"rel\":\"" + rel + "\"" +
                "}";

        return postRequest(postJSONData);
    }

    public String buy(String base, String rel, double relvolume, double price) throws IOException {
        String postJSONData = "{" +
                "\"userpass\":\"423556bfd014ce25934b72d34060c79e25b1f60eaef1d0010e4c3fc809943b7e\"," +
                "\"method\":\"buy\"," +
                "\"base\":\"" + base + "\"," +
                "\"rel\":\"" + rel + "\"," +
                "\"relvolume\":" + relvolume + "," +
                "\"price\":" + price +
                "}";

        return postRequest(postJSONData);
    }


    private String postRequest(String postJSONData) throws IOException {
        connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

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
    }
}
