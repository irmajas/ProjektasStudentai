package studentfiles;

import java.util.Objects;

public class Student {
    private String id;
    private String vardas;
    private String pavarde;

    public Student(String id, String vardas, String pavarde) {
        this.id = id;
        this.vardas = vardas;
        this.pavarde = pavarde;
    }

    public Student() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVardas() {
        return vardas;
    }

    public void setVardas(String vardas) {
        this.vardas = vardas;
    }

    public String getPavarde() {
        return pavarde;
    }

    public void setPavarde(String pavarde) {
        this.pavarde = pavarde;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", vardas='" + vardas + '\'' +
                ", pavarde='" + pavarde + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return getId().equals(student.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
