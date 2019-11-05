import processing.core.PApplet;
import processing.data.JSONObject;

public abstract class View {
    protected int x, y;
    protected int width, height;
    protected PApplet pApplet;

    public View(PApplet pApplet, int x, int y, int width, int height) {
        this.pApplet = pApplet;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void update() {

    }

    public abstract void render();

    public abstract void clicked();

    public void mouseClicked(int x, int y) {
        if (isCollision(x, y)) clicked();
    }

    private boolean isCollision(int x, int y) {
        return x > this.x && x < this.x + this.width
                && y > this.y && y < this.y + this.height;
    }


}
