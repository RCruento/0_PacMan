package controlleur;

import java.util.HashMap;
import java.util.Map;

import modelesJeu.ElementJeu;
import modelesJeu.Ghosts;
import modelesJeu.GhostsHouse;
import modelesJeu.MovableGameElement;
import modelesJeu.Vector2D;
import modelesJeu.MovableGameElement.Direction;

public class DeadGhostAI extends FantomeDep {
    public DeadGhostAI(Ghosts ghost) {
        super(ghost);
    }

    @Override
    public void setDirection(MovableGameElement movableGameElement) {
        if (mGhost.getMovementController().getElementAtPosition(mGhost) instanceof
        		GhostsHouse) {
            mGhost.setAlive(true);
            mGhost.switchToOutAI();
        }
        if (!mGhost.isAlive()) {
            HashMap<Direction, ElementJeu> availableDirections;
            Vector2D position = movableGameElement.getPosition();

            double min = Double.MAX_VALUE;
            Direction resDirection = null;

            availableDirections = ((FantomeDeplacemControl) mMovementController).getPossibleDirections(
                    mGhost,
                    movableGameElement.getCurrentDirection());


            for (Map.Entry<Direction, ElementJeu> entry : availableDirections.entrySet()) {
                double distance = mMovementController.getDistance(entry.getValue().getPosition(),
                        mGhost.getStartingPos());
                if (distance < min) {
                    resDirection = entry.getKey();
                    min = distance;
                }
            }

            if (resDirection == null) {
                throw new RuntimeException("No valid direction");
            }

            if (position.getX() % 100 == 0 && position.getY() % 100 == 0) {
                movableGameElement.setWantedDirection(resDirection);
            }
        }
    }
}
