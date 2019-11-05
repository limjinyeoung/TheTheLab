import processing.core.PApplet;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

interface SessionListener {
    void onAddWord(String word);

    void onRemoveWord(String word);
}

class Session extends Thread {
    private Socket socket;
    private volatile boolean isCancelled;
    private SessionListener listener;

    Session(String ip, int port) throws IOException {
        isCancelled = false;
        socket = new Socket(ip, port);
    }

    void setListener(SessionListener listener) {
        this.listener = listener;
    }

    void cancel() {
        isCancelled = true;
    }


    // Try with resource
    @Override
    public void run() {

        byte[] buf = new byte[128];
        try (InputStream is = socket.getInputStream()) {
            while (!isCancelled) {
                int len = is.read(buf);
                if (len == -1) {
                    break;
                }

                String message = new String(buf, 0, len);
                processMessage(message);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Session Thread exit");
    }

    private void processMessage(String message) {
        // ADD,hello
        // DEL,hello
        if (listener == null)
            return;

        String[] packet = message.split(",");
        if (packet.length != 2) {
            System.out.println("wrong message");
            return;
        }

        String protocol = packet[0];
        String word = packet[1].trim();

        switch (protocol) {
            case "ADD":
                listener.onAddWord(word);
                break;
            case "DEL":
                listener.onRemoveWord(word);
                break;
        }
    }
}

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

        textObjects = textObjects.stream().filter(new Predicate<TextObject>() {
            @Override
            public boolean test(TextObject textObject) {
                return !textObject.getText().equals(word);
            }
        }).collect(Collectors.toList());
    }
}
