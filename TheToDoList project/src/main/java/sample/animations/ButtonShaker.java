package sample.animations;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;

public class ButtonShaker extends Shaker {

    public ButtonShaker(Node node) {
        super(node);
        TranslateTransition translateTransition = getTranslateTransition();
        translateTransition.setByX(10f);
        translateTransition.setByY(20f);
        translateTransition.setCycleCount(2);
    }

    @Override
    public void shake() {
        super.shake();
    }
}
