package texturesJeu;

import com.badlogic.gdx.graphics.Texture;

import modelesJeu.ElementJeu;



public abstract class TextureWrapper implements ITexturable {

    protected ElementJeu wrappedObject;

    public TextureWrapper() {
        this.wrappedObject = null;
    }

    public ElementJeu getWrappedObject() {
        return wrappedObject;
    }


    public void setWrappedObject(ElementJeu wrappedObject) {
        if (wrappedObject == null) {
            throw new IllegalArgumentException("The wrapped object can't be null");
        }
        this.wrappedObject = wrappedObject;
    }

    public abstract void update(float deltaTime);

    public void resetWrapper() {
        this.wrappedObject = null;
    }
    @Override
    public abstract Texture getTexture();
}
