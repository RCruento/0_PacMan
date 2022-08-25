package modelesJeu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import controlleur.MovementController;


public class MovableGameElement extends ElementJeu {

    public enum Direction {
        LEFT, UP, RIGHT, DOWN;

        private static final List<Direction> VALUES =
                Collections.unmodifiableList(Arrays.asList(values()));
        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();

        public static Direction randomDirection()  {
            return VALUES.get(RANDOM.nextInt(SIZE));
        }
    }

    private Vector2D startingPos;
    private Direction mWantedDirection;
    private Direction mCurrentDirection;
    public int mSpeed;
    protected MovementController mMovementController;

    protected MovableGameElement(Vector2D position, World world, int speed) {
        super(position, world);
        mCurrentDirection = Direction.RIGHT;
        mWantedDirection = Direction.RIGHT;
        setSpeed(speed);
        startingPos = new Vector2D(position);
    }
    

    public void resetPosition() {
        mPosition = new Vector2D(startingPos);
    }


    public Direction getCurrentDirection() {
        return mCurrentDirection;
    }


    public void setCurrentDirection(Direction currentDirection) {
        mCurrentDirection = currentDirection;
    }

    public Direction getWantedDirection() {

        return mWantedDirection;
    }

    public void setWantedDirection(Direction wantedDirection) {
        mWantedDirection = wantedDirection;
    }

    public int getSpeed() {
        return mSpeed;
    }

    public void setSpeed(int speed) {
        if (speed <= 0) {
            throw new IllegalArgumentException("The speed must be positive.");
        }
        mSpeed = speed;
    }

    public MovementController getMovementController() {
        return mMovementController;
    }

    public void setMovementController(MovementController movementController) {
        mMovementController = movementController;
    }

    public void updatePosition(float deltaTime) {
        switch (mCurrentDirection) {
            case LEFT:
                mPosition.x -= (mSpeed * deltaTime);
                break;
            case RIGHT:
                mPosition.x += (mSpeed * deltaTime);
                break;
            case UP:
                mPosition.y -= (mSpeed * deltaTime);
                break;
            case DOWN:
                mPosition.y += (mSpeed * deltaTime);
                break;
        }
    }

    public void move(float deltaTime) {
        if (mMovementController == null) {
            throw new RuntimeException("No movement controller has been set.");
        }
        mMovementController.moveElement(this, deltaTime);
    }
}
