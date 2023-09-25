public class BallBuilder implements BallBuilderInterface {
    private int x;
    private int y;
    private int width;
    private int height;

    public BallBuilder setX(int x) {
        this.x = x;
        return this;
    }

    public BallBuilder setY(int y) {
        this.y = y;
        return this;
    }

    public BallBuilder setWidth(int width) {
        this.width = width;
        return this;
    }

    public BallBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    public Ball build() {
        return new Ball(x, y, width, height);
    }
}