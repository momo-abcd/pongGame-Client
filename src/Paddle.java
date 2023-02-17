package src;

import javafx.scene.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Paddle extends Rectangle {
    // 패들 기본 설정 변수 선언
    private static final double Width = 25;
    private static final double Height = 100;
    // private static final Color COLOR = Color.WHITE;

    // 이동 관련 변수 선언
    private double y;
    private double yVelocity = 0;

    // Paddle 클래스 초기화 생성자
    public Paddle(double initX, double initY, Color color) {
        super(initX,initY,Width,Height);
        y = initY;
        this.setFill(color);
    }

    // y변위 설정 메서드
    private void setYDirection(double yDirection){
        yVelocity = yDirection;
    }

    // Paddle의 y값 변화 메서드
    private void move() {
        if(detectWallCollision()) return;
        else y += yVelocity;

    }

    // Paddle, 벽 충돌 감지 메소드
    private boolean detectWallCollision() {
        if(yVelocity < 0) {
            if(y<GameScene.speed) return true;
            return false;
        }else if(yVelocity > 0){
            if(y >= GameScene.Height - Height) return true;
            return false;
        }else return false;
    }

    public boolean detectBallCollision(Ball ball) {
        double ballX = ball.getX();
        double ballY = ball.getY();
        double radius = ball.getRadius();
        double x = this.getX();
        double y = this.getY();
        // 왼쪽 패들일 경우
        if(x < 1){
            if(x + Width >= ballX-radius){
                if(y <= ballY-radius && y+Height >= ballY+radius) {
                    return true;
                }
            }
        }
        // 오른쪽 패들일 경우
        else {
            if(x <= ballX+radius)
                if(y <= ballY-radius && y+Height >= ballY+radius) {
                    return true;
                }
        }
        return false;
    }

    public void update(double v) {
        setYDirection(v);
        move();
        this.setY(y);
    }
}