public class Employee {

    private static int counter = 0;
    private final int ID;
    private final String NAME;
    private final String PROFESSION;
    private final int PHONE_NUMBER;
    private final int AGE;
    private int payment;

    Employee(String name, String profession, int phoneNumber, int payment, int age) {
        this.NAME = name;
        this.PROFESSION = profession;
        this.PHONE_NUMBER = phoneNumber;
        this.payment = payment;
        this.AGE = age;
        counter++;
        this.ID = counter;
    }

    public int getID() {
        return ID;
    }

    public String getNAME() {
        return NAME;
    }

    public String getPROFESSION() {
        return PROFESSION;
    }

    public int getPHONE_NUMBER() {
        return PHONE_NUMBER;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public int getAGE() {
        return AGE;
    }
}
