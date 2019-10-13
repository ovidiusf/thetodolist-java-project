package sample.animations;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Transition {
    private FadeTransition fadeTransition;

    public Transition(Node node) {
        //fadeout Transition
        fadeTransition = new FadeTransition(Duration.millis(1000), node);
        fadeTransition.setFromValue(1f);
        fadeTransition.setToValue(0f);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);
    }

    public void setFadeTransition(int milliseconds) {
        Duration duration = new Duration(milliseconds);
        fadeTransition.setDuration(duration);
    }

    public void fade(int milliseconds, boolean fadeIn) {
        Duration duration = new Duration(milliseconds);
        fadeTransition.setDuration(duration);
        if (fadeIn) {
            setToFadeIn();
        }
        fade();
    }

    public void setToFadeIn() {
        //overwrites the initial constructor by setting the node to fade in
        fadeTransition.setFromValue(0f);
        fadeTransition.setToValue(1f);
    }

    public void fade() {
        fadeTransition.play();
    }

    public FadeTransition getFadeTransition() {
        return fadeTransition;
    }
}
