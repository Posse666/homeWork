public class Horse extends Animal {

    private final String animal = "лошадь";
    private final int distanceLimit = 1500;
    private final double jumpLimit = 3;
    private final int swimLimit = 100;

    Horse(String name) {
        super(name);
        super.distanceLimit = (int) limits(distanceLimit);
        super.jumpLimit = limits(jumpLimit);
        super.animal = animal;
        super.swimLimit = (int) limits(swimLimit);
    }
}
