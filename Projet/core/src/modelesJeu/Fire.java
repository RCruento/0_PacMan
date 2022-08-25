package modelesJeu;

import controlleur.MovementController;

public class Fire extends MovableGameElement{

	private boolean exist = false;
	
	
	public Fire(Vector2D startPosition, World world, int speed,MovementController movementController) {
		super(startPosition, world, speed);
		setCurrentDirection(world.getPacman().getCurrentDirection());
		setWantedDirection(Direction.LEFT);
	}
	
	public void finalize() {}

	public boolean isExist() {
		return exist;
	}

	public void setExist(boolean exist) {
		this.exist = exist;
	}


	
}
