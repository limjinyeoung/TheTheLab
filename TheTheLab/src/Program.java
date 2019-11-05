import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import model.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

class GameModel {
    private static final GameModel INSTANCE = new GameModel();
    private List<Session> sessions = new CopyOnWriteArrayList<>();

    private GameModel() {
    }

    public static GameModel getInstance() {
        return INSTANCE;
    }

    public void broadcast(byte[] packet) {
        for (Session e : sessions) {
            e.write(packet);
        }
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }
}

class User {
    private static final float SPEED = 3f;

    enum State {
        STOP,
        MOVE,
        ATTACK
    }

    private String name;
    private float x;
    private float y;
    private int hp;
    private int score;
    private State state;
    private String direction;

    public User(String name) {
        this.name = name;
        this.x = 0;
        this.y = 0;
        this.hp = 100;
        this.score = 0;
        this.state = State.STOP;
        this.direction = "Left";
    }

    private void onMove() {
        switch (direction) {
            case "Left":
                this.x -= SPEED;
                break;
            case "Right":
                this.x += SPEED;
                break;
            case "Up":
                this.y -= SPEED;
                break;
            case "Down":
                this.y += SPEED;
                break;
        }
    }

    private void onAttack() {

    }

    public void update() {
        if (state == State.MOVE) {
            onMove();
        } else if (state == State.ATTACK) {
            onAttack();
        }
    }

    public void move(String direction) {
        this.direction = direction;
        this.state = State.MOVE;
    }

    public void attack() {
        this.state = State.ATTACK;
    }

    public void stop() {
        state = State.STOP;
    }
}

class UpdateResponse implements Packet {
    private final String type;
    private final User[] users;

    public UpdateResponse(User[] users) {
        this.type = "Update";
        this.users = users;
    }
}


class Game extends TimerTask {
    private List<User> users = new CopyOnWriteArrayList<>();

    private void broadcast() {
        System.out.println(users.size());
        User[] buf = new User[users.size()];
        users.toArray(buf);

        UpdateResponse response = new UpdateResponse(buf);
        byte[] packet = response.toPacket();

        GameModel.getInstance().broadcast(packet);
    }

    public void join(User user) {
        users.add(user);
    }

    @Override
    public void run() {
        for (User e : users) {
            e.update();
        }

        broadcast();
    }

    public void removeUser(User user) {
        users.remove(user);
    }
}

class Session extends Thread {
    private Socket socket;
    private Game game;

    private User user;

    Session(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;
    }

    // Session onJoinRequest
    private void onJoinRequest(JoinRequest request) {
        String name = request.getUser();
        user = new User(name);

        game.join(user);
    }


    @Override
    public void run() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        GameModel gameModel = GameModel.getInstance();
        gameModel.addSession(this);


        try (InputStream is = socket.getInputStream();
             DataInputStream dis = new DataInputStream(is)) {

            int readBytes;
            byte[] buf = new byte[8192];

            while (true) {
                int len = dis.readUnsignedShort();

                readBytes = dis.read(buf, 0, len);
                if (readBytes == -1)
                    break;

                String json = new String(buf, 0, len);
                JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

                String type = jsonObject.get("type").getAsString();
                switch (type) {
                    case "Join": {
                        JoinRequest request = gson.fromJson(json, JoinRequest.class);
                        onJoinRequest(request);
                        break;
                    }

                    case "Move": {
                        MoveRequest request = gson.fromJson(json, MoveRequest.class);
                        onMoveRequest(request);
                        break;
                    }

                    case "Attack": {
                        AttackRequest request = gson.fromJson(json, AttackRequest.class);
                        onAttackRequest(request);
                        break;
                    }

                    case "Stop": {
                        StopRequest request = gson.fromJson(json, StopRequest.class);
                        onStopRequest(request);
                        break;
                    }
                }
            }

        } catch (EOFException | SocketException ignored) {

        } catch (IOException e) {
            e.printStackTrace();
        }

        gameModel.removeSession(this);
        game.removeUser(user);

        System.out.println("Session disconnected");
    }

    private void onStopRequest(StopRequest request) {
        user.stop();
    }

    private void onAttackRequest(AttackRequest request) {
        user.attack();
    }

    private void onMoveRequest(MoveRequest request) {
        user.move(request.getDirection());
    }

    public void write(byte[] packet) {
        try {
            OutputStream os = socket.getOutputStream();
            os.write(packet);
        } catch (IOException ignored) {
        }
    }
}


class Acceptor implements Runnable {
    private int port;

    Acceptor(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        Game game = new Game();
        Timer updateTimer = new Timer();
        updateTimer.scheduleAtFixedRate(game, 0, 100);

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {

                Socket socket = serverSocket.accept();

                Session session = new Session(socket, game);
                session.start();


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class Program {
    public static void main(String[] args) {

        Thread thread = new Thread(new Acceptor(5000));
        thread.start();
    }
}













