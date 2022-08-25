package controlleur;

import java.util.Map;

import modelesJeu.ElementJeu;
import modelesJeu.Ghosts;
import modelesJeu.LabyrintheElement;
import modelesJeu.MovableGameElement;
import modelesJeu.MovableGameElement.Direction;

public class CourtChemin extends FantomeDep {
    private ElementJeu cible;
    public CourtChemin(Ghosts ghost) {
        super(ghost);
    }

    public CourtChemin(Ghosts ghost, ElementJeu cible) {
        super(ghost);
        this.cible = cible;
    }

    public ElementJeu getCible() {
        return this.cible;
    }

    public void setCible(ElementJeu cible) {
        this.cible = cible;
    }

    @Override
    public void setDirection(MovableGameElement movableGameElement) {
        if (mMovementController.estAIntersection(movableGameElement.getPosition(),
                movableGameElement.getCurrentDirection())) {
            mMovementController.trouverPlusCourtChemin(cible.getPosition());
            Map.Entry<Direction, LabyrintheElement> result = mMovementController.getBlockWithSmallestLabel(
                    movableGameElement.getPosition(),
                    movableGameElement.getCurrentDirection()
            );
            movableGameElement.setWantedDirection(result.getKey());
        }
    }
}
