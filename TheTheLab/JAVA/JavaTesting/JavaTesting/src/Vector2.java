public class Vector2 {
    public float x;
    public float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void add(Vector2 delta) {
        this.add(delta.x, delta.y);

    }

    public void add(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public void mul(float x, float y) {
        this.x *= x;
        this.y *= y;
    }

    public void mul(Vector2 v) {
        this.mul(v.x, v.y);
    }
}