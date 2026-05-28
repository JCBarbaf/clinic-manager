
import java.util.ArrayList;

public class Clerk extends Staff {
    private String area;
    private int accessLevel;
    private ArrayList<String> languages = new ArrayList<>();

    public Clerk(int id, String firstName, String lastName, String phoneNumber, String email, String area, int accessLevel) {
        super(id, firstName, lastName, phoneNumber, email);
        this.area = area;
        this.accessLevel = accessLevel;
    }

    public String getArea() {
        return area;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }

    public void addLanguage(String lang) {
        languages.add(lang);
    }

    @Override
    public String toString() {
        return String.format("""
DATOS DEL ADMINISTRATIVO/A
  -ID: %d
  -Nombre completo: %s %s
  -Teléfono: %s
  -Email: %s
  -Área: %s
  -Nivel de acceso: %d
  -Idiomas: %s
            """,
            super.getId(), super.getFirstName(), super.getLastName(), super.getPhoneNumber(), super.getEmail(),
            area, accessLevel, languages.toString()
        );
    }

    
}
