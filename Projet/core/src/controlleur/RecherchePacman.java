package controlleur;

import java.util.HashMap;
import java.util.Map;

import modelesJeu.Ghosts;
import modelesJeu.LabyrintheElement;
import modelesJeu.MovableGameElement;
import modelesJeu.Pacman;
import modelesJeu.Vector2D;
import modelesJeu.MovableGameElement.Direction;

public class RecherchePacman extends FantomeDep {
    public RecherchePacman(Ghosts ghost) {
        super(ghost);
    }

    @Override
    public void setDirection(MovableGameElement movableGameElement) {
        if (mMovementController.estAIntersection(movableGameElement.getPosition(),
                movableGameElement.getCurrentDirection())) {
            HashMap<Direction, LabyrintheElement> availableDirections;
            Pacman pacman = mMovementController.getWorld().getPacman();
            Vector2D position = movableGameElement.getPosition();
            double min = Double.MAX_VALUE;
            Direction resDirection = null;

            availableDirections = mMovementController.getPossibleDirections(movableGameElement.getPosition(),
                    movableGameElement.getCurrentDirection());
            
            for (Map.Entry<Direction, LabyrintheElement> entry : availableDirections.entrySet()) {
                double distance = mMovementController.getDistance(entry.getValue().getPosition(),
                        pacman.getPosition());
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
