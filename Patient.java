

public class Patient {
    private String firstName;
    private String lastName;
    private String nid;
    private int age;
    private String historyNumber;

    public Patient(String firstName, String lastName, String nid, int age, String historyNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nid = nid;
        this.age = age;
        this.historyNumber = historyNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCompleteName() {
        return firstName + " " + lastName;
    }

    public String getNid() {
        return nid;
    }

    public int getAge() {
        return age;
    }

    public String getHistoryNumber() {
        return historyNumber;
    }

    @Override
    public String toString() {
        return String.format("""
DATOS DEL PACIENTE
  -Nombre completo: %s %s
  -DNI: %s
  -Edad: %d
  -Número de historial: %s
            """,
            firstName, lastName, nid, age, historyNumber
        );
    }

}
