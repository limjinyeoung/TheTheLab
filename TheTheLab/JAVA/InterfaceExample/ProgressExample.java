import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProgressExample extends PApplet {
    public static void main(String[] args) {
        PApplet.main("ProgressExample");
    }


    private ArrayList<View> ui = new ArrayList<>();
//    ArrayList<View> buttons = new ArrayList<>();

//    Button.OnClickListener bitButtonCreator;
//    Button.OnClickListener smallButtonCreator;


    public void settings() {
        size(Constant.WIDTH, Constant.HEIGHT);
//        smallButtonCreator = new Button.OnClickListener() {
//            @Override
//            public void onClick() {
//                System.out.println("50짜리 만들거다");
//            }
//        };
//        bitButtonCreator = new Button.OnClickListener() {
//            @Override
//            public void onClick() {
//                System.out.println("200짜리 만들거다");
//            }
//        };

//        Button btn = new Button(this, 10, 10, 30, 30);
//        btn.setOnClickListener(new Button.OnClickListener() {
//            @Override
//            public void onClick() {
//
//                System.out.println("큰거큰거!!!");
//            }
//        });
//        buttons.add(btn);
//
//        btn = new Button(this, 50, 10, 30, 30);
//        btn.setOnClickListener(new Button.OnClickListener() {
//            @Override
//            public void onClick() {
//                System.out.println("중간거");
//            }
//        });
//        buttons.add(btn);

        UIBar bar = new UIBar(this);

        bar.addButton(new Button.OnClickListener() {
            @Override
            public void onClick() {
                System.out.println("큰거");
            }
        });
        bar.addButton(() -> System.out.println("작은거"));
        bar.addButton(() -> System.out.println("1 큰거"));
        bar.addButton(() -> System.out.println("2 작은거"));
        bar.addButton(() -> System.out.println("3 큰거"));
        bar.addButton(() -> System.out.println("4 작은거"));


        ui.add(bar);


    }

    public void setup() {
        background(0);

    }

    public void mouseClicked() {
//        for(View view : buttons) view.mouseClicked(this.mouseX, this.mouseY);

        for (View view : ui) view.mouseClicked(this.mouseX, this.mouseY);
    }

    public void draw() {
        background(0);

        //
        fill(255);
        rect(0, 0, Constant.WIDTH, Constant.BAR_HEIGHT);

//        for(View view : buttons) view.render();
        for (View view : ui) view.render();


    }
}