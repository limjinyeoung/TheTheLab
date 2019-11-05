import processing.core.PApplet;

public class Button extends View {
    public Button(PApplet pApplet, int x, int y, int width, int height) {
        super(pApplet, x, y, width, height);
    }

    public interface OnClickListener {
        void onClick();
    }

    private OnClickListener onClickListener;


    public void setOnClickListener(OnClickListener clickListener) {
        this.onClickListener = clickListener;
    }

    @Override
    public void render() {
        pApplet.fill(255, 70, 255);
        pApplet.rect(x, y, width, height);
    }

    @Override
    public void clicked() {
        // Command 에 따라서, 처리해야하는 방법이 다르다.
        // 클리된 이후 행위가 각기 달라야하는데 , Button 은 행위를 모름

        if (onClickListener != null) onClickListener.onClick();

        System.out.println("Click!");

    }

}
