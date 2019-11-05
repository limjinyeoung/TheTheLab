import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class Window extends PApplet {

    List<Point> points = new ArrayList<>();
    public void settings() {
        // 시스템
        size(960, 640);
    }

    public void setup() {

    }

    // 30 FPS
    public void draw() {
        background(0);

        int mx = mouseX - pmouseX;
        int my = mouseY - pmouseY;


        Point p = new Point(this, mouseX, mouseY);
        points.add(p);

        for(Point point : points){
            point.update();
            point.render();
        }


    }

}

