package modelesJeu;

import controlleur.AleaDepla;
import controlleur.DeadGhostAI;
import controlleur.FantomeDep;
import controlleur.QuitterAbrit;


public abstract class Ghosts extends MovableGameElement {

    private FantomeDep usedAI = null;
    private FantomeDep defaultAI = null;
    private FantomeDep frightenedAI = null;
    private FantomeDep outOfHouseAI = null;
    private FantomeDep deadAI = null;
    private boolean alive = true;
    private float mFrightenedTimer = 0;
    private int frightenedSpeed = 100;
    private int normalSpeed;
    private Vector2D startingPos;

    protected Ghosts(Vector2D position, World world, int speed) {
        super(position, world, speed);
        normalSpeed = speed;
        startingPos = new Vector2D(position);
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Vector2D getStartingPos() {
        return startingPos;
    }

    public void initAI(FantomeDep defaultAI) {
        this.defaultAI = defaultAI;
        frightenedAI = new AleaDepla(this);
        outOfHouseAI = new QuitterAbrit(this);
        deadAI = new DeadGhostAI(this);
        usedAI = outOfHouseAI;
    }

    public void switchToDeadAI() {
        usedAI = deadAI;
    }

    public void switchToFrightenedAI() {
        usedAI = frightenedAI;
    }

    public void switchToOutAI() {
        usedAI = outOfHouseAI;
    }

    public void switchToDefaultAI() {
        usedAI = defaultAI;
    }

    public FantomeDep getUsedAI() {
        return usedAI;
    }

    public void setUsedAI(FantomeDep usedAI) {
        if (usedAI == null) {
            throw new IllegalArgumentException("The AI can't be null");
        }
        this.usedAI = usedAI;
    }

    public FantomeDep getDefaultAI() {
        return defaultAI;
    }

    public void useAI() {
        usedAI.setDirection(this);
    }

    public void setFrightenedTimer(float frightenedTimer) {
        mFrightenedTimer = frightenedTimer;
        if (frightenedTimer > 0) {
            switchToFrightenedAI();
            setSpeed(frightenedSpeed);
        }
        else if (frightenedTimer == 0) {
            setSpeed(normalSpeed);
        }
    }

    public void decreaseFrightenedTimer(float time) {
        mFrightenedTimer -= time;
        if (mFrightenedTimer < 0) {
            mFrightenedTimer = 0;
            switchToDefaultAI();
            setSpeed(normalSpeed);
        }
    }

    public float getFrightenedTimer() {
        return mFrightenedTimer;
    }

    public boolean isFrightened() {
        return mFrightenedTimer > 0;
    }
}
