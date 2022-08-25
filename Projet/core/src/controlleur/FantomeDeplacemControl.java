package controlleur;

import static modelesJeu.MovableGameElement.Direction.DOWN;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;

import modelesJeu.Bloc;
import modelesJeu.ElementJeu;
import modelesJeu.Ghosts;
import modelesJeu.GhostsHouse;
import modelesJeu.MovableGameElement;
import modelesJeu.Vector2D;
import modelesJeu.World;
import modelesJeu.MovableGameElement.Direction;

public class FantomeDeplacemControl extends MovementController {
    private Ghosts mGhost;
    public FantomeDeplacemControl(World world, Ghosts ghost) {
        super(world);
        epsilon = (world.getGhosts().get(0).getSpeed() / 6000f);
        setGhost(ghost);
    }

    public Ghosts getGhost() {
        return mGhost;
    }

    public void setGhost(Ghosts ghost) {
        mGhost = ghost;
    }

    public HashMap<Direction, ElementJeu>
    getPossibleDirections(Ghosts ghost, Direction direction) {
        Vector2D position = ghost.getPosition();
        HashMap<Direction, ElementJeu> possibleDirections
                = new HashMap<Direction, ElementJeu>();

        ElementJeu element = getNextLeftElement(position);
        if (!(element instanceof Bloc) && direction != Direction.RIGHT
                && (!ghost.isAlive() || !(element instanceof GhostsHouse))) {
            possibleDirections.put(Direction.LEFT, element);
        }

        element = getNextRightElement(position);
        if (!(element instanceof Bloc) && direction != Direction.LEFT
                && (!ghost.isAlive() || !(element instanceof GhostsHouse))) {
            possibleDirections.put(Direction.RIGHT, element);
        }

        element = getNextUpElement(position);
        if (!(element instanceof Bloc)  && direction != Direction.DOWN
                && (!ghost.isAlive() || !(element instanceof GhostsHouse))) {
            possibleDirections.put(Direction.UP, element);
        }

        element = getNextDownElement(position);
        if (!(element instanceof Bloc) && direction != Direction.UP
                && (!ghost.isAlive() || !(element instanceof GhostsHouse))) {
            possibleDirections.put(Direction.DOWN, element);
        }

        return possibleDirections;
    }

    @Override
    public void checkWantedDirection(MovableGameElement movableGameElement, Direction wantedDirection) {
        ElementJeu nextBlock = getNextElement(movableGameElement.getPosition(),
                movableGameElement.getWantedDirection());

        Ghosts ghost = (Ghosts) movableGameElement;

        switch (wantedDirection) {
            case LEFT:
            case RIGHT:
                if (movableGameElement.getPosition().getY() % 100 == 0) {
                    if (!(nextBlock instanceof Bloc)
                            && (!ghost.isAlive() || !(nextBlock instanceof GhostsHouse))) {
                        movableGameElement.setCurrentDirection(wantedDirection);
                    }
                }
                break;
            case UP:
                if (movableGameElement.getPosition().getX() % 100 == 0) {
                    if (!(nextBlock instanceof Bloc)) {
                        movableGameElement.setCurrentDirection(wantedDirection);
                    }
                }
                break;
            case DOWN:
                if (movableGameElement.getPosition().getX() % 100 == 0) {
                    if (!(nextBlock instanceof Bloc)
                            && (!ghost.isAlive() || !(nextBlock instanceof GhostsHouse))) {
                        movableGameElement.setCurrentDirection(wantedDirection);
                    }
                }
                break;
        }
    }

    @Override
    public void moveElement(MovableGameElement movableGameElement, float deltaTime) {
        checkTunnel(movableGameElement);

        checkWantedDirection(movableGameElement, movableGameElement.getWantedDirection());

        ElementJeu nextBlock = getNextElement(movableGameElement.getPosition(),
                movableGameElement.getCurrentDirection());

        if (!(nextBlock instanceof Bloc) &&
                !((movableGameElement.getCurrentDirection() == DOWN)
                        && (nextBlock instanceof GhostsHouse) &&
                        ((Ghosts) movableGameElement).isAlive())) {
            movableGameElement.updatePosition(deltaTime);
        }

        fixPosition(movableGameElement);

//        checkLocked();
    }

    @Override
    public void updateEpsilon(float deltaTime) {
        float fps = 1 / deltaTime;
        float newEpsilon = mGhost.getSpeed() / (fps * World.getCoef());
        if (newEpsilon < 0.5) {
            epsilon = newEpsilon;
        }
        else {
            epsilon = 0.45;
        }
    }

    public void checkLocked() {
        if (mGhost.getPosition().getX() % 100 <= epsilon * 10
                || mGhost.getPosition().getY() % 100 <= epsilon * 10) {
            Gdx.app.log(getClass().getSimpleName(), "Bloqué en : " + mGhost.getPosition()
                    + " avec un epsilon de " + epsilon);
        }
    }
}
