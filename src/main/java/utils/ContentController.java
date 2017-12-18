package utils;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class ContentController  {



    public enum Pane {
        COINS("/fxml/scenes/coins.fxml"),
        TRADE("/fxml/scenes/trade.fxml");


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
        System.out.println("ContentController.setPane = " + pane.name());
        StageManager.setPaneFragment(FXMLLoader.load(ContentController.class.getResource(pane.getResourceLocation())));
    }
}
