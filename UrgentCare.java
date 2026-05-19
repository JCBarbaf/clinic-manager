
import java.time.LocalDateTime;

public class UrgentCare {
    private Patient patient;
    private int severity;
    private LocalDateTime arrival;

    private String[] severityTexts = {"Leve", "Moderado", "Grave", "Crítico"};

    public UrgentCare(Patient patient, int severity) {
        this.patient = patient;
        this.severity = severity;
        this.arrival = LocalDateTime.now();
    }

    public Patient getPatient() {
        return patient;
    }

    public int getSeverity() {
        return severity;
    }

    public String getSeverityText() {
        int maxSeverity = new Validator().getMaxSeverity();
        int index = (this.severity - 1) * severityTexts.length / maxSeverity;
        return severityTexts[index];
    }

    public LocalDateTime getArrival() {
        return arrival;
    }

    @Override
    public String toString() {
        return String.format("""
DATOS DEL TURNO DE URGENCIAS
  -Paciente: %s %s (DNI: %s)
  -Gravedad: %s(%d)
  -Llegada: %02d/%02d/%04d %02d:%02d
            """,
            this.patient.getFirstName(), this.patient.getLastName(), this.patient.getNid(),
            this.getSeverityText(), this.severity,
            this.arrival.getDayOfMonth(), this.arrival.getMonthValue(), this.arrival.getYear(), this.arrival.getHour(), this.arrival.getMinute()
        );
    }

}
