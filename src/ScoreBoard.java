package src;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.layout.*;
import javafx.geometry.*;
class ScoreBoard {
    static int fontSize = 40;
    private int p1Score = 0;
    private int p2Score = 0;
    public ScoreBoard(){
    }

    public boolean addP1Score() {
        p1Score++;
        if(p1Score > 1) return true;
        return false;
    } 
    public boolean addP2Score() {
        p2Score++;
        if(p2Score > 1) return true;
        return false;
    }
    public String getP1Score(){
        return p1Score+"";
    }
    public String getP2Score(){
        return p2Score+"";
    }
}
