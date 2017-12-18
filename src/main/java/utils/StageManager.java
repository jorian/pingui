package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StageManager {

    private static Stage primaryStage;
    private static AnchorPane pane;

    public StageManager(Stage primareStage) throws IOException {
        StageManager.primaryStage = primareStage;
        primareStage.setTitle("Pingui");
        primareStage.setMinWidth(1200);
        primareStage.setMinHeight(900);

        Parent root = FXMLLoader.load(utils.ContentController.class.getResource("/fxml/main/main.fxml"));

        Scene s = new Scene(root, 1920, 1080);
        s.getStylesheets().add("css/style.css"); // css for design
        primareStage.setScene(s);
        primareStage.show();

        ContentController.setPane(ContentController.Pane.COINS);
    }

    public static Stage getStage() {return primaryStage; }

    static void setRoot(Parent root) {StageManager.primaryStage.getScene().setRoot(root);}

    static void setPaneFragment(Parent root) {
        StageManager.pane.getChildren().setAll(root);
    }

    public static void setPane(AnchorPane pane) {StageManager.pane=pane;}

}
