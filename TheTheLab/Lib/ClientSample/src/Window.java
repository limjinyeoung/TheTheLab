import processing.core.PApplet;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class Window extends PApplet implements SessionListener {
    private Session session;
    private List<TextObject> textObjects = new CopyOnWriteArrayList<>();

    public Window() throws IOException {
        session = new Session("127.0.0.1", 5000);
        session.setListener(this);
        session.start();
    }

    @Override
    public void exit() {
        System.out.println("exit");
        session.cancel();
        super.exit();
    }

    @Override
    public void setup() {
        super.setup();
        background(255);
    }

    @Override
    public void settings() {
        super.settings();
        size(640, 480);
    }

    @Override
    public void draw() {
        background(255);

        for (TextObject e : textObjects) {
            e.update();
        }

        for (TextObject e : textObjects) {
            e.draw(this);
        }
    }

    @Override
    public void onAddWord(String word) {
        System.out.println("ADD:" + word);
        textObjects.add(new TextObject(word, 640));
    }

    @Override
    public void onRemoveWord(String word) {
        System.out.println("DEL:" + word);

        textObjects = textObjects.stream().filter(textObject -> !textObject.getText().equals(word)).collect(Collectors.toList());
    }
}
