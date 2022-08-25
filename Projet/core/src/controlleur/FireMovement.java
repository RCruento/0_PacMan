package controlleur;

import static modelesJeu.MovableGameElement.Direction.RIGHT;

import modelesJeu.Bloc;
import modelesJeu.ElementJeu;
import modelesJeu.Fire;
import modelesJeu.Ghosts;
import modelesJeu.GhostsHouse;
import modelesJeu.MovableGameElement;
import modelesJeu.Vector2D;
import modelesJeu.World;

public class FireMovement extends MovementController {

     
	public FireMovement(World world) {
		super(world);
		epsilon = (world.getFire().getSpeed()/ 6000f);
	}


	@Override
	public void moveElement(MovableGameElement movableGameElement, float deltaTime) {
		checkPacmanGhostCollision();
		checkTunnel(movableGameElement); 
		checkWanteFireDirection(movableGameElement, mWorld.getPacman().getCurrentDirection());
		 ElementJeu nextBlock = getNextElement(movableGameElement.getPosition(),
	                movableGameElement.getCurrentDirection());
		 if (!(nextBlock instanceof Bloc) && !(nextBlock instanceof GhostsHouse)) {
	            movableGameElement.updatePosition(deltaTime*2);
	        }
		 else {
			 if(movableGameElement instanceof Fire) {
				 mWorld.getFire().finalize();
			 }
		 }
	}

	 public void checkPacmanGhostCollision() {
	        Vector2D firePos = mWorld.getFire().getPosition();
	        for (Ghosts ghost : mWorld.getGhosts()) {
	            if (isOnSameTile(firePos, ghost.getPosition())) {
	                resolveFireCollision(ghost);
	            }
	        }
	    }
	    
	    public void resolveFireCollision(Ghosts ghost) {
	      
	            ghost.setFrightenedTimer(0);
	            ghost.switchToDeadAI();
	            ghost.setAlive(false);
	            mWorld.winPoint(500);
	    }

	    public void updateEpsilon(float deltaTime) {
	        float fps = 1 / deltaTime;
	        float newEpsilon = mWorld.getFire().getSpeed() / (fps * World.getCoef());
	        if (newEpsilon < 0.5) {
	            epsilon = newEpsilon;
	        }
	        else {
	            epsilon = 0.45;
	        }
	    }
	
	/*public void checkCanonFantomeCollision() {
        Vector2D canonPellet = mWorld.getPacman().getPosition();
        for (Fantomes ghost : mWorld.getGhosts()) {
            if (isOnSameTile(pacmanPosition, ghost.getPosition())) {
                resolveCollision(ghost);
            }
        }
    }*/

	

}
