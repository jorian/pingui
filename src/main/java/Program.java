import javafx.application.Application;
import javafx.stage.Stage;
import utils.StageManager;

public class Program extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        new StageManager(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
