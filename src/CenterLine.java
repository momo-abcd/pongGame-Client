package src;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
class CenterLine {
    private Color color;
    public CenterLine(){
        // super(GameScene.Width /2 , 0,GameScene.Width/2 + 2, GameScene.Height);
        // this.setStroke(Color.WHITE);
        // this.setStrokeWidth(2);
        // this.getStrokeDashArray().addAll(10d);
        // this.setStrokeDashOffset(0);

        this.color = Color.WHITE;
    }
    public Color getColor(){
        return this.color;
    }
    public void setColor(Color change){
        this.color=change;
    }
}
