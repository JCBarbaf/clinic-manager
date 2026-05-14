
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Phemex6000 {
    static final String HR = "-----------------------------------------------";
    static Scanner scanner;
    static ArrayList<Patient> patients = new ArrayList<>();
    static ArrayList<Appointment> appointments = new ArrayList<>();
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        mainMenu();
        scanner.close();
    }

    public static void mainMenu() {
        boolean exitMenu = false;
        while (!exitMenu) { 
            System.out.print("""
---- Phemex 6000 ----
  1-Dar de alta un paciente
  2-Mostrar lista de pacientes
  3-Crear una nueva cita.
  4-Ver las citas de un paciente
  5-Ver todas las citas registradas.
  0-Salir

> Qué quieres hacer?  """);
            switch (scanner.nextLine()) {
                case "0":
                    exitMenu = true;
                    break;
                case "1":
                    addNewPatient();
                    break;
                case "2":
                    listPatients();
                    break;
                case "3":
                    createAppointment();
                    break;
                case "4":
                    listPatientAppointments();
                    break;
                case "5":
                    listAppointments();
                    break;
                default:
                    System.out.println("!! Por favor, introduce una opción válida !!");
                    break;
            }
        }
        System.out.println("Gracias por usar la aplicación ^_^");
    }

    public static void addNewPatient() {
        Validator validator = new Validator();
        System.out.println("--- Alta Paciente ---");
        System.out.print("Nombre: ");
        String firstName = scanner.nextLine();
        System.out.print("Apellidos: ");
        String lastName = scanner.nextLine();

        boolean validNid = false;
        String nid = "";
        
        while (!validNid) {
            System.out.print("DNI: ");
            nid = scanner.nextLine();
            validNid = validator.validateNid(nid);
            if (!validNid) {
                System.out.println("!! DNI inválido !!");
            }
            validNid = validator.nidIsUnique(patients, nid);
            if (!validNid) {
                System.out.println("!! El DNI ya está registrado !!");
            }
        }

        boolean isInt = false;
        int age = 0;
        while (!isInt) {
            try {
                System.out.print("Edad: ");
                String ageString = scanner.nextLine();
                age = Integer.parseInt(ageString);

                if (age < 0 || age > 120) {
                    throw new IllegalArgumentException();
                }

                isInt = true; // entrada válida
            } catch (Exception e) {
                System.out.println("!! Edad inválida !!");
            }
        }

        boolean validHnum = false;
        String historyNumber = "";

        while (!validHnum) {
            System.out.print("No. Historial: ");
            historyNumber = scanner.nextLine();
            validHnum = validator.validateHistoryNumber(historyNumber);
            if (!validHnum) {
                System.out.println("!! Número de historial inválido !!");
            }
        }

        patients.add(new Patient(firstName, lastName, nid, age, historyNumber));
        System.out.println("Paciente añadido con éxito");
        // System.out.println(patients.getFirst().toString());
    }

    public static void listPatients() {
        System.out.println("-- Listado de pacientes --");
        if (patients.isEmpty()) {
            System.out.println("No hay pacientes registrados");
        } else {
            for (Patient patient : patients) {
                System.out.println(HR);
                System.out.println(patient.toString());
            }
        }
        System.out.println(HR);
    }

    public static void createAppointment() {
        Validator validator = new Validator();
        System.out.println("--- Nueva Cita ---");
        
        boolean validPatient = false;
        Patient patient = null;
        
        while (!validPatient) {
            System.out.print("DNI del paciente: ");
            String patientNid = scanner.nextLine();
            try {
                patient = validator.findPatientByNid(patients, patientNid);
                validPatient = true;
            } catch (Exception e) {
                System.out.println("No se encuentra un paciente con ese DNI.");
            }
        }

        boolean validDateTime = false;
        LocalDateTime dateTime = LocalDateTime.MIN;

        while (!validDateTime) {
            boolean validDate = false;
            String date = "";
    
            while (!validDate) {
                System.out.print("Fecha de la cita (dd/MM/yyyy): ");
                String userInput = scanner.nextLine();
                validDate = validator.isValidDateFormat(userInput);
                if (!validDate) {
                    System.out.println("Formato de fecha inválido.");
                    continue;
                }
                date = userInput;
            }

            boolean validTime = false;
            String time = "";

            while (!validTime) {
                System.out.print("Hora de la cita (hh:mm): ");
                String userInput = scanner.nextLine();
                validTime = validator.isValidTimeFormat(userInput);
                if (!validTime) {
                    System.out.println("Formato de hora inválido.");
                    continue;
                }
                time = userInput;
            }

            validDateTime = validator.isValidDate(date, time);

            if (!validDateTime) {
                System.out.println("Hora de la cita no válida.");
                System.out.println("Solo se pueden crear citas para momentos posteriores a la proxima hora.");
                continue;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            dateTime = LocalDateTime.parse(date + " " + time, formatter);
        }

        System.out.print("Especialidad médica: ");
        String medicalSpecialty = scanner.nextLine();
        
        appointments.add(new Appointment(1, dateTime, medicalSpecialty, "pendiente", patient));
        System.out.println("Cita creada");
    }

    public static void listPatientAppointments() {
        Validator validator = new Validator();
        boolean validPatient = false;
        Patient patient = null;
        
        while (!validPatient) {
            System.out.print("DNI del paciente: ");
            String patientNid = scanner.nextLine();
            try {
                patient = validator.findPatientByNid(patients, patientNid);
                validPatient = true;
            } catch (Exception e) {
                System.out.println("No se encuentra un paciente con ese DNI.");
            }
        }

        final Patient selectedPatient = patient;

        List<Appointment> filteredList = appointments
            .stream()
            .filter(a -> a.getPatient().equals(selectedPatient))
            .toList();
        System.out.println("-- Listado de citas --");
        if (patients.isEmpty()) {
            System.out.println("No hay citas registradas");
        } else {
            for (Appointment appointment : filteredList) {
                System.out.println(HR);
                System.out.println(appointment.toString());
            }
        }
        System.out.println(HR);
    }


    public static void listAppointments() {
        System.out.println("-- Listado de citas --");
        if (patients.isEmpty()) {
            System.out.println("No hay citas registradas");
        } else {
            for (Appointment appointment : appointments) {
                System.out.println(HR);
                System.out.println(appointment.toString());
            }
        }
        System.out.println(HR);
    }
}
