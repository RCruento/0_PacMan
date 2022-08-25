package texturesJeu;

import com.badlogic.gdx.graphics.Texture;

public class FireTx extends TextureWrapper {
	
	private float time;
    private int state;

    private Texture[] mTextures;

    public FireTx() {
        mTextures = new Texture[3];
        mTextures[0] = new Texture("fire1.png");
        mTextures[1] = new Texture("fire2.png");
        mTextures[2] = new Texture("fire3.png");
    }
	
	
    @Override
    public void update(float deltaTime) {
        time += deltaTime;
        state = (int) (time * 8) % 3;
    }

    @Override
    public Texture getTexture() {
        return mTextures[state];
    }

}
