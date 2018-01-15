package model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ElectrumServers {
    private HashMap<String, ArrayList<Electrum>> servers;
    private Set<String> coinsWithElectrumServers;
    private JsonObject electrumsFile;


    public ElectrumServers() {
        servers = new HashMap<>();

        try {
            electrumsFile = new JsonParser().parse(new String(Files.readAllBytes(Paths.get("src/main/resources/assets/electrum.json")))).getAsJsonObject();
        } catch (IOException e) {
            System.err.println("electrum.json not found");
        }

        coinsWithElectrumServers = electrumsFile.keySet();

        // TODO needs solution.
        // Retrieve servers from difficult to parse JSON:
        coinsWithElectrumServers.forEach((coinName -> {
            ArrayList<Electrum> electrums = new ArrayList<>(2);
            (electrumsFile.get(coinName).getAsJsonArray()).forEach(jsonElement -> {
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

    }

    public Set<String> getCoinsWithElectrumServers() {
        return new TreeSet<>(this.coinsWithElectrumServers);
    }

    public Map<String, ArrayList<Electrum>> getServers() {
        return new HashMap<>(servers);
    }

//    public ElectrumServers getElectrumServers() {
//        return this;
//    }


    public class Electrum {
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
