import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

interface SessionListener {
    void onDisconnect(Session session);
}

class Session extends Thread {
    private Socket socket;
    private SessionListener listener;
    private Broadcaster broadcaster;

    Session(Socket socket, Broadcaster broadcaster) {
        this.socket = socket;
        this.broadcaster = broadcaster;
    }


    void setSessionListener(SessionListener listener) {
        this.listener = listener;
    }

    OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    @Override
    public void run() {
        try {
            InputStream is = socket.getInputStream();

            byte[] buf = new byte[256];
            while (true) {
                int len = is.read(buf);
                if (len == -1)
                    break;

                String word = new String(buf, 0, len);
                broadcaster.removeWord(word);
            }

            if (listener != null)
                listener.onDisconnect(this);

            System.out.println("Connection Thread end");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

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


class Acceptor extends Thread {
    private int port;

    // ArrayList 모든 연산이 스레드 안전하게 동작하는 것을 보장한다.
    private List<Session> sessions = new CopyOnWriteArrayList<>();

    Acceptor(int port) {
        this.port = port;
    }

    private void addSession(Session s) {
        sessions.add(s);
    }

    private void removeSession(Session s) {
        sessions.remove(s);
    }

    @Override
    public void run() {
        try {
            Broadcaster broadcaster = new Broadcaster(sessions);
            broadcaster.start();

            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);

            while (true) {
                Socket socket = serverSocket.accept();

                Session session = new Session(socket, broadcaster);
                session.setSessionListener(s -> {
                    System.out.println("onDisconnect");
                    removeSession(s);
                });

                addSession(session);
                session.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


public class Program {
    public static void main(String[] args) {
        Acceptor acceptor = new Acceptor(5000);
        acceptor.start();
    }
}