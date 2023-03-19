package src;

class ScoreBoard {
    static int fontSize = 40;
    private int p1Score = 0;
    private int p2Score = 0;
    private int endScore = 40;

    // constructor
    public ScoreBoard(){
    }

    public boolean addP1Score() {
        p1Score++;
        if(p1Score > endScore) return true;
        return false;
    } 
    public boolean addP2Score() {
        p2Score++;
        if(p2Score > endScore) return true;
        return false;
    }
    public String getP1Score(){
        return p1Score+"";
    }
    public String getP2Score(){
        return p2Score+"";
    }
}
