import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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
