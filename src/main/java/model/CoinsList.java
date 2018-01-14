package model;

import java.util.ArrayList;

public class CoinsList {
    private ArrayList<Coin> coins;

    public CoinsList() {
        coins = new ArrayList<>();
    }

    public void addToCoinsList(Coin c) {
        this.coins.add(c);
    }

    public ArrayList<Coin> getCoins() {
        return coins;
    }

    public class Coin {
        private String coin;
        private boolean installed;
        private long height;
        private double balance;
        private double KMDvalue;
        private String status;
        private String smartaddress;

        public Coin() {

        }

        @Override
        public String toString() {
            return "Name: " + this.coin + ", smartaddress: " + this.smartaddress;
        }

        public String getCoin() {
            return coin;
        }

        public void setCoin(String coin) {
            this.coin = coin;
        }

        public boolean isInstalled() {
            return installed;
        }

        public void setInstalled(boolean installed) {
            this.installed = installed;
        }

        public long getHeight() {
            return height;
        }

        public void setHeight(long height) {
            this.height = height;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public double getKMDvalue() {
            return KMDvalue;
        }

        public void setKMDvalue(double KMDvalue) {
            this.KMDvalue = KMDvalue;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSmartaddress() {
            return smartaddress;
        }

        public void setSmartaddress(String smartaddress) {
            this.smartaddress = smartaddress;
        }
    }
}
