public class Vector2 {

    public float x, y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 add(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }


    public Vector2 add(Vector2 vector2) {
        this.x += vector2.x;
        this.y += vector2.y;
        return this;
    }

    public static Vector2 add(Vector2 a, Vector2 b) {
        return new Vector2(a.x + b.x, a.y + b.y);
    }

    public Vector2 getAdd(Vector2 vector2) {
        return new Vector2(this.x + vector2.x, this.y + vector2.y);
    }


    public Vector2 subtract(float x, float y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vector2 subtract(Vector2 vector2) {
        this.x -= vector2.x;
        this.y -= vector2.y;
        return this;
    }

    public static Vector2 subtract(Vector2 a, Vector2 b) {
        return new Vector2(a.x - b.x, a.y - b.y);
    }

    public Vector2 getSubtract(Vector2 vector2) {
        return new Vector2(this.x - vector2.x, this.y - vector2.y);
    }

    public Vector2 mul(float x, float y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    public Vector2 mul(Vector2 vector2) {
        this.x *= vector2.x;
        this.y *= vector2.y;
        return this;
    }

    public static Vector2 mul(Vector2 a, Vector2 b) {
        return new Vector2(a.x * b.x, a.y * b.y);
    }

    public Vector2 getMul(Vector2 vector2) {
        return new Vector2(this.x * vector2.x, this.y * vector2.y);
    }


    public Vector2 divide(float x, float y) {
//        try {
//            this.x /= x;
//            this.y /= y;
//
//        } catch (Exception e) {
//            System.out.println("0으로 나눌 수 없습니다");
//        }

        if (x == 0 || y == 0) {
            throw new ArithmeticException("0으로 나눌수 없어요!!");
        }

        this.x /= x;
        this.y /= y;

        return this;
    }


    public Vector2 divide(Vector2 vector2) {

        if (vector2.x == 0 || vector2.y == 0) {
            throw new ArithmeticException("0으로 나눌수 없어요!!");
        }
        this.x /= vector2.x;
        this.y /= vector2.y;
        return this;
    }

    public static Vector2 divide(Vector2 a, Vector2 b) {

        if (b.x == 0 || b.y == 0) {
            throw new ArithmeticException("0으로 나눌수 없어요!!");
        }
        return new Vector2(a.x / b.x, a.y / b.y);
    }

    public Vector2 getDivide(Vector2 vector2) {

        if (vector2.x == 0 || vector2.y == 0) {
            throw new ArithmeticException("0으로 나눌수 없어요!!");
        }

        return new Vector2(this.x / vector2.x, this.y / vector2.y);
    }


    public float magnitude() {
        return (float) (Math.sqrt((x * x) + (y * y)));
    }

    public Vector2 nomalize() {
        Vector2 vector2 = new Vector2(x, y);

        vector2.x = x / magnitude();
        vector2.y = y / magnitude();
        return vector2;
    }

    public float distance(Vector2 vector2) {
        return (float) Math.sqrt(Math.pow(x - vector2.x, 2) + Math.pow(y - vector2.y, 2));
    }

    public static float distance(Vector2 a, Vector2 b) {
        return (float) Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.x - b.y, 2));

    }

    public Vector2 reflection(Vector2 vector2) {
        return new Vector2(2 * vector2.x - x, 2 * vector2.y - y);
    }

    //    public Vector2 rotation(float degree){
//        float radian = (float) Math.toRadians(degree);
//        this.x = (float) (Math.cos(radian) * x - (float)Math.sin(radian) * y);
//        this.y = (float) (Math.sin(radian) * x + (float)Math.cos(radian) * y);
//        return this;
//    }
    public Vector2 rotateByCenterPoint(float degree) {
        float radian = (float) Math.toRadians(degree);
        float preX = x;
        this.x = (float) (Math.cos(radian) * preX - Math.sin(radian) * y);
        this.y = (float) (Math.sin(radian) * preX + Math.cos(radian) * y);
        return this;
    }

    public Vector2 rotateByPoint(float degree, Vector2 point){
        float radian = (float) Math.toRadians(degree);
        float px = this.x - point.x;
        float py = this.y - point.y;

        this.x = (float) (Math.cos(radian) * px - Math.sin(radian) * py) + point.x;
        this.y = (float) (Math.sin(radian) * px + Math.cos(radian) * py) + point.y;
        return this;
    }


    @Override
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
