package model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ElectrumServers {
    private HashMap<String, ArrayList<Electrum>> servers;
    private Set<String> electrumEnabledCoins;
    private JsonObject electrumsFile;



    ElectrumServers() {
        servers = new HashMap<>();

        try {
            electrumsFile = new JsonParser().parse(new String(Files.readAllBytes(Paths.get("src/main/resources/assets/electrum.json")))).getAsJsonObject();
        } catch (IOException e) {
            System.err.println("electrum.json not found");
        }
        electrumEnabledCoins = electrumsFile.keySet();


        electrumEnabledCoins.forEach((coinName -> {
            ArrayList<Electrum> electrums = new ArrayList<>(2);
            (electrumsFile.get(coinName).getAsJsonArray()).forEach(jsonElement -> {
                Gson gson = new Gson();
                Set<String> keySet = jsonElement.getAsJsonObject().keySet();
                        keySet.forEach(key -> {
                            Electrum electrum = new Electrum(key,jsonElement.getAsJsonObject().get(key).getAsString());
                            electrums.add(electrum);
                        });
            });
            servers.put(coinName,electrums);
        }));

        servers.get("BTC").forEach(electrum ->
                System.out.println(electrum.toString()));

//        JsonArray ipList = electrumsFile.getAsJsonArray();
//

//
//
//        ArrayList<String> coinIPList = new ArrayList<>();
//
//        System.out.println(ipList.toString());
//        for (JsonElement j : ipList) {
////                System.out.println(j.getAsJsonPrimitive().toString());
//
//            Set<String> ip = j.getAsJsonObject().keySet();
//            coinIPList.addAll(ip);
//        }
//
//        System.out.println(coinIPList);
    }

    public static void main(String[] args) {
        new ElectrumServers();
    }

    class Electrum {
        String ipaddr;

        String port;
        Electrum() {

        }

        Electrum(String ipaddr, String port) {
            this.ipaddr = ipaddr;
            this.port = port;
        }

        public String getIpaddr() {
            return ipaddr;
        }

        public void setIpaddr(String ipaddr) {
            this.ipaddr = ipaddr;
        }

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }

        @Override
        public String toString() {
            return "IP: " + this.ipaddr + ", Port: " + this.port;
        }
    }
}
