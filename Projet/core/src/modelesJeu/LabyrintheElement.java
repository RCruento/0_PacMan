package modelesJeu;



public class LabyrintheElement extends ElementJeu {
    private int shortestPathLabel;
    
    protected LabyrintheElement(Vector2D position, World world) {
        super(position, world);
    }

    public int getShortestPathLabel() {
        return shortestPathLabel;
    }

    public void setShortestPathLabel(int shortestPathLabel) {
        this.shortestPathLabel = shortestPathLabel;
    }
}
