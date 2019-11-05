import processing.core.PApplet;

public abstract class ExampleBaseObject implements ExampleObjectInterface {
    protected PApplet pApplet;
    protected float x, y;

    protected float vx, vy, ax, ay;

    public ExampleBaseObject(PApplet p) {
        this.pApplet = p;
    }
}
