import java.util.ArrayList;
import java.util.StringJoiner;

public class Nurse extends Staff {
    private String area;
    private ArrayList<Patient> assignedPatients = new ArrayList<>();

    public Nurse(int id, String firstName, String lastName, String phoneNumber, String email, String assignedFloor) {
        super(id, firstName, lastName, phoneNumber, email);
        this.area = assignedFloor;
    }

    public String getArea() {
        return area;
    }

    public ArrayList<Patient> getAssignedPatients() {
        return assignedPatients;
    }

    public void showAssignedPatients() {
        StringJoiner patientList = new StringJoiner(", ");

        for (Patient patient : assignedPatients) {
            patientList.add(String.format("%s %s (%s)",
                patient.getFirstName(),
                patient.getLastName(),
                patient.getNid()));
        }
        
        System.out.printf("\nPacientes del enfermero/a %s %s: %s", 
            super.getFirstName(), super.getLastName(), patientList.toString());
        
    }

    @Override
    public String toString() {
        return String.format("""
DATOS DEL ENFERMERO/A
  -ID: %d
  -Nombre completo: %s %s
  -Teléfono: %s
  -Email: %s
  -Área: %s
  -Número de pacientes asignados: %d
            """,
            super.getId(), super.getFirstName(), super.getLastName(), super.getPhoneNumber(), super.getEmail(),
            area, assignedPatients.size()
        );
    }

}
