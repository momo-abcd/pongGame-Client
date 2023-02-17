package src;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import java.util.Random;
class Ball extends Circle {
    // 볼의 성질에 대한 변수 선언
    private static double radius = 15;
    private static double ballSpeed = 3;


    private double x = GameScene.Width/2;
    private double y = GameScene.Height/2;

    private double yVelocity = ballSpeed;
    private double xVelocity = ballSpeed;
    public Ball(double initX, double initY) {
        super(initX,initY,radius);
        this.setFill(Color.BLUE);
        resetBall();
    }

    private void move() {
        detectWallCollision();
        x += xVelocity;
        y += yVelocity;
    }
    public void update(){
        // setYDirection();
        // setXDirection();
        move();
        setCenterX(x);
        setCenterY(y);
    }
    // private void setYDirection(double yDirection){
    //     yVelocity *= yDirection;
    // }
    public void reverseX() {
        xVelocity *= -1;
    }
    public void reverseY() {
        yVelocity *= -1;
    }
    // private void setXDirection(double xDirection){
    //     xVelocity *= xDirection;
    // }
    public void resetBall() {
        x = GameScene.Width/2;
        y = GameScene.Height/2;
        xVelocity *= (new Random()).nextBoolean() ? -1 : 1;
    }
    private void detectWallCollision() {
        // x - radius 한 값이 <= 0 --> setYDirection(-1);
        if(y - radius <= 0 )
            reverseY();
        if(y + radius >= GameScene.Height)
            reverseY();
        // if(x-radius <= 0)
        //     reverseX();
        // if(x +radius >= GameScene.Width)
        //     reverseX();
    }

    // public double getRadius() {
    //     return this.radius;
    // }
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }

}