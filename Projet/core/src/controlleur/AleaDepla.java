package controlleur;

import modelesJeu.Ghosts;
import modelesJeu.MovableGameElement;
import modelesJeu.Vector2D;

public class AleaDepla extends FantomeDep {
    public AleaDepla(Ghosts ghost) {
        super(ghost);
    }

    @Override
    public void setDirection(MovableGameElement movableGameElement) {
        Vector2D position = movableGameElement.getPosition();
        if (mMovementController.estAIntersection(position,
                movableGameElement.getCurrentDirection())) {
            if (position.getX() % 100 == 0 && position.getY() % 100 == 0) {
                movableGameElement.setWantedDirection(MovableGameElement.Direction.randomDirection());
            }
        }
    }
}
