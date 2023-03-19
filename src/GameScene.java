package src;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.*;
import javafx.scene.canvas.*;
import javafx.scene.text.Font;
import javafx.scene.control.Label;

public class GameScene extends Scene {
    //Scene 기본 설정 값 
    final static double Width = 640;
    final static double Height = 540;
    // private static final Group group = new Group();

    // timer 변수
     AnimationTimer animationTimer;

    // Paddle 변수 선언
    Paddle userPaddle;
    Paddle pcPaddle;
    static double speed = 10; //패들 움직임 속도

    // Ball, Score 변수 선언
    Ball ball;
    ScoreBoard score;

    BooleanProperty wPressed = new SimpleBooleanProperty();
    BooleanProperty sPressed = new SimpleBooleanProperty();
    BooleanProperty upPressed = new SimpleBooleanProperty();
    BooleanProperty downPressed = new SimpleBooleanProperty();

    // graphics 로 전환 변수들
     final Canvas canvas = new Canvas(Width,Height);
     GraphicsContext gc = canvas.getGraphicsContext2D();

    Group group;

    final Stage stage;
    CenterLine line;

    // constructor
    public GameScene(Stage primaryStage){
        super(new Group(), Width, Height, Color.BLACK);
        stage = primaryStage;

        ball = new Ball();
        userPaddle = new Paddle(0, Height/2-50);
        pcPaddle = new Paddle(Width-Paddle.Width,Height/2-50);
        line = new CenterLine();
        score = new ScoreBoard();
        gc.setFont(new Font(40));

        // group.getChildren().addAll(userPaddle, pcPaddle, ball, line,score);
        group = (Group) getRoot();
        group.getChildren().add(canvas);
        installEvent();
        animationTimer = timer();
        animationTimer.start();
    }

