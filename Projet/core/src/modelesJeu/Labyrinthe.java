package modelesJeu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;


public class Labyrinthe implements Iterable<ElementJeu> {

    private int width;
    private int height;
    private World world;
    private LabyrintheElement[][] blocks;
    private int pelletNumber = 0;

    public Labyrinthe(World world) {
        setWorld(world);
    }


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        if (width <= 0) {
            throw new IllegalArgumentException("The width of the maze cannot be negative.");
        }
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        if (height <= 0) {
            throw new IllegalArgumentException("The height of the maze cannot be negative.");
        }

        this.height = height;
    }

    public void setWorld(World world) {
        if (world == null) {
            throw new IllegalArgumentException("The mWorld cannot be empty.");
        }
        this.world = world;
    }


    public LabyrintheElement getBlock(int x, int y) {
        if (x >= width) {
            x = width - 1;
        }
        else if (x < 0) {
            x = 0;
        }
        if (x >= height) {
            y = height - 1;
        }
        else if (x < 0) {
            y = 0;
        }
        return blocks[x][y];
    }

    public void setBlock(LabyrintheElement gameElement, int x, int y) {
        blocks[x][y] = gameElement;
    }

    public int getPelletNumber() {
        return pelletNumber;
    }

    public void decreasePelletNumber() {
        if (pelletNumber > 0)
            pelletNumber--;
    }

    @Override
    public Iterator<ElementJeu> iterator() {

        return new Iterator<ElementJeu>() {
            private int x = 0;
            private int y = 0;

            @Override
            public boolean hasNext() {
                return x < Labyrinthe.this.width && y < Labyrinthe.this.height;
            }

            @Override
            public LabyrintheElement next() {
                LabyrintheElement gameElement;
                gameElement = Labyrinthe.this.blocks[x][y];
                x++;
                if (x >= Labyrinthe.this.width) {
                    x = 0;
                    y++;
                }
                return gameElement;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }


    public void loadDemoLevel(int coeff) {
        setWidth(28);
        setHeight(31);
        blocks = new LabyrintheElement[width][height];
        FileHandle handle = Gdx.files.internal("levels/lvl.txt");
        InputStream stream = handle.read();
        try {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    char c = (char) stream.read();
                    if (c == '\n')
                        c = (char) stream.read();

                    if (c == '0')
                        blocks[x][y] = new Bloc(new Vector2D(x * coeff, y * coeff), world);
                    else if (c == '1')
                        blocks[x][y] = new Vide(new Vector2D(x * coeff, y * coeff), world);
                    else if (c == '2') {
                        blocks[x][y] = new BasicPellet(new Vector2D(x * coeff, y * coeff), world);
                        pelletNumber++;
                    }
                    else if (c == '3') {
                        blocks[x][y] = new SuperPellet(new Vector2D(x * coeff, y * coeff), world);
                        pelletNumber++;
                    }
                    else if (c == '4') {
                        blocks[x][y] = new GhostsHouse(new Vector2D(x * coeff, y * coeff), world);
                    }
                    else if (c == '5') {
                        blocks[x][y] = new Trap(new Vector2D(x * coeff, y * coeff), world);
                    }
                    else
                        blocks[x][y] = new Vide(new Vector2D(x * coeff, y * coeff), world);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
