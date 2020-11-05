public class Bird extends Animal {

    private final String animal = "птичка";
    private final int distanceLimit = 5;
    private final double jumpLimit = 0.2;

    Bird(String name) {
        super(name);
        super.distanceLimit = (int) limits(distanceLimit);
        super.jumpLimit = limits(jumpLimit);
        super.animal = animal;
    }

    @Override
    protected String swim(int distance) {
        return String.format("%s %s не умеет плавать...", animal, name);
    }
}
