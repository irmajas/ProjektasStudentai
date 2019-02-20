package studentfiles;

public class StudentResult extends Student {
    private String egzam_data;
    private String egzam_trukme;
    private int ivertinimas;

    public StudentResult(String id, String vardas, String pavarde, int rez) {
        super(id, vardas, pavarde);
        this.ivertinimas = rez;
    }

    public StudentResult(int rez) {
        this.ivertinimas = rez;
    }

    public int getRez() {
        return ivertinimas;
    }

    public String getEgzam_data() {
        return egzam_data;
    }

    public void setEgzam_data(String egzam_data) {
        this.egzam_data = egzam_data;
    }

    public String getEgzam_trukme() {
        return egzam_trukme;
    }

    public void setEgzam_trukme(String egzam_trukme) {
        this.egzam_trukme = egzam_trukme;
    }

    public void setRez(int rez) {
        this.ivertinimas = rez;
    }

    @Override
    public String toString() {
        return "StudentResult{" +
                "ivertinimas=" + ivertinimas +
                "} " + super.toString();
    }
}
