package model;

public class JoinRequest implements Packet {
    class Body {
        final String user;

        Body(String username) {
            this.user = username;
        }
    }

    private final String type;
    private final Body body;

    public JoinRequest(String username) {
        this.type = "Join";
        this.body = new Body(username);
    }

    public String getUser() {
        return body.user;
    }

    @Override
    public String toString() {
        return "JoinRequest{" +
                "type='" + type + '\'' +
                '}';
    }
}

