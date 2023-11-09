import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

class UserData {
    private String surname;
    private String firstName;
    private String middleName;
    private LocalDate dateOfBirth;
    private long phoneNumber;
    private char gender;

    public UserData(String[] data) {
        if (data.length != 6) {
            throw new IllegalArgumentException("Invalid data: required 6 parameters");
        }

        this.surname = data[0];
        this.firstName = data[1];
        this.middleName = data[2];
        this.dateOfBirth = parseDateOfBirth(data[3]);
        this.phoneNumber = parsePhoneNumber(data[4]);
        this.gender = parseGender(data[5]);
    }

    private LocalDate parseDateOfBirth(String str) {
        try {
            return LocalDate.parse(str, java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (java.time.format.DateTimeParseException ex) {
            throw new IllegalArgumentException("Invalid date of birth format");
        }
    }

    private long parsePhoneNumber(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
    }

    private char parseGender(String str) {
        if (str.length() != 1 || !str.matches("[fmFM]")) {
            throw new IllegalArgumentException("Invalid gender format");
        }
        return Character.toLowerCase(str.charAt(0));
    }

    public String getFullName() {
        return surname + firstName + middleName;
    }

    public String getFormattedData() {
        return surname + " " + firstName + " " + middleName + " " +
            dateOfBirth.format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " " +
            phoneNumber + " " + gender;
    }
}

public class Program {
    public static void main(String[] args) {
        String input = "Иванов Александр Сергеевич 14.01.1990 9632587456 m";
        String[] userData = input.split(" ");

        try {
            UserData user = new UserData(userData);
            String filename = user.getFullName() + ".txt";
            writeToFile(filename, user.getFormattedData());
            System.out.println("Data saved successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void writeToFile(String filename, String data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(data);
            writer.newLine();
        }
    }
}