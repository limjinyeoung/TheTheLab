import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketConnector implements Runnable {
    private String host;
    private int port;
    private Socket socket;

    private InputStream is;
    private OutputStream os;
    public SocketConnector (String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException {
        if (this.socket != null) throw new IllegalStateException("Already Exist");

        this.socket = new Socket();
        this.socket.connect(new InetSocketAddress(host, port));

        this.is = socket.getInputStream();
        this.os = socket.getOutputStream();
    }

    public void startReceiver() throws IOException {
        is = socket.getInputStream();
        os = socket.getOutputStream();

        new Thread(()-> {
            int len;
            byte[] bytes = new byte[128];
            try {
                while ((len = is.read(bytes)) != -1) {
                    String s = new String(bytes, 0, len);
                    receiver.onReceive(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public SocketConnector(Socket socket){
        this.socket = socket;
    }
    // 이 클래스가 연결과 소켓에 대한 쓰레드를 관리한다.
    // 연결이 되면 쓰레드가 실행되고, 접속이 종료되면 레드가 종료된다.

    // 다른 소켓에 연결되었으므로, 메세지를 보낼 수 있어야 한다.
    public void send (String msg) throws IOException {
        os.write(msg.getBytes());
    }

    // 연결된 대상이 보낸 메세지를 수신할 수 있어야 하므로 수동적이기 때문에 인터페이스로 등록한다.

    private Receiver receiver = s -> {

    };

    public interface Receiver {
        void onReceive(String s);
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void run() {

    }
}