    // 이벤트 핸들러 등록 함수
    private void installEvent() {
        final EventHandler<KeyEvent> handler = new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event){
                switch(event.getCode()){
                    case W:
                        if(event.getEventType() == KeyEvent.KEY_PRESSED){
                            // userPaddle.update(-speed);
                            wPressed.set(true);
                            event.consume();
                        }else {
                            // userPaddle.update(0);
                            wPressed.set(false);
                            event.consume();
                        }
                      break;
                    case S:
                        if(event.getEventType() == KeyEvent.KEY_PRESSED){
                            // userPaddle.update(speed);
                            sPressed.set(true);
                            event.consume();
                        }else {
                            // userPaddle.update(0);
                            sPressed.set(false);
                            event.consume();
                        }
                        break;
                    case UP:
                        if(event.getEventType() == KeyEvent.KEY_PRESSED){
                            // pcPaddle.update(-speed);
                            upPressed.set(true);
                            event.consume();
                        }else {
                            // pcPaddle.update(0);
                            upPressed.set(false);
                            event.consume();
                        }
                        break;
                    case DOWN:
                        if(event.getEventType() == KeyEvent.KEY_PRESSED){
                            // pcPaddle.update(speed);
                            downPressed.set(true);
                            event.consume();
                        }else {
                            // pcPaddle.update(0);
                            downPressed.set(false);
                            event.consume();
                        }
                        break;
                }
            }
        };
        this.setOnKeyPressed(handler);
        this.setOnKeyReleased(handler);
    }


    // 그리기 함수 (에니메이션 쓰레드에서 주기마다 호출)
    private void gameLogic() {
        // ball.update();
        if(wPressed.get()){
            userPaddle.update(-speed);
        }
        if(sPressed.get()){
            userPaddle.update(speed);
        }
        if(upPressed.get()){
            pcPaddle.update(-speed);
        }
        if(downPressed.get()){
            pcPaddle.update(speed);
        }

        // 패들과 볼 충돌시 방향 전환
        if(userPaddle.detectBallCollision(ball)){
            double collidPoint = (ball.getY() - (userPaddle.getY() + Paddle.Height/2));
            collidPoint = collidPoint / (Paddle.Height/2);

            double angleRad = (Math.PI/4) * collidPoint;
            // ball.reverseX();
            double direction = ball.getX() + ball.getRadius() < Width/2 ? 1 : -1;
            ball.setXVelocity(angleRad, direction);
            ball.setYVelocity(angleRad, direction);
        }
        if(pcPaddle.detectBallCollision(ball)){
            double collidPoint = (ball.getY() - (pcPaddle.getY() + Paddle.Height/2));
            collidPoint = collidPoint / (Paddle.Height/2);

            double angleRad = (Math.PI/4) * collidPoint;
            double direction = ball.getX() + ball.getRadius() < Width/2 ? 1 : -1;
            ball.setXVelocity(angleRad, direction);
            ball.setYVelocity(angleRad, direction);
        }

        // 볼이 나갔을 경우 점수 처리
        if((int)ball.getX() <= -1){
            ball.resetBall();
            if(score.addP1Score()){
                // 게임 끝내기
                endGame("You Win!");
            }
        }
        if((int)ball.getX()+ball.getRadius() >= (int)Width+1){
            ball.resetBall();
            if(score.addP2Score()) {
                // 게임 끝내기
                endGame("You Lose!");
            }
        }
        // scoreBoard.update();
        ball.update();
        draw();
    }
    private void endGame(String whoWin) {
        animationTimer.stop();
        animationTimer = null;

        Label label = new Label(whoWin);
        Label label2 = new Label("Press Enter to restart the game / Home to press \"Q\"");
        label.setFont(new Font(70));
        label.setTextFill(Color.GREEN);
        label.setLayoutX(Width/2 - 140);
        label.setLayoutY(Height/2- 70);
        label2.setFont(new Font(20));
        label2.setTextFill(Color.GREEN);
        label2.setLayoutX(Width/2 - 185);
        label2.setLayoutY(Height/2+ 30);
        
        // group.getChildren().remove(ball);
        // group.getChildren().remove(score);
        ball.setColor(Color.BLACK);
        line.setColor(Color.BLACK);
        group.getChildren().addAll(label, label2);
        // stage.setScene(new GameScene(stage));


        // final EventHandler<KeyEvent> handler = new EventHandler<KeyEvent>(){

        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().toString().equals("ENTER")){
                    // 엔터 누르면 게임 다시 시작
                    stage.setScene(new GameScene(stage));
                }
                if(event.getCode().toString().equals("Q")){
                    // stage.close();
                    stage.setScene(new StandByScene(stage));
                }
            }
        });
    }
    void draw() {
        // clear previous drawing
        System.out.println(ball.getX() + " // " + ball.getY());
        this.gc.clearRect(0,0, Width,Height);

        // draw center line
        gc.setStroke(line.getColor());
        gc.setLineWidth(2);
        gc.setLineDashes(20);
        gc.strokeLine(Width/2,0,Width/2+10,Height);

        // draw userPaddle
        this.gc.setFill(Color.WHITE);
        this.gc.fillRect(0,userPaddle.getY(), Paddle.Width,Paddle.Height);

        // draw pcPaddle
        this.gc.setFill(Color.RED);
        this.gc.fillRect(pcPaddle.getX(),pcPaddle.getY(), Paddle.Width,Paddle.Height);

        // draw ball
        gc.setFill(ball.getColor());
        gc.fillRoundRect(ball.getX(), ball.getY(),Ball.Width, Ball.Height,Ball.Width,Ball.Height);

        // draw scroe
        gc.setFill(Color.WHITE);
        gc.fillText(score.getP1Score(),Width/2-50,50);
        gc.fillText(score.getP2Score(),Width/2+25,50);
    }

    // 에니메이션 쓰레드 생성함수
    private AnimationTimer timer() {
        AnimationTimer timer = new AnimationTimer(){
            @Override
            public void handle(long now){
                gameLogic();
            }
        };
        return timer;
    }
}
