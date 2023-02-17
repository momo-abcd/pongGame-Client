package src;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage stage) {
        GameScene gameScene = new GameScene();

        stage.setScene(gameScene);
        stage.setResizable(false);
        stage.setTitle("JavaFx Pong Game!!");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}