import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class MainClass {
    public static final int CARS_COUNT = 4;
    public static final Phaser STAGE = new Phaser();
    private static final int RACE_START = 0;
    private static final int RACE_END = 1;

    public static void main(String[] args) {
        ExecutorService racers = Executors.newFixedThreadPool(CARS_COUNT);
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(2), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            racers.execute(cars[i]);
        }
        STAGE.awaitAdvance(RACE_START);
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        while (STAGE.getArrivedParties() == 0) {
        }
        System.out.println("Победитель: " + cars[Car.ID - 1].getName());
        STAGE.awaitAdvance(RACE_END);
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        racers.shutdown();
    }
}