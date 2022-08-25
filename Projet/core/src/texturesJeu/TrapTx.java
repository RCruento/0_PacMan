package texturesJeu;

import com.badlogic.gdx.graphics.Texture;


public class TrapTx extends TextureWrapper {

	private Texture[] trapTab;
	private float time = 0;
	private int state = 1;
	
	public TrapTx() {
		trapTab = new Texture[6];
		trapTab[0]= new Texture("trapclosed.png");
		trapTab[1] = new Texture("trap1.png");
		trapTab[2] = new Texture("trap2.png");
		trapTab[3] = new Texture("trap3.png");
		trapTab[4] = new Texture("trap4.png");
		trapTab[5] = new Texture("trapopened.png");
		
	}
		
	public Texture getTrapTab(int i) {
		return trapTab[i];
	}
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		time += deltaTime;
        state = (int) ((time*2)  % 6);
		
	}

	@Override
	public Texture getTexture() {
		// TODO Auto-generated method stub
		return trapTab[state];
	}

}
