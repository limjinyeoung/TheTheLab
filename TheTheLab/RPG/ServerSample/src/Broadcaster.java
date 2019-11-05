import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

class Broadcaster extends Thread {
    private List<String> words;
    private List<Session> sessions;

    Broadcaster(List<Session> sessions) {
        this.sessions = sessions;
        this.words = Arrays.asList(
                "hello",
                "world",
                "show",
                "me",
                "the", "money"
        );
    }

    private void addWord(String word) {
        broadcast("ADD," + word);
    }

    void removeWord(String word) {
//        if (!words.contains(word)) {
//            return;
//        }

        broadcast("DEL," + word);
    }

    private void broadcast(String message) {
        for (Session session : sessions) {
            try {
                OutputStream os = session.getOutputStream();
                os.write(message.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);

                int index = random.nextInt(words.size());
                addWord(words.get(index));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
