package studentfiles;

public abstract class Exam {
    private String examID;
    private String pavadinimas;
    private String tipas;

    public Exam() {

    }

    public Exam(String examID, String pavadinimas, String tipas) {
        this.examID = examID;
        this.pavadinimas = pavadinimas;
        this.tipas = tipas;
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public String getPavadinimas() {
        return pavadinimas;
    }

    public void setPavadinimas(String pavadinimas) {
        this.pavadinimas = pavadinimas;
    }

    public String getTipas() {
        return tipas;
    }

    public void setTipas(String tipas) {
        this.tipas = tipas;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "examID='" + examID + '\'' +
                ", pavadinimas='" + pavadinimas + '\'' +
                ", tipas='" + tipas + '\'' +
                '}';
    }


}
