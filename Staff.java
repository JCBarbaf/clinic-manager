public class Staff {
    private int id;
    private String firstName; 
    private String lastName;
    private String phoneNumber;
    private String email;

    public Staff(int id, String firstName, String lastName, String phoneNumber, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return String.format("""
DATOS DEL EMPLEADO
  -ID: %d
  -Nombre completo: %s %s
  -Teléfono: %s
  -Email: %s
            """,
            id, firstName, lastName, phoneNumber, email
        );
    }

}
