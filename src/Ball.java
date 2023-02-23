package src;
import javafx.scene.paint.Color;
import java.util.Random;
class Ball {
    // 볼의 성질에 대한 변수 선언
    // private static double radius = 30;
    private static double ballSpeed = 3;
    private Color color;

     static double Width = 30;
     static double Height = 30;
     private double radius = Width/2;


    private double x;
    private double y;

    private double yVelocity;
    private double xVelocity;
    public Ball() {
        resetBall();
        yVelocity = ballSpeed;
        xVelocity = ballSpeed;
        color = Color.BLUE;
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
        if(y <= 0 )
            reverseY();
        if(y + Height >= GameScene.Height)
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
    public double getRadius() {
        return radius;
    }
    public double getXDirection() {
        return xVelocity;
    }
    public double getYDirection() {
        return xVelocity;
    }
    public void setXVelocity(double angleRad, double direction) {
        // xVelocity = direction * ballSpeed * Math.cos(angleRad);
        xVelocity = direction * ballSpeed;
    }
    public void setYVelocity(double angleRad, double direction) {
        // yVelocity = ballSpeed * Math.sin(angleRad);
        yVelocity = ballSpeed * Math.sin(angleRad);
    }
    public Color getColor(){
        return this.color;
    }
    public void setColor(Color change){
        this.color=change;
    }
}
