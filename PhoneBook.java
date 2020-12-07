import java.util.ArrayList;
import java.util.HashMap;

public class PhoneBook {

    private final HashMap<String, ArrayList<Person>> phoneBook = new HashMap<>();

    public void putPhone(String surname, String name, String phoneNumber, String eMail) {
        if (!phoneBook.containsKey(surname)) phoneBook.put(surname, new ArrayList<>());
        phoneBook.get(surname).add(new Person(surname, name, phoneNumber, eMail));
    }

    public ArrayList<String> getPhoneNumber(String surname) {
        return getPersonData(surname, true, false);
    }

    public ArrayList<String> getEMail(String surname) {
        return getPersonData(surname, false, true);
    }

    private ArrayList<String> getPersonData(String surname, boolean phoneNumber, boolean eMail) {
        ArrayList<String> data = new ArrayList<>();
        for (HashMap.Entry<String, ArrayList<Person>> entry : phoneBook.entrySet()) {
            if (entry.getKey().equals(surname)) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    if (phoneNumber) data.add(entry.getValue().get(i).getPhoneNumber());
                    if (eMail) data.add(entry.getValue().get(i).getEMail());
                }
            }
        }
        return data;
    }
}
