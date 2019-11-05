import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

class Broadcaster extends Thread {
    private List<Session> sessions;
    private String victim;
    private int victimIndex;

    Broadcaster(List<Session> sessions) {
        this.sessions = sessions;
    }

    private void broadcast(String message) {
        for (Session session : sessions) {
            try {
                OutputStream os = session.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);

                dos.writeInt(message.getBytes().length);
                dos.write(message.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(30);
                if (sessions.size() > 0) {
                    updateUser();
                    broadcast(Protocol.makeUpdateProtocol(sessions));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateUser() {
        for (Session s : sessions) {
            Session.UserInfo u = s.getUserInfo();
            switch (u.getDirection().toLowerCase().charAt(0)) {
                case 'u':
                    collisionTest(u.getX(), u.getY() - 3, 50);
                    u.setY(u.getY() - 3);
                    break;
                case 'd':
                    collisionTest(u.getX(), u.getY() + 3, 50);
                    u.setY(u.getY() + 3);
                    break;
                case 'l':
                    collisionTest(u.getX() - 3, u.getY(), 50);
                    u.setX(u.getX() - 3);
                    break;
                case 'r':
                    collisionTest(u.getX() + 3, u.getY(), 50);
                    u.setX(u.getX() + 3);
                    break;
            }

            if (u.isAttack() && !collisionTest(u.getX(), u.getY(), 10)) {
                broadcast(Protocol.makeHitProtocol(u.getUser(), victim, 10));
                if (sessions.get(victimIndex).getUserInfo().getHp() <= 0) {
                    broadcast(Protocol.makeKillProtocol(u.getUser(), victim));
                    u.setScore(u.getScore() + 50);
                    sessions.get(victimIndex).getUserInfo().setScore(sessions.get(victimIndex).getUserInfo().getScore() / 2);
                }
            }
        }
    }

    private boolean collisionTest(int x, int y, int range) {
        for (int i = 0; i < sessions.size(); i++) {
            Session s = sessions.get(i);
            Session.UserInfo u = s.getUserInfo();

            if (y < u.getY() + range && y + range > u.getY()) {
                victim = u.getUser();
                victimIndex = i;
                return false;
            }
            if (x < u.getX() + range && x + range > u.getX()) {
                victim = u.getUser();
                victimIndex = i;
                return false;
            }
        }
        return true;
    }


}
