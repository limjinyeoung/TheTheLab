import processing.core.PApplet;

public class Window3 extends PApplet {

    public static void main(String[] args) {
        PApplet.main("Window3");
    }

    public void settings() {
        size(960, 640);
    }

    public final int RectSize = 100;
    public final int CircleSize = 100;
    public int[] rectXs;
    public int[] rectYs;
    public int[] hues;
    public int[] lifes;
    public int[] circleLifes;

    public int[] circleXs;
    public int[] circleYs;

    int circleIndex = 0;
    int index = 0;

    public int tick = 0;

    public void setup() {
        rectXs = new int[RectSize];
        rectYs = new int[RectSize];
        hues = new int[RectSize];
        lifes = new int[RectSize];

        circleXs = new int[CircleSize];
        circleYs = new int[CircleSize];

        for (int i = 0; i < RectSize; i++) {
            rectXs[i] = -1;
            rectYs[i] = -1;
            lifes[i] = 0;
        }
    }


    public void draw() {
        background(0);
        colorMode(HSB);
        blendMode(ADD);
        fill(255);

        tick++;

        if (mousePressed) {


            // 네모를 만든다
            // 네모를 만든건데, 어디에
            rectXs[index] = mouseX - 15;
            rectYs[index] = mouseY - 15;
            hues[index] = tick;
            lifes[index] = 30;

            index++;
            index = index % RectSize;

        }

        for (int i = 0; i < RectSize; i++) {
            // 파괴 되었을때 찾아라
            lifes[i]--;
            if (lifes[i] == 0) {
                //
                circleXs[circleIndex] = rectXs[i];
                circleYs[circleIndex] = rectYs[i];

                circleIndex++;
                circleIndex %= CircleSize;
                //
            }
            if (lifes[i] <= 0) {
                continue;
            }
            // 30 : opa 1
            // 0 : opa 0
            fill(hues[i] % 255, 255, 255, lifes[i] * 8);

            rect(rectXs[i] + (30 - lifes[i]) * 0.2f
                    , rectYs[i] + (30 - lifes[i]) * 0.2f
                    , 30 - (30 - lifes[i]) * 0.4f
                    , 30 - (30 - lifes[i]) * 0.4f);
        }

        for (int i = 0; i < CircleSize; i++) {

            //update
            circleYs[i] -= 0.1;
            //render
            ellipse(circleXs[i], circleYs[i], 15, 15);
        }
    }
}

