package utils;

public class ContentController {

    public enum Pane {
        COINS("fxml/scenes/coins.fxml"),
        TRADE("fxml/scenes/trade.fxml");


        private String resourceLocation;

        Pane(String resourceLocation) {
            this.resourceLocation = resourceLocation;
        }
    }

    public static void setPane(Pane pane) {

    }
}
