package src;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
    public final static double Width = 640;
    public final static double Height = 540;
    private  Stage primaryStage;
    @Override

    public void start(Stage stage) {
        primaryStage = stage;
        GameScene gameScene = new GameScene(primaryStage);
        StandByScene standByScene = new StandByScene(stage);

        // stage.setScene(gameScene);
        stage.setScene(standByScene);
        stage.setResizable(false);
        stage.setTitle("JavaFx Pong Game!!");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
