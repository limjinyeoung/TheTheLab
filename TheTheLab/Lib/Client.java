import java.io.IOException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        SocketConnector conn = new SocketConnector("localhost", 3333);

        conn.connect();
        conn.startReceiver();

        conn.setReceiver((String s)-> {
            System.out.println(s);
        });
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            conn.send(line);
        }
    }
}