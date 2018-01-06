package utils;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class ContentController  {

    private static Pane currentPane;

    public enum Pane {
        COINS("/fxml/scenes/coins.fxml"),
        TRADE("/fxml/scenes/trade.fxml"),
        FAQ("/fxml/scenes/faq.fxml");


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
        System.out.println("ContentController.setPane = " + pane.name());
        StageManager.setPaneFragment(FXMLLoader.load(ContentController.class.getResource(pane.getResourceLocation())));
    }

    public static Pane getPane() {
        return currentPane;
    }
}
