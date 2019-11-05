package model;


public class MoveRequest implements Packet {
    class Body {
        final String direction;

        Body(String direction) {
            this.direction = direction;
        }
    }

    private final String type;
    private final Body body;

    public MoveRequest(String direction) {
        this.type = "Move";
        this.body = new Body(direction);
    }

    public String getDirection() {
        return body.direction;
    }

    @Override
    public String toString() {
        return "MoveRequest{" +
                "type='" + type + '\'' +
                ", body=" + body +
                '}';
    }
}
