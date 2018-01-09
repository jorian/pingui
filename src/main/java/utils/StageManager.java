package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import static utils.BarterRPC.barterRPC;

public class StageManager {

    private static Stage primaryStage;
    private static AnchorPane pane;

    public StageManager(Stage primareStage) throws Exception {
        barterRPC =  new BarterRPC();

        StageManager.primaryStage = primareStage;
        primareStage.setTitle("Pingui");
        primareStage.setMinWidth(600);
        primareStage.setMinHeight(450);

        // Initial FXML:
        Parent root = FXMLLoader.load(utils.ContentController.class.getResource("/fxml/main/main.fxml"));

        Scene s = new Scene(root, 1200, 650);
        s.getStylesheets().add("css/style.css"); // css for design
        primareStage.setScene(s);
        primareStage.show();

        primareStage.setOnCloseRequest(windowEvent -> {
            try {
                Runtime.getRuntime().exec("pkill -15 marketmaker");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ContentController.setPane(ContentController.Pane.PASSPHRASE);
    }

    public static Stage getStage() {return primaryStage; }

    public static void setRoot(Parent root) {StageManager.primaryStage.getScene().setRoot(root);}

    public static void setPaneFragment(Parent root) {
        StageManager.pane.getChildren().setAll(root);
    }

    public static void setPane(AnchorPane pane) {StageManager.pane=pane;}

}
