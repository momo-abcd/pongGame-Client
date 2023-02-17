package src;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
class Centerline extends Line {
    public Centerline(){
        super(GameScene.Width /2 , 0,GameScene.Width/2 + 2, GameScene.Height);
        this.setStroke(Color.WHITE);
        this.setStrokeWidth(2);
        this.getStrokeDashArray().addAll(10d);
        this.setStrokeDashOffset(0);
    }
}