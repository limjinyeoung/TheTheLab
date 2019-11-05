import processing.core.PApplet;

public abstract class BaseObject implements ObjectInterface {
    protected PApplet pApplet;
    protected float x, y;

    protected float vx, vy, ax, ay;

    public BaseObject(PApplet p, float x, float y) {
        this.pApplet = p;
        this.x = x;
        this.y = y;
    }
}
