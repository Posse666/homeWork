public class Animal {

    protected int distanceLimit;
    protected double jumpLimit;
    protected int swimLimit;
    protected String name;
    protected String animal;

    Animal(String name) {
        this.name = name;
    }

    protected double limits(double number) {
        double percent = number / 100 * 25;
        return (number - percent + Math.random() * percent * 2);
    }

    protected String run(int distance) {
        return result(distance, distanceLimit, " пробежала ", "бежать");
    }

    protected String swim(int distance) {
        return result(distance, swimLimit, " проплыла ", "плыть");
    }

    protected String jump(double height) {
        return result(height, jumpLimit, " прыгнула ", "прыгать");
    }

    private String result(double number, double limit, String action1, String action2) {
        if (number <= limit)
            return String.format("%s %s %s %.1f м. из возможных %.1f м.", animal, name, action1, number, limit);
        else
            return String.format("Для %s %s очень далеко, может %s только %.1f м.", animal, name, action2, limit);
    }
}
