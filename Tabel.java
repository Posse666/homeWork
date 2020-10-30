public class Tabel {

    private final static Employee[] EMPLOYEES = new Employee[5];
    private final static String[] NAMES = {"Pupkin V.V.", "Ivanov I.I.", "Petrov P.P.", "Mask I.", "Gates B."};
    private final static String[] PROFESSIONS = {"Flayer", "Driver", "Worker", "Engineer", "Diver"};
    private final static int[] PHONE_NUMBERS = {5641616, 15816, 944, 618616, 19461};
    private final static int[] AGES = {45, 33, 20, 50, 70};
    private final static int[] PAYMENTS = {100000, 30000, 20000, 80000, 50000};

    public static void main(String[] args) {
        initEmployees();
        readNamesAndProfessions();
        getOldEmployees();
        risePayment();
    }

    private static void initEmployees() {
        for (int i = 0; i < EMPLOYEES.length; i++) {
            EMPLOYEES[i] = new Employee(NAMES[i], PROFESSIONS[i], PHONE_NUMBERS[i], PAYMENTS[i], AGES[i]);
        }
    }

    private static void readNamesAndProfessions() {
        System.out.println("Список сотрудников:\n");
        for (int i = 0; i < EMPLOYEES.length; i++) {
            System.out.println("Сотрудник №" + EMPLOYEES[i].getID() + ":\n" +
                    "ФИО: " + EMPLOYEES[i].getNAME() + "\n" +
                    "Профессия: " + EMPLOYEES[i].getPROFESSION() + "\n");
        }
    }

    private static void getOldEmployees() {
        int oldAge = 40;
        System.out.println("Сотрудники старше " + oldAge + ":\n");
        for (int i = 0; i < EMPLOYEES.length; i++) {
            if (EMPLOYEES[i].getAGE() > oldAge) {
                System.out.println("Сотрудник №" + EMPLOYEES[i].getID() + ":\n" +
                        "ФИО: " + EMPLOYEES[i].getNAME() + "\n" +
                        "Профессия: " + EMPLOYEES[i].getPROFESSION() + "\n" +
                        "Телефон: " + EMPLOYEES[i].getPHONE_NUMBER() + "\n" +
                        "Возраст: " + EMPLOYEES[i].getAGE() + "\n" +
                        "Зарплата: " + EMPLOYEES[i].getPayment() + "\n");
            }
        }
    }

    private static void risePayment() {
        int neededAgeToRisePayment = 45;
        int paymentRise = 15000;
        System.out.println("Приказ о повышении зарплаты сотрудникам старше " + neededAgeToRisePayment + " лет на " + paymentRise + " монет:");
        for (int i = 0; i < EMPLOYEES.length; i++) {
            if (EMPLOYEES[i].getAGE() > neededAgeToRisePayment) {
                System.out.print("Сотрудник №" + EMPLOYEES[i].getID() + ":\n" +
                        "ФИО: " + EMPLOYEES[i].getNAME() + "\n" +
                        "Возраст :" + EMPLOYEES[i].getAGE() + "\n" +
                        "Бывшая зарплата: " + EMPLOYEES[i].getPayment() + ", ");
                EMPLOYEES[i].setPayment(EMPLOYEES[i].getPayment() + paymentRise);
                System.out.println("Новая зарплата: " + EMPLOYEES[i].getPayment() + "\n");
            }
        }
    }
}