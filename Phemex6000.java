
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Phemex6000 {
    static final String HR = "-----------------------------------------------";
    static Scanner scanner;
    static ArrayList<Patient> patients = new ArrayList<>();
    static ArrayList<Staff> staff = new ArrayList<>();
    static ArrayList<Appointment> appointments = new ArrayList<>();
    static PriorityQueue<UrgentCare> urgentCareQueue = new PriorityQueue<>(
        Comparator
            .comparingInt(UrgentCare::getSeverity).reversed()
            .thenComparing(UrgentCare::getArrival)
    );
    static int firstAvailableID = 0;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        mainMenu();
        scanner.close();
    }

    public static void mainMenu() {
        boolean exitMenu = false;
        while (!exitMenu) { 
            System.out.print("""
\n---- Phemex 6000 ----
  1-  Dar de alta un paciente
  2-  Ver lista de pacientes
  3-  Dar de alta en urgencias
  4-  Ver lista de espera de urgencias
  5-  Atender paciente de urgencias
  6-  Crear una nueva cita
  7-  Ver las citas de un paciente
  8-  Ver todas las citas registradas
  9-  Añadir datos del personal
  10- Ver lista de personal
  11- Ver lista de pacientes de un miembro
  0-Salir

> Qué quieres hacer?\s""");
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
                    addPatientToUrgentCareQueue();
                    break;
                case "4":
                    showUrgentCareQueue();
                    break;
                case "5":
                    callNextUrgentCarePatient();
                    break;
                case "6":
                    createAppointment();
                    break;
                case "7":
                    listPatientAppointments();
                    break;
                case "8":
                    listAppointments();
                    break;
                case "9":
                    addStaff();
                    break;
                case "10":
                    listStaff();
                    break;
                case "11":
                    listPatientsOfStaffMember();
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
        System.out.println("\n--- Alta Paciente ---");
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
                continue;
            }
            validNid = validator.nidIsUnique(patients, nid);
            if (!validNid) {
                System.out.println("!! El DNI ya está registrado !!");
                continue;
            }
            nid = nid.toUpperCase();
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

        boolean validHNum = false;
        String historyNumber = "";

        while (!validHNum) {
            System.out.print("No. Historial: ");
            historyNumber = scanner.nextLine();
            validHNum = validator.validateHistoryNumber(historyNumber);
            if (!validHNum) {
                System.out.println("!! Número de historial inválido !!");
            }
            validHNum = validator.HnumIsUnique(patients, historyNumber);
            if (!validHNum) {
                System.out.println("!! El número de historial ya está registrado !!");
            }
        }
        patients.add(new Patient(firstName, lastName, nid, age, historyNumber));
        System.out.println("Paciente añadido con éxito");
    }

    public static void listPatients() {
        System.out.println("\n-- Listado de pacientes --");
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
        System.out.println("\n-- Nueva Cita --");
        
        boolean validPatient = false;
        Patient patient = null;
        
        while (!validPatient) {
            System.out.print("DNI del paciente: ");
            String patientNid = scanner.nextLine();
            try {
                patient = validator.findPatientByNid(patients, patientNid.toUpperCase());
            } catch (Exception e) {
                System.out.println("No se encuentra un paciente con ese DNI.");
                continue;
            }
            System.out.printf("¿Quieres crear una cita para %s %s? (Y/n)", patient.getFirstName(), patient.getLastName());
            String userAnswer = scanner.nextLine();
            validPatient = validator.yesNoQuestion(userAnswer, true);
            if (!validPatient) {
                System.out.println("Seleccione al paciente");
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
                validDate = validator.validateDateFormat(userInput);
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
                validTime = validator.validateTimeFormat(userInput);
                if (!validTime) {
                    System.out.println("Formato de hora inválido.");
                    continue;
                }
                time = userInput;
            }

            validDateTime = validator.validateDate(date, time);

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
                System.out.println("No se encuentra ningún paciente con este DNI.");
            }
        }

        final Patient selectedPatient = patient;

        List<Appointment> filteredList = appointments
            .stream()
            .filter(a -> a.getPatient().equals(selectedPatient))
            .toList();
        System.out.println("\n-- Listado de citas --");
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
        System.out.println("\n-- Listado de citas --");
        if (appointments.isEmpty()) {
            System.out.println("No hay citas registradas");
        } else {
            for (Appointment appointment : appointments) {
                System.out.println(HR);
                System.out.println(appointment.toString());
            }
        }
        System.out.println(HR);
    }

    public static void addPatientToUrgentCareQueue() {
        Validator validator = new Validator();
        boolean validPatient = false;
        Patient patient = null;
        
        while (!validPatient) {
            System.out.print("DNI del paciente: ");
            String patientNid = scanner.nextLine();
            try {
                patient = validator.findPatientByNid(patients, patientNid.toUpperCase());
            } catch (Exception e) {
                System.out.println("No se encuentra ningún paciente con este DNI.");
                continue;
            }
            validPatient = !validator.isAlreadyInQueue(urgentCareQueue, patientNid);
            if (!validPatient) {
                System.out.println("El paciente ya se encuentra en la lista de espera de urgencias");
            }
            System.out.printf("¿Quieres añadir a %s %s a la lista de espera? (Y/n)", patient.getFirstName(), patient.getLastName());
            String userAnswer = scanner.nextLine();
            validPatient = validator.yesNoQuestion(userAnswer, true);
            if (!validPatient) {
                System.out.println("Seleccione al paciente");
            }
        }

        boolean validSeverity = false;
        int severity = 0;
        while(!validSeverity) {
            System.out.print("Gravedad (leve [1] - grave [10]): ");
            String userInput = scanner.nextLine();
            validSeverity = validator.validateSeverity(userInput);
            if(!validSeverity) {
                System.out.println("Valor inválido para la gravedad");
                continue;
            }
            severity = Integer.parseInt(userInput);
        }

        UrgentCare urgentCare = new UrgentCare(patient, severity);
        urgentCareQueue.add(urgentCare);
        System.out.println("Paciente registrado en urgencias.");
    }

    public static void showUrgentCareQueue() {
        PriorityQueue<UrgentCare> queueCopy = new PriorityQueue<>(urgentCareQueue); 
        System.out.println("\n-- Lista de espera de urgencias --");
        if (queueCopy.isEmpty()) {
            System.out.println("Ningún paciente en la lista de espera de urgencias");
        } else {
            while (!queueCopy.isEmpty()) { 
                System.out.println(HR);
                System.out.println(queueCopy.poll().toString()); 
            }
        }
        System.out.println(HR);
    }

    public static void callNextUrgentCarePatient() {
        if (urgentCareQueue.isEmpty()) {
            System.out.println("Todos los pacientes han sido atendidos");
        } else {
            System.out.println("\n-- Siguiente turno --");
            System.out.println(urgentCareQueue.poll().toString());
            System.out.println(HR);
        }
    }

    public static void addStaff() {
        Validator validator = new Validator();
        System.out.println("\n--- Alta Paciente ---");

        int id = firstAvailableID;
        firstAvailableID++;

        System.out.print("Nombre: ");
        String firstName = scanner.nextLine();
        System.out.print("Apellidos: ");
        String lastName = scanner.nextLine();
        
        boolean validPhone = false;
        String phoneNumber = "";
        while (!validPhone) {
            System.out.print("Teléfono: ");
            phoneNumber = scanner.nextLine();
            validPhone = validator.isValidPhone(phoneNumber);
        }

        boolean validEmail = false;
        String email = "";
        while (!validPhone) {
            System.out.print("Teléfono: ");
            phoneNumber = scanner.nextLine();
            validPhone = validator.isValidPhone(phoneNumber);
        }
    }

    public static void listStaff() {
        System.out.println("\n-- Listado de personal --");
        if (patients.isEmpty()) {
            System.out.println("No hay personal registrados");
        } else {
            for (Staff staffMember : staff) {
                System.out.println(HR);
                System.out.println(staffMember.toString());
            }
        }
        System.out.println(HR);
    }

    public static void listPatientsOfStaffMember() {

    }
}
