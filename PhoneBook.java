import java.util.ArrayList;
import java.util.HashMap;

public class PhoneBook {

    private final HashMap<String, Person> phoneBook = new HashMap<>();

    public Person getPersonCard(String surname) {
        return phoneBook.get(surname);
    }

    public void putPhone(String surname, String name, String phoneNumber, String eMail) {
        phoneBook.put(surname, new Person(surname, name, phoneNumber, eMail));
    }

    public ArrayList<String> getPhoneNumber(String surname) {
        ArrayList<String> phone = new ArrayList<>();
        for (HashMap.Entry<String, Person> entry : phoneBook.entrySet()) {
            if (entry.getKey().equals(surname)) phone.add(entry.getValue().getPhoneNumber());
        }
        return phone;
    }
}
