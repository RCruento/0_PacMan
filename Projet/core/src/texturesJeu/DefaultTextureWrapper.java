package texturesJeu;

import com.badlogic.gdx.graphics.Texture;


public class DefaultTextureWrapper implements ITexturable {

    private Texture mDefaultTexture;

    public DefaultTextureWrapper(Texture defaultTexture) {
        setDefaultTexture(defaultTexture);
    }

    public void setDefaultTexture(Texture defaultTexture) {
        if (defaultTexture == null) {
            throw new IllegalArgumentException("defaultTexture can't be null");
        }
        this.mDefaultTexture = defaultTexture;
    }

    @Override
    public Texture getTexture() {
        return mDefaultTexture;
    }
}
