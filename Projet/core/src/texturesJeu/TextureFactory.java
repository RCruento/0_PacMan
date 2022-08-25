package texturesJeu;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;

import modelesJeu.BasicPellet;
import modelesJeu.Bloc;
import modelesJeu.Fire;
import modelesJeu.ElementJeu;
import modelesJeu.GhostB;
import modelesJeu.GhostY;
import modelesJeu.GhostP;
import modelesJeu.GhostR;
import modelesJeu.GhostsHouse;
import modelesJeu.Pacman;
import modelesJeu.SuperPellet;
import modelesJeu.Trap;
import modelesJeu.Vide;


public class TextureFactory {

    private final Map<Class<?>, ITexturable> mTextureMap;
    private static TextureFactory instance;
    private Texture livesTexture = new Texture("pacmanRight.png");
    private TextureFactory() {
        Texture blocTexture = new Texture("bloc.png");
        Texture emptyTexture = new Texture("dark.png");
        Texture basicPelletTexture = new Texture("pellet.png");
        Texture redGhost = new Texture("ghost1.png");
        Texture pinkGhost = new Texture("ghost2.png");
        Texture blueGhost = new Texture("ghost3.png");
        Texture yellowGhost = new Texture("ghost4.png");
        mTextureMap = new HashMap<Class<?>, ITexturable>();
        mTextureMap.put(Pacman.class, new PacmanTx());
        mTextureMap.put(GhostR.class, new GhostTextureWrapper(redGhost));
        mTextureMap.put(GhostP.class, new GhostTextureWrapper(pinkGhost));
        mTextureMap.put(GhostB.class, new GhostTextureWrapper(blueGhost));
        mTextureMap.put(GhostY.class, new GhostTextureWrapper(yellowGhost));
        mTextureMap.put(Bloc.class, new DefaultTextureWrapper(blocTexture));
        mTextureMap.put(Vide.class, new DefaultTextureWrapper(emptyTexture));
        mTextureMap.put(BasicPellet.class, new DefaultTextureWrapper(basicPelletTexture));
        mTextureMap.put(GhostsHouse.class, new DefaultTextureWrapper(emptyTexture));
        mTextureMap.put(Trap.class, new TrapTx());
        mTextureMap.put(SuperPellet.class, new SuperPelletTx());
        mTextureMap.put(Fire.class, new FireTx());
    }

    public static TextureFactory getInstance() {
        if (instance == null) {
            instance = new TextureFactory();
        }
        return instance;
    }

    public void update(float deltaTime) {
        ((PacmanTx) mTextureMap.get(Pacman.class)).update(deltaTime);
        ((GhostTextureWrapper) mTextureMap.get(GhostP.class)).update(deltaTime);
        ((GhostTextureWrapper) mTextureMap.get(GhostR.class)).update(deltaTime);
        ((GhostTextureWrapper) mTextureMap.get(GhostY.class)).update(deltaTime);
        ((GhostTextureWrapper) mTextureMap.get(GhostB.class)).update(deltaTime);
        ((SuperPelletTx) mTextureMap.get(SuperPellet.class)).update(deltaTime);
        ((TrapTx) mTextureMap.get(Trap.class)).update(deltaTime);
        ((FireTx) mTextureMap.get(Fire.class)).update(deltaTime);
    }

    public Texture getTexture(ElementJeu element) {
        ITexturable iTexturable = mTextureMap.get(element.getClass());

        if (iTexturable instanceof TextureWrapper) {
            TextureWrapper textureWrapper = (TextureWrapper) iTexturable;
            if (textureWrapper.getWrappedObject() == null) {
                textureWrapper.setWrappedObject(element);
            }
        }
        return iTexturable.getTexture();
    }

    public Texture getLivesTexture() {
        return livesTexture;
    }

    public void resetTextureFactory() {
        for (ITexturable texturable : mTextureMap.values()) {
            if (texturable instanceof TextureWrapper) {
                ((TextureWrapper) texturable).resetWrapper();
            }
        }
    }
}
