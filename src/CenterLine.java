package src;
import javafx.scene.paint.Color;
class CenterLine {
    private Color color;
    public CenterLine(){
        this.color = Color.WHITE;
    }
    public Color getColor(){
        return this.color;
    }
    public void setColor(Color change){
        this.color=change;
    }
}
