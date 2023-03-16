package src;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class StandByScene extends Scene {
    // Socket 관련 

    final Stage stage;
    Group group;
    // constructor
    public StandByScene(Stage primaryStage) {
        super(new Group(), Main.Width, Main.Height, Color.WHITE);
        group = (Group)getRoot();
        stage = primaryStage;
        setComponents();
    }

    private void setComponents() {
        Label label = new Label("네트워크에 접속 하려면 \"큐 돌리기\"를 클릭하세요 ");
        Button networkConnectionBtn = new Button("큐 돌리기");
        Button _2pvpBtn = new Button("2인 게임 하기");

        VBox vbox = new VBox(label, networkConnectionBtn, _2pvpBtn);
        group.getChildren().addAll(vbox);
        // 1:1 대전 이벤트리스너
        _2pvpBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                stage.setScene(new GameScene(stage));
            }
        });

        networkConnectionBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Rectangle rectangle = new Rectangle(100, 100, Color.BLUE);
                Button cancleBtn = new Button("큐 잡기 취소");
                group.getChildren().addAll(rectangle, cancleBtn);
                QueueThread tt = new QueueThread(stage);
                tt.setDaemon(true);
                tt.start();
                cancleBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("큐 찾기 종료");
                        group.getChildren().removeAll(cancleBtn, rectangle);
                        tt.cancle();
                    }
                });
            }
        });
    }
}