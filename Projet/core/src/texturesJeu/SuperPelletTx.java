package texturesJeu;

import com.badlogic.gdx.graphics.Texture;

public class SuperPelletTx extends TextureWrapper {
    private float time;
    private int state;

    private Texture[] mTextures;

    public SuperPelletTx() {
        mTextures = new Texture[2];
        mTextures[0] = new Texture("superpellet.png");
        mTextures[1] = new Texture("superpellet-2.png");
    }

    @Override
    public void update(float deltaTime) {
        time += deltaTime;
        state = (int) (time * 5) % 2;
    }

    @Override
    public Texture getTexture() {
        return mTextures[state];
    }
}
