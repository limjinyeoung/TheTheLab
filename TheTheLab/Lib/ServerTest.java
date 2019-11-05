import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerTest {
    public static List<SocketConnector> conns = new ArrayList<>();
    public static int i = 0;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3333);

        while (true) {
            Socket socket = serverSocket.accept();
            SocketConnector conn = new SocketConnector(socket);


            conn.startReceiver();
            conn.setReceiver((String s)-> {
                System.out.println("says something");
                conn.send("me too");
            });

            conns.add(conn);
            System.out.println("address" + socket.getInetAddress());

        }
    }

}
