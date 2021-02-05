public class Car implements Runnable {
    private static final String NAME_PREFIX;
    public static volatile int ID;
    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
        NAME_PREFIX = "Участник #";
    }

    private Race race;
    private int speed;
    private String name;

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = NAME_PREFIX + CARS_COUNT;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public void run() {
        try {
            MainClass.STAGE.register();
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            MainClass.STAGE.arriveAndAwaitAdvance();
            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }
            if (MainClass.STAGE.getArrivedParties() == 0) ID = Integer.parseInt(name.substring(NAME_PREFIX.length()));
            MainClass.STAGE.arrive();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}