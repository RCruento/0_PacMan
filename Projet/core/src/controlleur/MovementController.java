package controlleur;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import com.badlogic.gdx.utils.TimeUtils;
import modelesJeu.Bloc;
import modelesJeu.ElementJeu;
import modelesJeu.Ghosts;
import modelesJeu.GhostsHouse;
import modelesJeu.LabyrintheElement;
import modelesJeu.MovableGameElement;
import modelesJeu.SuperPellet;
import modelesJeu.Vector2D;
import modelesJeu.Vide;
import modelesJeu.World;
import modelesJeu.MovableGameElement.Direction;


public abstract class MovementController {
    protected World mWorld;
    protected int mCoef = World.getCoef();
    protected double epsilon;

    public MovementController(World world) {
        setWorld(world);
    }

    public void setWorld(World world) {
        if (world == null) {
            throw new IllegalArgumentException();
        }
        mWorld = world;
    }

    public World getWorld() {
        return mWorld;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

    public double getEpsilon() {
        return epsilon;
    }

    public ElementJeu getNextElement(Vector2D position, Direction direction) {
        switch (direction) {
            case LEFT:
                return getNextLeftElement(position);
            case RIGHT:
                return getNextRightElement(position);
            case UP:
                return getNextUpElement(position);
            case DOWN:
                return getNextDownElement(position);
        }

        throw new IllegalArgumentException("Unrecognized Direction.");
    }

    public LabyrintheElement getNextUpElement(Vector2D position) {
        return mWorld.getMaze().getBlock(
                position.getX() / mCoef,
                (int) Math.ceil((position.getY() / ((float) mCoef))) - 1);
    }

    public LabyrintheElement getNextDownElement(Vector2D position) {
        return mWorld.getMaze().getBlock(
                position.getX() / mCoef,
                (position.getY() / mCoef) + 1);
    }

    public LabyrintheElement getNextRightElement(Vector2D position) {
        return mWorld.getMaze().getBlock(
                (position.getX() / mCoef) + 1,
                position.getY() / mCoef);
    }

    public LabyrintheElement getNextLeftElement(Vector2D position) {
        return mWorld.getMaze().getBlock(
                (int) Math.ceil((position.getX() / ((float) mCoef)) - 1),
                position.getY() / mCoef);
    }

    public HashMap<Direction, LabyrintheElement>
    getPossibleDirections(Vector2D position, Direction direction) {
        HashMap<Direction, LabyrintheElement> possibleDirections
                = new HashMap<Direction, LabyrintheElement>();

        LabyrintheElement element = getNextLeftElement(position);
        if (!(element instanceof Bloc) && direction != Direction.RIGHT
                && !(element instanceof GhostsHouse)) {
            possibleDirections.put(Direction.LEFT, element);
        }

        element = getNextRightElement(position);
        if (!(element instanceof Bloc) && direction != Direction.LEFT
                && !(element instanceof GhostsHouse)) {
            possibleDirections.put(Direction.RIGHT, element);
        }

        element = getNextUpElement(position);
        if (!(element instanceof Bloc)  && direction != Direction.DOWN
                && !(element instanceof GhostsHouse)) {
            possibleDirections.put(Direction.UP, element);
        }

        element = getNextDownElement(position);
        if (!(element instanceof Bloc) && direction != Direction.UP
                && !(element instanceof GhostsHouse)) {
            possibleDirections.put(Direction.DOWN, element);
        }

        return possibleDirections;
    }

    public LinkedList<LabyrintheElement>
    getAvailableBlocks(Vector2D position) {
        LinkedList<LabyrintheElement> possibleDirections
                = new LinkedList<LabyrintheElement>();

        LabyrintheElement element = getNextLeftElement(position);
        if (!(element instanceof Bloc)
                && !(element instanceof GhostsHouse)) {
            possibleDirections.add(element);
        }

        element = getNextRightElement(position);
        if (!(element instanceof Bloc)
                && !(element instanceof GhostsHouse)) {
            possibleDirections.add(element);
        }

        element = getNextUpElement(position);
        if (!(element instanceof Bloc)
                && !(element instanceof GhostsHouse)) {
            possibleDirections.add(element);
        }

        element = getNextDownElement(position);
        if (!(element instanceof Bloc)
                && !(element instanceof GhostsHouse)) {
            possibleDirections.add(element);
        }

        return possibleDirections;
    }

    public Map.Entry<Direction, LabyrintheElement>
    getBlockWithSmallestLabel(Vector2D position, Direction direction) {
        HashMap<Direction, LabyrintheElement> availableBlocks
                = getPossibleDirections(position, direction);

        Map.Entry<Direction, LabyrintheElement> result = null;
        int min = Integer.MAX_VALUE;

        for (Map.Entry<Direction, LabyrintheElement> entry : availableBlocks.entrySet()) {
            int shortestPathLabel = entry.getValue().getShortestPathLabel();
            if (shortestPathLabel < min) {
                result = entry;
                min = entry.getValue().getShortestPathLabel();
            }
        }

        return result;
    }

    public double getDistance(Vector2D position1, Vector2D position2) {
        double diffX = position1.getX() - position2.getX();
        double diffY = position1.getY() - position2.getY();
        return Math.sqrt((diffX * diffX) + (diffY * diffY));
    }

    boolean estAIntersection(Vector2D position, Direction direction) {
        if (isExactlyOnTile(position)) {
            int emptyBlockCounter = 0;
            if (!(getNextUpElement(position) instanceof Bloc)) {
                emptyBlockCounter++;
            }
            if (!(getNextDownElement(position) instanceof Bloc)) {
                emptyBlockCounter++;
            }
            if (!(getNextRightElement(position) instanceof Bloc)) {
                emptyBlockCounter++;
            }
            if (!(getNextLeftElement(position) instanceof Bloc)) {
                emptyBlockCounter++;
            }

            return (emptyBlockCounter >= 3 || (getNextElement(position, direction) instanceof Bloc));
        }

        else {
            return false;
        }
    }

    public boolean isExactlyOnTile(Vector2D position) {
        return position.getX() % 100 == 0 && position.getY() % 100 == 0;
    }

    public void fixPosition(MovableGameElement element) {
        switch (element.getCurrentDirection()) {
            case LEFT:
                if (element.getPosition().getX() / ((float) mCoef)
                        - (element.getPosition().getX() / mCoef) < epsilon) {
                    element.getPosition().setX(element.getPosition().getX() / mCoef * mCoef);
                }
                break;
            case RIGHT:
                if ((element.getPosition().getX() / mCoef) + 1
                        - element.getPosition().getX() / ((float) mCoef) < epsilon) {
                    element.getPosition().setX(((element.getPosition().x / mCoef) + 1) * mCoef);
                }
                break;
            case UP:
                if (element.getPosition().y / ((float) mCoef)
                        - (element.getPosition().y / mCoef) < epsilon) {
                    element.getPosition().setY((element.getPosition().y / mCoef) * mCoef);
                }
                break;
            case DOWN:
                if ((element.getPosition().y / mCoef) + 1
                        - element.getPosition().y / ((float) mCoef) < epsilon) {
                    element.getPosition().setY(((element.getPosition().y / mCoef) + 1) * mCoef);
                }
                break;
        }
    }


    public ElementJeu getElementAtPosition(ElementJeu element) {
        int x = element.getPosition().getX() / mCoef;
        int y = element.getPosition().getY() / mCoef;
        return mWorld.getMaze().getBlock(x, y);
    }

    public LabyrintheElement getElementAtPosition(Vector2D position) {
        int x = position.getX() / mCoef;
        int y = position.getY() / mCoef;
        return mWorld.getMaze().getBlock(x, y);
    }

    public Vector2D getExactPosition(Vector2D position) {
        return new Vector2D(position.getX() / mCoef, position.getY() / mCoef);
    }

    public boolean isOnSameTile(Vector2D position1, Vector2D position2) {
        return getExactPosition(position1).equals(getExactPosition(position2));
    }

    public void checkTunnel(MovableGameElement movableGameElement) {

        if ((movableGameElement.getPosition().x / mCoef) == mWorld.getMaze().getWidth() - 1) {
            movableGameElement.setPosition(new Vector2D(0, movableGameElement.getPosition().y));
        }

        if ((movableGameElement.getPosition().x / mCoef) == 0
                && movableGameElement.getCurrentDirection() == Direction.LEFT) {
            movableGameElement.setPosition(new Vector2D(27 * mCoef, movableGameElement.getPosition().y));
        }
    }

    public void eatPellet(ElementJeu gameElement) {
        Vector2D position = new Vector2D(gameElement.getPosition().getX() / mCoef,
                gameElement.getPosition().getY() / mCoef);

        Vector2D gameElementPosition = gameElement.getPosition();
        mWorld.getMaze().setBlock(new Vide(gameElementPosition, mWorld),
                position.getX(), position.getY());

        long timeElapsed = TimeUtils.timeSinceMillis(mWorld.getStartTime());
        int baseScore;
        int timeMalus = (int) (timeElapsed / 1000);
        int frightenedDuration = 5;
        if (gameElement instanceof SuperPellet) {
            baseScore = 100;
            for (Ghosts ghost : mWorld.getGhosts()) {
                if (ghost.isAlive()) {
                    ghost.setFrightenedTimer(frightenedDuration);
                }
            }
        }
        else {
            baseScore = 10;
        }
        mWorld.winPoint(baseScore - timeMalus);
        mWorld.getMaze().decreasePelletNumber();
    }

    public void checkWantedDirection(MovableGameElement pacman, Direction wantedDirection) {
        ElementJeu nextBlock = getNextElement(pacman.getPosition(), pacman.getWantedDirection());

        switch (wantedDirection) {
            case LEFT:
            case RIGHT:
                if (pacman.getPosition().getY() % 100 == 0) {
                    if (!(nextBlock instanceof Bloc)) {
                        pacman.setCurrentDirection(wantedDirection);
                    }
                }
                break;
            case UP:
                if (pacman.getPosition().getX() % 100 == 0) {
                    if (!(nextBlock instanceof Bloc)) {
                        pacman.setCurrentDirection(wantedDirection);
                    }
                }
                break;
            case DOWN:
                if (pacman.getPosition().getX() % 100 == 0) {
                    if (!(nextBlock instanceof Bloc) && !(nextBlock instanceof GhostsHouse)) {
                        pacman.setCurrentDirection(wantedDirection);
                    }
                }
                break;
        }
    }
    
    public void checkWanteFireDirection(MovableGameElement fire, Direction wantedDirection) {
        ElementJeu nextBlock = getNextElement(fire.getPosition(), fire.getWantedDirection());

        switch (wantedDirection) {
            case LEFT:
            	if (fire.getPosition().getY() % 100 == 0) {
                    if (!(nextBlock instanceof Bloc)) {
                        fire.setCurrentDirection(Direction.LEFT);
                    }
                }
                break;
            case RIGHT:
                if (fire.getPosition().getY() % 100 == 0) {
                    if (!(nextBlock instanceof Bloc)) {
                        fire.setCurrentDirection(Direction.RIGHT);
                    }
                }
                break;
            case UP:
                if (fire.getPosition().getX() % 100 == 0) {
                    if (!(nextBlock instanceof Bloc)) {
                        fire.setCurrentDirection(Direction.UP);
                    }
                }
                break;
            case DOWN:
                if (fire.getPosition().getX() % 100 == 0) {
                    if (!(nextBlock instanceof Bloc) && !(nextBlock instanceof GhostsHouse)) {
                        fire.setCurrentDirection(Direction.DOWN);
                    }
                }
                break;
        }
    }


    public abstract void moveElement(MovableGameElement movableGameElement, float deltaTime);

    public abstract void updateEpsilon(float deltaTime);

    public void trouverPlusCourtChemin(Vector2D positionObjective) {
        for (ElementJeu gameElement : mWorld.getMaze()) {
            if (gameElement instanceof LabyrintheElement) {
                LabyrintheElement mazeElement = (LabyrintheElement) gameElement;
                mazeElement.setShortestPathLabel(Integer.MAX_VALUE);
            }
        }
        Queue<LabyrintheElement> blockQueue = new LinkedList<LabyrintheElement>();
        LabyrintheElement beginningBlock = getElementAtPosition(positionObjective);
        LabyrintheElement currentBlock;
        LinkedList<LabyrintheElement> neighboursBlocks;
        int label = 0;

        blockQueue.add(beginningBlock);
        beginningBlock.setShortestPathLabel(label);

        while (!blockQueue.isEmpty()) {
            currentBlock = blockQueue.remove();
            label = currentBlock.getShortestPathLabel() + 1;
            neighboursBlocks = getAvailableBlocks(currentBlock.getPosition());
            for (LabyrintheElement neighbour : neighboursBlocks) {
                if (neighbour.getShortestPathLabel() == Integer.MAX_VALUE) {
                    blockQueue.add(neighbour);
                    neighbour.setShortestPathLabel(label);
                }
            }

        }
    }
}
