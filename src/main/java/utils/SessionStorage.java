package utils;

public class SessionStorage {

    private static String passphrase;
    private String userpass;

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
        System.out.println(this.passphrase);
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }

    public static String getPassphrase() {
        return passphrase;
    }
}
