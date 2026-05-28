
import java.util.ArrayList;
import java.util.StringJoiner;



public class Doctor extends Staff {
    private String medicalLicense; //9 dígitos
    private String specialty;
    private ArrayList<Patient> assignedPatients = new ArrayList<>();

    public Doctor(int id, String firstName, String lastName, String phoneNumber, String email, String medicalLicense, String specialty) {
        super(id, firstName, lastName, phoneNumber, email);
        this.medicalLicense = medicalLicense;
        this.specialty = specialty;
    }

    public String getMedicalLicense() {
        return medicalLicense;
    }

    public String getSpecialty() {
        return specialty;
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
        
        System.out.printf("\nPacientes del dr. %s %s: %s", 
            super.getFirstName(), super.getLastName(), patientList.toString());
        
    }

    @Override
    public String toString() {
        return String.format("""
DATOS DEL DOCTOR/A
  -ID: %d
  -Nombre completo: %s %s
  -Teléfono: %s
  -Email: %s
  -Número de colegido: %s
  -Especialidad: %s
  -Número de pacientes asignados: %d
            """,
            super.getId(), super.getFirstName(), super.getLastName(), super.getPhoneNumber(), super.getEmail(),
            medicalLicense, specialty, assignedPatients.size()
        );
    }
}
