package studentfiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Rezults extends Exam{
    List<StudentResult> egzaminoRezultatai;

    public List<StudentResult> getEgzaminoRezultatai() {
        return egzaminoRezultatai;
    }

    public void setEgzaminoRezultatai(StudentResult rezz) {
        this.egzaminoRezultatai.add(rezz);
    }



    public Rezults(String examID, String pavadinimas, String tipas) {
        super(examID, pavadinimas, tipas);
        this.egzaminoRezultatai = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Rezults{" +
                "egzaminoRezultatai=" + egzaminoRezultatai +
                "} " + super.toString();
    }
}
