public class Dog extends Animal {

    private final String animal = "собака";
    private final int distanceLimit = 500;
    private final double jumpLimit = 0.5;
    private final int swimLimit = 10;

    Dog(String name) {
        super(name);
        super.distanceLimit = (int) limits(distanceLimit);
        super.jumpLimit = limits(jumpLimit);
        super.animal = animal;
        super.swimLimit = (int) limits(swimLimit);
    }
}
