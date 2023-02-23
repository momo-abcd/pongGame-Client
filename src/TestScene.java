package src;
import javafx.scene.*;

import javafx.scene.paint.Color;
class TestScene extends Scene {
    private final static Group group = new Group();
    public TestScene() {
        super(group, 500, 500, Color.RED);
    }

}