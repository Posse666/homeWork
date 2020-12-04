/*
        2. Написать простой класс PhoneBook(внутри использовать HashMap):
        - В качестве ключа использовать фамилию
        - В каждой записи всего два поля: phone, e-mail
        - Отдельный метод для поиска номера телефона по фамилии (ввели фамилию, получили ArrayList телефонов),
        и отдельный метод для поиска e-mail по фамилии. Следует учесть, что под одной фамилией может быть несколько записей.
        Итого должно получиться 3 класса Main, PhoneBook, Person.
*/

public class Main2 {

    private static final PhoneBook phoneBook = new PhoneBook();

    public static void main(String[] args) {
        putPeopleToPhoneBook();
        searchPhoneNumber("Ivanov");

    }

    private static void putPeopleToPhoneBook() {
        phoneBook.putPhone("Gates", "Bill", "8902346767L", "awefwer@weferg.com");
        phoneBook.putPhone("Ivanov", "Ivan", "8436543533L", "ytjulk@wsrthjn.com");
        phoneBook.putPhone("Cook", "Tim", "89765446533L", "ykgk@gyukgyk.com");
        phoneBook.putPhone("Mask", "Elon", "45636453L", "rfthjiuk@rsdth.com");
        phoneBook.putPhone("Jobs", "Steeve", "5435463456L", "iuliloiu@afrefre.com");
        phoneBook.putPhone("Ivanov", "Vasily", "79886989863L", "yujkyil@qertewq.com");
    }

    private static void searchPhoneNumber (String surname){
        System.out.println(phoneBook.getPhoneNumber(surname));
    }



}
