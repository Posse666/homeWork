public class HW4_EX1 {
    private static final Object mon = new Object();

    private static volatile String turn = "A";

    public static void main(String[] args) {

        new Thread(() -> {
            synchronized (mon) {
                try {
                    int i = 0;
                    while (i < 5) {
                        if (!turn.equals("A")) {
                            mon.wait();
                        } else {
                            System.out.print("A");
                            turn = "B";
                            mon.notifyAll();
                            i++;
                        }
                    }
                } catch (InterruptedException ignored) {
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (mon) {
                try {
                    int i = 0;
                    while (i < 5) {
                        if (!turn.equals("B")) {
                            mon.wait();
                        } else {
                            System.out.print("B");
                            turn = "C";
                            mon.notifyAll();
                            i++;
                        }
                    }
                } catch (InterruptedException ignored) {
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (mon) {
                try {
                    int i = 0;
                    while (i < 5) {
                        if (!turn.equals("C")) {
                            mon.wait();
                        } else {
                            System.out.print("C");
                            turn = "A";
                            mon.notifyAll();
                            i++;
                        }
                    }
                } catch (InterruptedException ignored) {
                }
            }
        }).start();
    }
}
