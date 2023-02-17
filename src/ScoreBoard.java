package src;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.layout.*;
import javafx.geometry.*;
class ScoreBoard extends HBox  {
    static Label p1Label;
    static Label p2Label;
    static int fontSize = 40;
    private int p1Score = 0;
    private int p2Score = 0;
    public ScoreBoard(){
        super(2);
        p1Label = new Label(p1Score + " ");
        p2Label = new Label(" " + p2Score);

        p1Label.setTextFill(Color.WHITE);
        p1Label.setFont(new Font(fontSize));
        p2Label.setTextFill(Color.WHITE);
        p2Label.setFont(new Font(fontSize));

        this.getChildren().addAll(p1Label, p2Label);
        this.setLayoutX(GameScene.Width/2-40);
        // this.setBackground(new Background(new BackgroundFill(Color.GREEN,CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public boolean addP1Score() {
        p1Score++;
        p1Label.setText(p1Score + " ");
        if(p1Score > 3) return true;
        return false;
    } 
    public boolean addP2Score() {
        p2Score++;
        p2Label.setText(" " + p2Score);
        if(p2Score > 3) return true;
        return false;
    }
}