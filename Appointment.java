
import java.time.LocalDateTime;

public class Appointment {
    private int id;
    private LocalDateTime date;
    private String medicalSpecialty;
    private Status status;
    private Patient patient;
    
    public enum Status { PENDIENTE, ATENDIDA, CANCELADA };


    public Appointment(int id, LocalDateTime date, String medicalSpecialty, String status, Patient patient) {
        this.id = id;
        this.date = date;
        this.medicalSpecialty = medicalSpecialty;
        this.status = Status.valueOf(status.toUpperCase());
        this.patient = patient;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getMedicalSpecialty() {
        return medicalSpecialty;
    }

    public Status getStatus() {
        return status;
    }

    public Patient getPatient() {
        return patient;
    }

    @Override
    public String toString() {
        return String.format("""
DATOS DE LA CITA
  -Paciente: %s %s (DNI: %s)
  -Fecha: %02d/%02d/%04d %02d:%02d
  -Especialidad médica: %s
  -Estatus: %s
            """,
            this.patient.getFirstName(), this.patient.getLastName(), this.patient.getNid(),
            this.date.getDayOfMonth(), this.date.getMonthValue(), this.date.getYear(), this.date.getHour(), this.date.getMinute(),
            this.medicalSpecialty, this.status
        );
    }
}