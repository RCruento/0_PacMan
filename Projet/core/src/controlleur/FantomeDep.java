package controlleur;

import modelesJeu.Ghosts;
import modelesJeu.MovableGameElement;


public abstract class FantomeDep {
    protected MovementController mMovementController;
    protected Ghosts mGhost;

    public FantomeDep(Ghosts ghost) {
        mGhost = ghost;
        mMovementController = ghost.getMovementController();
    }

    public void setMovementController(MovementController movementController) {
        if (movementController == null) {
            throw new IllegalArgumentException("Movement controller can't be null");
        }
        mMovementController = movementController;
    }

    public abstract void setDirection(MovableGameElement movableGameElement);
}
