import processing.core.PApplet;

public class ExamplePoint extends ExampleBaseObject{
    public float radius;

    public ExamplePoint(PApplet p) {
        super(p);
    }

    public void update(){
        ax += Math.random() * 0.1f - 0.05f;
        ay += Math.random() * 0.1f - 0.05f;
        vx += ax;
        vy += ay;

        x += vx;
        y += vy;
    }

    public void render() {

        pApplet.ellipse(x, y, radius, radius);


    }
}