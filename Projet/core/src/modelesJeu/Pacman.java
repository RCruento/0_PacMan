package modelesJeu;

import controlleur.MovementController;

public class Pacman extends MovableGameElement {
    private float deadCounter = 0;
    public Pacman(Vector2D position, World world, int speed, MovementController movementController) {
        super(position, world, speed);
    }

    public float getDeadCounter() {
        return deadCounter;
    }

    public void setDeadCounter(float deadCounter) {
        this.deadCounter = deadCounter;
    }

    public void decreaseDeadCounter(float deltaTime) {
        deadCounter -= deltaTime;
        if (deadCounter < 0) {
            deadCounter = 0;
        }
    }
}