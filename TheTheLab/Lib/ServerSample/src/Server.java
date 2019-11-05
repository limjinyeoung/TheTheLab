public class Server {
    public static void main(String[] args) {
        Acceptor acceptor = new Acceptor(5000);
        acceptor.start();
    }
}