public class Cat extends Animal {

    private final String animal = "кошка";
    private final int distanceLimit = 200;
    private final double jumpLimit = 2;

    Cat(String name) {
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
