
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import javax.naming.directory.InvalidAttributesException;

public class Validator {

    public Validator() {
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

    public boolean isValidDateFormat(String date) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            formatter.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isValidTimeFormat(String time) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm")
                    .withResolverStyle(ResolverStyle.STRICT);

            formatter.parse(time);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isValidDate(String date, String time) {
        try {
            if (!isValidDateFormat(date) || !isValidTimeFormat(time)) {
                return false;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            LocalDateTime inputDateTime = LocalDateTime.parse(date + " " + time, formatter);

            return inputDateTime.isAfter(LocalDateTime.now().plusHours(1));

        } catch (Exception e) {
            return false;
        }
    }
}
