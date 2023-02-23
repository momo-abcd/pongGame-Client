package src;
import javafx.scene.paint.Color;
import java.util.Random;
class Ball {
    private static double ballSpeed = 6;
    static double Width = 30;
    static double Height = 30;

    private Color color;
    private double radius = Width/2;

    private double x;
    private double y;

    private double yVelocity;
    private double xVelocity;

    // constructor
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
        move();
    }
    public void reverseX() {
        xVelocity *= -1;
    }
    public void reverseY() {
        yVelocity *= -1;
    }
    public void resetBall() {
        x = GameScene.Width/2;
        y = GameScene.Height/2;
        xVelocity *= (new Random()).nextBoolean() ? -1 : 1;
    }

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
    private void detectWallCollision() {
        if(y <= 0 )
            reverseY();
        if(y + Height >= GameScene.Height)
            reverseY();
    }
}
