package texturesJeu;

import com.badlogic.gdx.graphics.Texture;

import modelesJeu.ElementJeu;
import modelesJeu.Ghosts;

public class GhostTextureWrapper extends TextureWrapper {
    public Texture normalTexture;
    public Texture frightenedTexture = new Texture("ghostEscaping.png");
    public Texture deadTexture = new Texture("ghostDead.png");
    public float frightenedTimer = 0;

    public GhostTextureWrapper(Texture defaultTexture) {
        super();
        setNormalTexture(defaultTexture);
    }

    public void setNormalTexture(Texture normalTexture) {
        if (normalTexture == null) {
            throw new IllegalArgumentException("NULL texture");
        }
        this.normalTexture = normalTexture;
    }

    public void setFrightenedTimer(float frightenedTimer) {
        this.frightenedTimer = frightenedTimer;
    }

    public void update(float deltaTime) {
        if (((Ghosts) wrappedObject).getFrightenedTimer() > 0) {
            ((Ghosts) wrappedObject).decreaseFrightenedTimer(deltaTime);
        }
    }

    @Override
    public void setWrappedObject(ElementJeu wrappedObject) {
        super.setWrappedObject(wrappedObject);

        if (!(wrappedObject instanceof Ghosts)) {
            throw new IllegalArgumentException("GhostTextureWrapper's wrapped object should" +
                    " be a ghost.");
        }
    }

    @Override
    public Texture getTexture() {
        if (((Ghosts) wrappedObject).getFrightenedTimer() > 0) {
            return frightenedTexture;
        }
        if (!((Ghosts) wrappedObject).isAlive()) {
            return deadTexture;
        }
        return normalTexture;
    }
}
