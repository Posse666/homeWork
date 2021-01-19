package ex3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        Box<Apple> boxWithApples = new Box<>(getFruits(Apple.class,RANDOM.nextInt(15)),"Apples 1");
        Box<Apple> boxWithApples2 = new Box<>(getFruits(Apple.class,RANDOM.nextInt(15)),"Apples 2");
        Box<Apple> boxWithApples3 = new Box<>(getFruits(Apple.class,RANDOM.nextInt(15)), "Apples 3");
        Box<Orange> boxWithOranges = new Box<>(getFruits(Orange.class,RANDOM.nextInt(15)),"Oranges 1");

        System.out.println(boxWithApples.getWeight());
        System.out.println(boxWithApples.compare(boxWithApples));
        System.out.println(boxWithApples.compare(boxWithOranges));
        System.out.println(boxWithOranges.getWeight());
        System.out.println(boxWithOranges.compare(boxWithApples));
        boxWithApples.addFruitsFromAnotherBox(boxWithApples2);
        boxWithApples2.addFruitsFromAnotherBox(boxWithApples3);
    }

    private static <T extends Fruit> List<T> getFruits(Class<T> fruit, int quantity) {
        List<T> fruits = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            try {
                fruits.add(fruit.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return fruits;
    }

}
