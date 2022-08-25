package controlleur;

import static modelesJeu.MovableGameElement.Direction.RIGHT;

import modelesJeu.BasicPellet;
import modelesJeu.Bloc;
import modelesJeu.ElementJeu;
import modelesJeu.Ghosts;
import modelesJeu.GhostsHouse;

import modelesJeu.MovableGameElement;
import modelesJeu.SuperPellet;
import modelesJeu.Vector2D;
import modelesJeu.World;




public class PacmanMoveController extends MovementController {
    public PacmanMoveController(World world) {
        super(world);
        epsilon = (world.getPacman().getSpeed() / 6000f);
    }

    @Override
    public void moveElement(MovableGameElement movableGameElement, float deltaTime) {
        checkPacmanGhostCollision();
        checkTunnel(movableGameElement);   

        checkWantedDirection(movableGameElement, movableGameElement.getWantedDirection());

        ElementJeu nextBlock = getNextElement(movableGameElement.getPosition(),
                movableGameElement.getCurrentDirection());

        if (!(nextBlock instanceof Bloc) && !(nextBlock instanceof GhostsHouse)) {
            movableGameElement.updatePosition(deltaTime);
        }

        fixPosition(movableGameElement);

        Vector2D currentPosition = movableGameElement.getPosition();
        ElementJeu currentGameElement = mWorld.getMaze().getBlock(
                (currentPosition.getX() + 50) / mCoef,
                (currentPosition.getY() + 50) / mCoef);


        if (currentGameElement instanceof BasicPellet
                || currentGameElement instanceof SuperPellet) {
            eatPellet(currentGameElement);
        }


    }



    public void checkPacmanGhostCollision() {
        Vector2D pacmanPosition = mWorld.getPacman().getPosition();
        for (Ghosts ghost : mWorld.getGhosts()) {
            if (isOnSameTile(pacmanPosition, ghost.getPosition())) {
                resolveCollision(ghost);
            }
        }
    }
    
    public void resolveCollision(Ghosts ghost) {
        if (ghost.isFrightened()) {
            ghost.setFrightenedTimer(0);
            ghost.switchToDeadAI();
            ghost.setAlive(false);
            mWorld.winPoint(500);
        }
        else if (ghost.isAlive()){
            World.decreaseLifeCounter();
            mWorld.getPacman().resetPosition();
            mWorld.getPacman().setDeadCounter(2);
            mWorld.getPacman().setCurrentDirection(RIGHT);
            for (Ghosts ghost2 : mWorld.getGhosts()) {
                ghost2.resetPosition();
                ghost2.setAlive(true);
                ghost2.switchToOutAI();
            }
        }
    }

    public void updateEpsilon(float deltaTime) {
        float fps = 1 / deltaTime;
        float newEpsilon = mWorld.getPacman().getSpeed() / (fps * World.getCoef());
        if (newEpsilon < 0.5) {
            epsilon = newEpsilon;
        }
        else {
            epsilon = 0.45;
        }
    }
}
