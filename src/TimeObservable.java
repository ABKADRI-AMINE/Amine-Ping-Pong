import java.util.ArrayList;
import java.util.List;

public class TimeObservable {
    private List<TimeObserver> observers = new ArrayList<>();
    private int timeElapsed = 0;

    public void addObserver(TimeObserver observer) {
        observers.add(observer);
    }

    public void start() {
        new Thread(() -> {
            while (timeElapsed < 40) {
                try {
                    Thread.sleep(1000);
                    timeElapsed++;
                    notifyObservers();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void notifyObservers() {
        for (TimeObserver observer : observers) {
            observer.updateTime();
        }
    }

    public int getTimeElapsed() {
        return timeElapsed;
    }
}
