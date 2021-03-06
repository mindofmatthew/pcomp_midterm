package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.Random;

/**
 * Created by lirong on 10/3/14.
 */
public class CircleObstacle extends Obstacle {
    private static double circleObstacleVelocityX = 2 * GameEngine.defaultSceneSpeed;
    private double radius = 30;

    public CircleObstacle() {
        super(0, 0);
        Random rand = new Random();
        velocityX = (rand.nextInt(4) + 1) * GameEngine.defaultSceneSpeed;
        Circle circle = new Circle(radius, -radius, radius);
        circle.setFill(Color.BLACK);
        getChildren().add(circle);
    }

    @Override
    public void move() {
        super.move();
        rotate();
    }

    public void updateSpeed() {
        velocityY = velocityY + GameEngine.gravity;
    }

    @Override
    public void horizontalCollision(GameObject collidingObj) {
        if (collidingObj instanceof GameCharacter) return;
        if (collidingObj instanceof ThornObstacle) return;
        velocityX = -velocityX; //GameEngine.getSceneSpeed();
    }

    @Override
    public void collisionDownward(double y, GameObject collidingObj) {
        if (collidingObj instanceof GameCharacter) return;
        if (collidingObj instanceof ThornObstacle) return;
        land(y);
    }

    @Override
    public void land(double y) {
        velocityY = 0;
        this.setTranslateY(y);
    }

    // TODO
    public void rotate() {

    }

    @Override
    public CircleObstacle getDeepCopy() {
        return new CircleObstacle();
    }
}