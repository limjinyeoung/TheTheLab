import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.Socket;

class Session extends Thread {
    private Socket socket;
    private OnReceived listener;
    private Broadcaster broadcaster;
    private UserInfo userInfo;
    private int[] map;
    private int index;

    Session(Socket socket, Broadcaster broadcaster) {
        this.socket = socket;
        this.broadcaster = broadcaster;
        userInfo = new UserInfo("Hello", 10, 10);
    }


    void setSessionListener(OnReceived listener) {
        this.listener = listener;
    }

    OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    @Override
    public void run() {
        try {
            InputStream is = socket.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            byte[] buf = new byte[8192];
            while (true) {
                if (dis.available() > -1) {
                    int len = dis.readInt();
                    int ret = readn(dis, buf, len);
                    if (ret == -1) {
                        break;
                    }

                    String message = new String(buf, 0, len).replace(" ", "");
                    System.out.println(message);
                    if (message.length() > 8) {
                        Gson gson = new GsonBuilder().create();
                        String[] s = message.split("body\":");
                        String body = s[1].substring(0,s[1].length()-1);

                        switch (message.charAt(9)) {
                            case 'J' :
                                userInfo.user = gson.fromJson(body, Protocol.Join.class).user;
                                sendMessage(Protocol.makeMapProtocol(map));
                                sendMessage(Protocol.makeItemCreateProtocol(index));
                                sendMessage(Protocol.makeItemConsumeProtocol(userInfo.user, index));
                                break;
                            case 'M' :
                                userInfo.direction = gson.fromJson(body, Protocol.Move.class).direction;
                                break;
                            case 'A' :
                                userInfo.attack = true;
                                break;
                        }
                    }
                } else
                    break;
            }

            if (listener != null)
                listener.onDisconnect(this);

        } catch (IOException e) {
            listener.onDisconnect(this);
            System.out.println("Connection Thread end");
        }
    }

    public void sendMessage(String message) {
        try {
            OutputStream os = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);

            dos.writeInt(message.getBytes().length);
            dos.write(message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int readn(InputStream is, byte[] buf, int size) throws IOException {
        int left = size;
        int offset = 0;

        while (left > 0) {
            int len = is.read(buf, offset, left);
            if (len == -1)
                return -1;

            left -= len;
            offset += len;
        }

        return size;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public static class UserInfo {
        private String user;
        private int x;
        private int y;
        private int hp;
        private int score;
        private boolean attack;
        private String direction;

        public UserInfo(String user, int x, int y) {
            this.user = user;
            this.x = x;
            this.y = y;
            this.hp = 100;
            this.score = 100;
            this.attack = false;
        }

        public String getUser() {
            return user;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getHp() {
            return hp;
        }

        public int getScore() {
            return score;
        }

        public boolean isAttack() {
            return attack;
        }

        public String getDirection() {
            return direction;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void setHp(int hp) {
            this.hp = hp;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public void setAttack(boolean attack) {
            this.attack = attack;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }
    }
}
