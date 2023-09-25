public interface BallBuilderInterface {
    BallBuilderInterface setX(int x);
    BallBuilderInterface setY(int y);
    BallBuilderInterface setWidth(int width);
    BallBuilderInterface setHeight(int height);
    Ball build();
}