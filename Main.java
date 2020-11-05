public class Main {

    private static final Cat cat = new Cat("Мурка");
    private static final Dog dog = new Dog("Барбос");
    private static final Horse horse = new Horse("Молния");
    private static final Bird bird = new Bird("Галка");
    private static final Cat cat2 = new Cat("Василиса");
    private static final Dog dog2 = new Dog("Гав");
    private static final Horse horse2 = new Horse("Галоп");
    private static final Bird bird2 = new Bird("Яша");
    private static final Animal[] animals = {cat, cat2, dog, dog2, horse, horse2, bird, bird2};

    public static void main(String[] args) {

        for (int i = 0; i < animals.length; i++) {
            System.out.println(animals[i].run(200));
            System.out.println(animals[i].swim(3));
            System.out.println(animals[i].jump(2));
            System.out.println();
        }
    }
}
