
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import javax.naming.directory.InvalidAttributesException;

public class Validator {

    private int minSeverity = 1;
    private int maxSeverity = 10;

    public Validator() {
    }

    public int getMinSeverity() {
        return minSeverity;
    }

    public int getMaxSeverity() {
        return maxSeverity;
    }

    public boolean validateNid(String nid) {
        if (nid == null || !nid.matches("\\d{8}[A-Za-z]")) {
            return false;
        }

        String numbers = nid.substring(0, 8);
        char letter = Character.toUpperCase(nid.charAt(8));

        String validLetters = "TRWAGMYFPDXBNJZSQVHLCKE";
        int index = Integer.parseInt(numbers) % 23;
        char expectedLetter = validLetters.charAt(index);

        return letter == expectedLetter;
    }

    public boolean  nidIsUnique(ArrayList<Patient> patients, String newNid) {
        for (Patient patient : patients) {
            if (patient.getNid().equals(newNid)) {
                return false;
            }
        }
        return true;
    }

    public boolean validateHistoryNumber(String historyNumber) {
        return historyNumber.matches("\\d{6,12}");
    }

    public Patient findPatientByNid(ArrayList<Patient> patients, String nid) throws InvalidAttributesException {
        for (Patient patient : patients) {
            if (patient.getNid().equals(nid)) {
                return patient;
            }
        }
        throw new InvalidAttributesException();
    }

    public boolean validateDateFormat(String date) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            formatter.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateTimeFormat(String time) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm")
                    .withResolverStyle(ResolverStyle.STRICT);

            formatter.parse(time);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateDate(String date, String time) {
        try {
            if (!validateDateFormat(date) || !validateTimeFormat(time)) {
                return false;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            LocalDateTime inputDateTime = LocalDateTime.parse(date + " " + time, formatter);

            return inputDateTime.isAfter(LocalDateTime.now().plusHours(1));

        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateSeverity(String severityStr) {
        try {
            int severity = Integer.parseInt(severityStr);
            return (severity >= minSeverity && severity <= maxSeverity);
        } catch (Exception e) {
            return false;
        }
    }
}
