import java.util.concurrent.Phaser;

public class Tunnel extends Stage {
    private final Phaser TUNNEL = new Phaser();
    private final int width;

    public Tunnel(int width) {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        this.width = width;
    }

    @Override
    public void go(Car c) {
        try {
            try {
                TUNNEL.register();
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                while (TUNNEL.getRegisteredParties() > width) {
                }
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
            }
            TUNNEL.arriveAndDeregister();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}