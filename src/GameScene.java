package src;
import javafx.application.Platform;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.*;
import javafx.scene.paint.*;
 import javafx.scene.canvas.*;
 import javafx.scene.shape.ArcType;
 import javafx.scene.text.Font;


public class GameScene extends Scene {
    //Scene 기본 설정 값 
    final static double Width = 640;
    final static double Height = 540;
    private final static Group group = new Group();

    // timer 변수
    final AnimationTimer animationTimer;

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


    // GameScene 초기화
    public GameScene(){
        super(group, Width, Height, Color.BLACK);

        ball = new Ball();
        userPaddle = new Paddle(0, Height/2-50);
        pcPaddle = new Paddle(Width-Paddle.Width,Height/2-50);
        // Centerline line = new Centerline();
        score = new ScoreBoard();
        gc.setFont(new Font(40));

        // group.getChildren().addAll(userPaddle, pcPaddle, ball, line,score);
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
                    case Q:
                        animationTimer.stop();
                        break;
                    case R:
                        animationTimer.start();
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
        if((int)ball.getX() == -1){
            ball.resetBall();
            if(score.addP1Score()){
            }
        }
        if((int)ball.getX()+ball.getRadius() >= (int)Width+1){
            ball.resetBall();
            if(score.addP2Score()) {
            }
        }
        // scoreBoard.update();
        ball.update();
        draw();
    }
    void draw() {
        this.gc.clearRect(0,0, Width,Height);

        // draw userPaddle
        this.gc.setFill(Color.WHITE);
        this.gc.fillRect(0,userPaddle.getY(), Paddle.Width,Paddle.Height);

        // draw pcPaddle
        this.gc.setFill(Color.RED);
        this.gc.fillRect(pcPaddle.getX(),pcPaddle.getY(), Paddle.Width,Paddle.Height);

        // draw ball
        gc.setFill(Color.BLUE);
        // gc.fillArc(ball.getX()+ball.getRadius(), ball.getY()-ball.getRadius(),Ball.Width, Ball.Height,(double)0,(double)360,ArcType.OPEN);;
        // gc.fillOval(ball.getX(), ball.getY(),Ball.Width, Ball.Height);
        gc.fillRoundRect(ball.getX(), ball.getY(),Ball.Width, Ball.Height,Ball.Width,Ball.Height);

        gc.setFill(Color.WHITE);
        gc.fillText(score.getP1Score(),Width/2-50,50);
        gc.fillText(score.getP2Score(),Width/2+50,50);

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.setLineDashes(20);
        // gc.setLineDashOffset(5);
        gc.strokeLine(Width/2,0,Width/2+10,Height);
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