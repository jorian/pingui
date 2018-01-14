package model;

public class Coin {
    private String name;
    private String tradeAddress;
    private Electrum[] electrum;

    private class Electrum {
        String addr;
        String port;
    }

}
