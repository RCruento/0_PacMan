package modelesJeu;


public abstract class ElementJeu {

    protected Vector2D mPosition;
    protected World mWorld;
    protected ElementJeu(Vector2D position, World world) {
        setPosition(position);
        setWorld(world);
    }

    public Vector2D getPosition() {
        return mPosition;
    }

    public void setPosition(Vector2D position) {
        if (position == null)
            throw new IllegalArgumentException("Position cannot be null");
        this.mPosition = position;
    }

    public void setWorld(World world) {
        if (world == null)
            throw new IllegalArgumentException("World cannot be null");
        this.mWorld = world;
    }
}
