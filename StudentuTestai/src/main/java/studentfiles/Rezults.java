package studentfiles;

import java.util.ArrayList;
import java.util.List;

public class Rezults extends Exam {
    List<StudentResult> egzaminoRezultatai;

    public Rezults(String examID, String pavadinimas, String tipas) {
        super(examID, pavadinimas, tipas);
        this.egzaminoRezultatai = new ArrayList<>();
    }

    public List<StudentResult> getEgzaminoRezultatai() {
        return egzaminoRezultatai;
    }

    public void setEgzaminoRezultatai(List<StudentResult> egzaminoRezultatai) {
        this.egzaminoRezultatai = egzaminoRezultatai;
    }

    public void addEgzaminoRezultatai(StudentResult rezz) {
        this.egzaminoRezultatai.add(rezz);
    }

    @Override
    public String toString() {
        return "Rezults{" +
                "egzaminoRezultatai=" + egzaminoRezultatai +
                "} " + super.toString();
    }
}
