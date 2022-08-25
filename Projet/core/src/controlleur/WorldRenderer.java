package controlleur;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ecrans.FinEcran;
import modelesJeu.Fire;
import modelesJeu.ElementJeu;
import modelesJeu.Ghosts;
import modelesJeu.Pacman;
import modelesJeu.Vector2D;
import modelesJeu.World;
import modelesJeu.MovableGameElement.Direction;
import texturesJeu.TextureFactory;


public class WorldRenderer implements InputProcessor {
    float size;
    private final SpriteBatch batch;
    private final TextureFactory textureFactory;
    private final World mWorld;
    private final Game mGame;

    public WorldRenderer(World world, Game game) {
        textureFactory = TextureFactory.getInstance();
        batch = new SpriteBatch();
        mWorld = world;
        Gdx.input.setInputProcessor(this);
        mWorld.getMaze().loadDemoLevel(World.getCoef());
        mGame = game;

    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getSize() {
        return size;
    }


    public void render(OrthographicCamera camera, float deltaTime) {

        updateEpsilons(deltaTime);
        checkEndGame();
        batch.setProjectionMatrix(camera.combined);
        drawWorld();
        textureFactory.update(deltaTime);
        if (!isPaused(deltaTime)) {
            moveGameElements(deltaTime);
        }
    }

    public boolean isPaused(float deltaTime) {
        Pacman pacman = mWorld.getPacman();
        if (pacman.getDeadCounter() > 0) {
            pacman.decreaseDeadCounter(deltaTime);
            return true;
        }
        if (mWorld.getGlobalPauseTimer() > 0) {
            mWorld.decreaseGlobalPauseTimer(deltaTime);
            return true;
        }
        return false;
    }

    public void setLag(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateEpsilons(float deltaTime) {
        mWorld.getPacman().getMovementController().updateEpsilon(deltaTime);
        for (Ghosts ghost : mWorld.getGhosts()) {
            ghost.getMovementController().updateEpsilon(deltaTime);
        }
    }

    public void checkEndGame() {
        if (mWorld.getMaze().getPelletNumber() == 0) {
            Gdx.app.log(WorldRenderer.class.getSimpleName(), "Vous avez gagné !");
            mGame.setScreen(new FinEcran(true, mWorld.getScore(), mWorld, mGame));
        }
        else if (World.getLifeCounter() <= 0) {
            Gdx.app.log(getClass().getSimpleName(), "Vous avez perdu !");
            mGame.setScreen(new FinEcran(false, mWorld.getScore(), mWorld, mGame));
        }
    }

    public void drawWorld() {
        batch.begin();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for (ElementJeu e : mWorld) {
            Vector2D position = e.getPosition();
            Texture texture = textureFactory.getTexture(e);
            batch.draw(texture,
                    ((position.x / ((float) World.getCoef())) - mWorld.getMaze().getWidth() / 2f) * size,
                    ((position.y / ((float) World.getCoef())) - mWorld.getMaze().getHeight() / 2f) * size,
                    size, size,
                    0, 0,
                    texture.getWidth(), texture.getHeight(), false, true);
        }
        batch.end();
    }


    public void moveGameElements(float deltaTime) {
        mWorld.getPacman().move(deltaTime);
        for (Ghosts ghost : mWorld.getGhosts()) {
            ghost.useAI();
            ghost.move(deltaTime);
        }
        if(mWorld.getFire().getMovementController()!=null) {
        	
        	mWorld.getFire().move(deltaTime);
        	mWorld.removeElementArray(mWorld.getFire());
        	mWorld.getFire().finalize();
        }
       
    }


    @Override
    public boolean keyDown(int keycode) {
        Pacman pacman = mWorld.getPacman();
       
        switch (keycode) {
            case Input.Keys.LEFT:
                pacman.setWantedDirection(Direction.LEFT);
                break;
            case Input.Keys.RIGHT:
                pacman.setWantedDirection(Direction.RIGHT);
                break;
            case Input.Keys.UP:
                pacman.setWantedDirection(Direction.UP);
                break;
            case Input.Keys.DOWN:
                pacman.setWantedDirection(Direction.DOWN);
                break;
            case Input.Keys.T :
            	if(mWorld.getFire()==null) {
            				mWorld.setFire( new Fire(pacman.getPosition(), mWorld, 580, null));
            				Fire fire = mWorld.getFire();
	            	fire.setMovementController(new FireMovement(mWorld));
	            	fire.setPosition(pacman.getPosition());
	            	fire.setCurrentDirection(pacman.getCurrentDirection());
	            	mWorld.addElementArray(mWorld.getFire());
            	}
            	
            	
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
