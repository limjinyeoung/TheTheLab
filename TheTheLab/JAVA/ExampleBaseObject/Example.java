import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;


public class Example extends PApplet {
    public static void main(String args[]) {
        PApplet.main("Example");
    }

    public void settings() {
        // 시스템
        size(960, 640);

    }

    List<ExamplePoint> points;

    public void setup() {
        points = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            points.add(new ExamplePoint(this));
        }
    }


    private float cx = 480, cy = 320, radius = 10;

    private float vx = 0, ax = 0;
    private float vy = 0, ay = 0;

    // 반복문
    // 한번 돌때 단위 시간이 진행됨.
    // 30번의 루프가 일어나므로 , 0.033..... 초
    // 1초에 10px 오른쪽


    // 처음엔 속도가 0
    // 가속도가 오른쪽으로 10px 1초에
    // 1초 : 10px = 0.033초 : x
    public void draw() {
        background(0);
        colorMode(HSB);
        fill(255, 255, 255);
        for(int i = 0 ; i < points.size(); i ++){
            points.get(i).update();
            points.get(i).render();
        }
    }

}

