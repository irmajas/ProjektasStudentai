package studentfiles;

import java.util.ArrayList;
import java.util.List;

public class Rezults extends Exam {
    private List<StudentResult> egzaminoRezultatai= new ArrayList<>();

//    public Rezults(List<StudentResult> egzaminoRezultatai) {
//        this.egzaminoRezultatai = egzaminoRezultatai;
//    }

//    public Rezults(String examID, String pavadinimas, String tipas, List<StudentResult> egzaminoRezultatai) {
//        super(examID, pavadinimas, tipas);
//        this.egzaminoRezultatai = egzaminoRezultatai;
 //   }

    public Rezults(String examID, String pavadinimas, String tipas) {
        super(examID, pavadinimas, tipas);
        this.egzaminoRezultatai = new ArrayList<>();
    }

    public Rezults() {
        super();
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
