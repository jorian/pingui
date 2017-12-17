import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Program extends Application {

    private static Stage stage;
    private static GridPane contentPane;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/main/main.fxml"));
        primaryStage.setTitle("Pingui");

        primaryStage.setMinHeight(900);
        primaryStage.setMinWidth(1200);
        Scene scene = new Scene(root, 1920, 1080);
        scene.getStylesheets().add("css/style.css");

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void setPane(GridPane pane) {
        contentPane = pane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
