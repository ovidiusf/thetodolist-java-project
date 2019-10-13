package sample.animations;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Shaker {
    private TranslateTransition translateTransition;

    public Shaker(Node node) {
        translateTransition = new TranslateTransition(Duration.millis(50), node);
        translateTransition.setFromX(0f);
        translateTransition.setByX(15f);
        translateTransition.setCycleCount(4);
        translateTransition.setAutoReverse(true);
    }

    public void shake(){
        translateTransition.playFromStart();
    }

    public TranslateTransition getTranslateTransition() {
        return translateTransition;
    }
}
