public class Server {
    public static void main(String[] args) {
        Acceptor acceptor = new Acceptor(8888);
        acceptor.start();
    }
}