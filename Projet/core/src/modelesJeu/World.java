package modelesJeu;

import java.util.ArrayList;
import java.util.Iterator;
import com.badlogic.gdx.utils.TimeUtils;
import controlleur.AleaDepla;
import controlleur.ChangementDirect;
import controlleur.CourtChemin;
import controlleur.FantomeDeplacemControl;
import controlleur.PacmanMoveController;
import controlleur.RecherchePacman;

public class World implements Iterable<ElementJeu> {
    private int width = 30;
    private int height = 35;
    private Trap trap;
    private final Pacman mPacman;
    private Fire fire = null;
    private final ArrayList<Ghosts> mGhosts = new ArrayList<Ghosts>();
    private Labyrinthe mMaze;
    private final static int mCoef = 100;
    private int score = 0;
    long startTime;
    private ArrayList<ElementJeu> mGameElements;
    private static int lifeCounter = 3;
    public static final int maxLife = 3;
    private float globalPauseTimer = 4;
    public static Vector2D pacmanStartingPosition = new Vector2D(14 * mCoef, 17 * mCoef);
    public static Vector2D redGhostStartingPos = new Vector2D(14 * mCoef, 13 * mCoef);
    public static Vector2D blueGhostStartingPos = new Vector2D(14 * mCoef, 14 * mCoef);
    public static Vector2D yellowGhostStartingPos = new Vector2D(13 * mCoef, 13 * mCoef);
    public static Vector2D pinkGhostStartingPos = new Vector2D(13 * mCoef, 14 * mCoef);


    public World() {
        mPacman = new Pacman(new Vector2D(pacmanStartingPosition), this,
                500, null);
        mPacman.setMovementController(new PacmanMoveController(this));
            
        mMaze = new Labyrinthe(this);
        startTime = TimeUtils.millis();
        setLifeCounter(maxLife);
    }

    public static Vector2D getPacmanStartingPosition() {
        return pacmanStartingPosition;
    }

    public static int getLifeCounter() {
        return lifeCounter;
    }

    public static void setLifeCounter(int lifeCounter) {
        World.lifeCounter = lifeCounter;
    }

    public static void decreaseLifeCounter() {
        lifeCounter--;
        if (lifeCounter <= 0) {
            lifeCounter = 0;
        }
    }

    public void createGhosts() {
        GhostR redGhost = new GhostR(new Vector2D(redGhostStartingPos), this, 500);
        GhostY yellowGhost = new GhostY(new Vector2D(yellowGhostStartingPos), this, 500);
        GhostP pinkGhost = new GhostP(new Vector2D(pinkGhostStartingPos), this, 500);
        GhostB blueGhost = new GhostB(new Vector2D(blueGhostStartingPos), this, 500);

        mGameElements = new ArrayList<ElementJeu>();
        mGameElements.add(mPacman);
        mGameElements.add(redGhost);
        mGameElements.add(yellowGhost);
        mGameElements.add(pinkGhost);
        mGameElements.add(blueGhost);
        mGhosts.add(pinkGhost);
        mGhosts.add(yellowGhost);
        mGhosts.add(redGhost);
        mGhosts.add(blueGhost);

        for (Ghosts ghost : mGhosts) {
            ghost.setMovementController(new FantomeDeplacemControl(this, ghost));
        }

        pinkGhost.initAI(new ChangementDirect(pinkGhost));
        yellowGhost.initAI(new RecherchePacman(yellowGhost));
        redGhost.initAI(new AleaDepla(redGhost));
        blueGhost.initAI(new CourtChemin(blueGhost, mPacman));

    }
    
    public void addElementArray(ElementJeu x) {
    	mGameElements.add(x);
    }
    
    public void removeElementArray(ElementJeu x) {
    	for (int i = 0; i <= mGameElements.size(); ){
    		if(mGameElements.get(i).equals(x)) {
    			mGameElements.remove(i);
    		}
			break;
		}
    }
    public ArrayList<Ghosts> getGhosts() {
        return mGhosts;
    }

    public long getStartTime() {
        return startTime;
    }

    public int getScore() {
        return score;
    }

    public void winPoint(int wonPoint) {

    	score +=1 ;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Pacman getPacman() {
        return mPacman;
    }

    public Fire getFire() {
    	return this.fire;
    }
    public Trap getTrap() {
    	return trap;
	}

    public Labyrinthe getMaze() {
        return mMaze;
    }
    
    public void setFire(Fire feu) {
    	this.fire = feu;
    }

    @Override
    public Iterator<ElementJeu> iterator() {
        return new WorldIterator();
    }

    class WorldIterator implements Iterator<ElementJeu> {

        final Iterator<ElementJeu> mazeIterator = mMaze.iterator();
        final Iterator<ElementJeu> mGameElementIterator = mGameElements.iterator();

        @Override
        public boolean hasNext() {
            return mazeIterator.hasNext() || mGameElementIterator.hasNext();
        }

        @Override
        public ElementJeu next() {
            if (mazeIterator.hasNext()) {
                return mazeIterator.next();
            }
            return mGameElementIterator.next();
        }

        @Override
        public void remove() {
                throw new UnsupportedOperationException("Remove is not supported.");
        }
    }

    public static int getCoef() {
        return mCoef;
    }

    public float getGlobalPauseTimer() {
        return globalPauseTimer;
    }

    public void setGlobalPauseTimer(float globalPauseTimer) {
        this.globalPauseTimer = globalPauseTimer;
    }

    public void decreaseGlobalPauseTimer(float deltaTime) {
        globalPauseTimer -= deltaTime;
        if (globalPauseTimer < 0) {
            globalPauseTimer = 0;
        }
    }
}
