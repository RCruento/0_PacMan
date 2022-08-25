package controlleur;

import modelesJeu.Ghosts;
import modelesJeu.GhostsHouse;
import modelesJeu.MovableGameElement;
import modelesJeu.MovableGameElement.Direction;


public class QuitterAbrit extends FantomeDep {
    public QuitterAbrit(Ghosts ghost) {
        super(ghost);
    }

    @Override
    public void setDirection(MovableGameElement movableGameElement) {
        if (mMovementController.getElementAtPosition(movableGameElement) instanceof GhostsHouse) {
            movableGameElement.setWantedDirection(Direction.UP);
        }
        else {
            ((Ghosts) movableGameElement).switchToDefaultAI();
        }
    }
}
