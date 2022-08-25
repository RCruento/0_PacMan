package controlleur;


import modelesJeu.Ghosts;
import modelesJeu.MovableGameElement;


public class ChangementDirect extends FantomeDep {
    private FantomeDep usedAI;
    private FantomeDep randomAI;
    private FantomeDep searchAI;

    public ChangementDirect(Ghosts ghost) {
        super(ghost);
        randomAI = new AleaDepla(ghost);
        searchAI = new RecherchePacman(ghost);
        usedAI = searchAI;
    }

    @Override
    public void setDirection(MovableGameElement movableGameElement) {
        if (mMovementController.estAIntersection(movableGameElement.getPosition(),
                movableGameElement.getCurrentDirection())) {
            usedAI.setDirection(movableGameElement);
            if (usedAI instanceof RecherchePacman) {
                usedAI = randomAI;
            }
            else {
                usedAI = searchAI;
            }
        }
    }
}
