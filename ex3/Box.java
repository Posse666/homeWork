package ex3;

import java.util.List;
import java.util.Random;

public class Box<T extends Fruit> {

    private static final Random RANDOM = new Random();
    private static final int MINIMUM_CAPACITY = 8;
    private static final int MAXIMUM_CAPACITY = 15;
    private final List<T> fruits;
    private final int capacity;
    private final String name;

    public Box(List<T> fruits, String name) {
        capacity = RANDOM.nextInt(MAXIMUM_CAPACITY - MINIMUM_CAPACITY + 1) + MINIMUM_CAPACITY;
        this.fruits = fruits;
        this.name = name;
        checkCapacity(fruits);
    }

    public float getWeight() {
        float result = 0;
        for (int i = 0; i < fruits.size(); i++) {
            result += fruits.get(i).getWeight();
        }
        return result;
    }

    public void addFruit(T fruit) {
        fruits.add(fruit);
    }

    public boolean compare(Box<?> box) {
        return Math.abs(this.getWeight() - box.getWeight()) < 0.0001;
    }

    public void addFruitsFromAnotherBox(Box<T> box) {
        int count = capacity - fruits.size();
        if (fruits.size() + box.fruits.size() <= capacity) {
            count = box.fruits.size();
        } else {
            System.out.println("Could not add " +
                    (box.fruits.size() - (capacity - fruits.size())) +
                    " fruits! The box " + name + " with capacity " +
                    capacity + " is Full!");
        }
        for (int i = 0; i < count; i++) {
            addFruit(box.fruits.get(box.fruits.size() - 1));
            box.removeFruits(1);
        }
        System.out.println("This box " + name + " now has " + fruits.size() + " fruits with capacity of " + capacity);
        System.out.println("Count of fruits left in other box " + box.name + " is " + box.fruits.size() + "\n");
    }

    private void checkCapacity(List<T> fruits) {
        if (fruits.size() > capacity) {
            System.out.println("This box " + name + " capacity is " + capacity +
                    ". You are trying to put there " +
                    fruits.size() + " fruits. The box " + name + " is full now! " +
                    (fruits.size() - capacity) + " Fruits was lost!");
            removeFruits(fruits.size() - capacity);
        }
    }

    private void removeFruits(int quantity) {
        for (int i = 0; i < quantity; i++) {
            fruits.remove(fruits.size() - 1);
        }
    }
}
