package utils;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class ContentController  {

    private static Pane currentPane;

    public enum Pane {
        COINS("/fxml/scenes/coins.fxml"),
        EXCHANGE("/fxml/scenes/exchange.fxml"),
        FAQ("/fxml/scenes/faq.fxml"),
        PASSPHRASE("/fxml/scenes/passphrase.fxml"),
        TRADEHISTORY("/fxml/scenes/tradeHistory.fxml");


        private String resourceLocation;

        Pane(String resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public String getResourceLocation() {
            return resourceLocation;
        }
    }

    public ContentController() {

    }

    public static void setPane(Pane pane) throws IOException {
        currentPane = pane;
//        System.out.println("ContentController.setPane = " + pane.name());

//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(pane.getResourceLocation()))

        StageManager.setPaneFragment(FXMLLoader.load(ContentController.class.getResource(pane.getResourceLocation())));

    }

    public static Pane getPane() {
        return currentPane;
    }
}
