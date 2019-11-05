import java.util.Random;

class TextObject {
    private float x;
    private float y;
    private String text;

    TextObject(String text, int width) {
        Random random = new Random();
        y = 0;
        x = random.nextInt(width);
        this.text = text;
    }

    void update() {
        y += 3;
    }

    void draw(Window window) {
        window.fill(0);
        window.text(text, x, y);
    }

    String getText() {
        return this.text;
    }
}
