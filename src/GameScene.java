package src;
import javafx.application.Platform;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.*;

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



    // GameScene 초기화
    public GameScene(){
        super(group, Width, Height, Color.BLACK);

        ball = new Ball(Width/2, Height/2);
        userPaddle = new Paddle(0, Height/2-50, Color.WHITE);
        pcPaddle = new Paddle(Width-25,Height/2-50, Color.RED);
        Centerline line = new Centerline();
        score = new ScoreBoard();

        group.getChildren().addAll(userPaddle, pcPaddle, ball, line,score);
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
        ball.update();
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
        if(userPaddle.detectBallCollision(ball) || pcPaddle.detectBallCollision(ball)) {
            ball.reverseX();
            // ball.reverseY();
        }

        // 볼이 나갔을 경우 점수 처리
        if((int)ball.getX() == -1){
            ball.resetBall();
            if(score.addP1Score()){
            }
        }
        if((int)ball.getX() == (int)Width+1){
            System.out.println((int)ball.getX());
            ball.resetBall();
            if(score.addP2Score()) {
            }
        }
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