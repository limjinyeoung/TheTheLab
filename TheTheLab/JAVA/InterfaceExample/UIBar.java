import processing.core.PApplet;

import java.util.ArrayList;

public class UIBar extends View {

    public UIBar(PApplet pApplet) {
        super(pApplet, 0, 0, Constant.WIDTH, Constant.BAR_HEIGHT);
    }

    private ArrayList<View> buttons = new ArrayList<>();

    public void addButton(Button.OnClickListener onClickListener) {
        Button btn = new Button(this.pApplet, 10 + 40 * buttons.size(), 10, 30, 30);
        btn.setOnClickListener(onClickListener);
        this.buttons.add(btn);
    }

    @Override
    public void render() {
        this.pApplet.fill(255);
        this.pApplet.rect(0, 0, width, height);

        for (View view : buttons) view.render();
    }

    public void mouseClicked(int x, int y) {
        super.mouseClicked(x, y);
        for (View view : buttons) view.mouseClicked(x, y);
    }

    @Override
    public void clicked() {

    }
}
