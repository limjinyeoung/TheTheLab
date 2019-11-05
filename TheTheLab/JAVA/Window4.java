import processing.core.PApplet;

public class Window4 extends PApplet {

    public static void main(String[] args) {
        PApplet.main("Window4");
    }

    public void settings() {
        size(960, 640);
    }

    public void setup() {
        // 원충돌 어떻게 할까?

    }

    int x = 480, y = 320;
    int r = 100;

    public void draw() {
        background(0);
        fill(255);
        ellipse(x, y, r, r);

        ellipse(mouseX, mouseY, 20, 20);

        if (Math.sqrt(Math.pow(mouseX - x, 2) + Math.pow(mouseY - y, 2)) < (r + 20) / 2) {
            System.out.println("Col!");
        }
    }
}

