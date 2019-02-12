package studentfiles;

public class StudentResult  extends Student {
    int rez;

    public int getRez() {
        return rez;
    }

    public void setRez(int rez) {
        this.rez = rez;
    }

    public StudentResult(String id, String vardas, String pavarde, int rez) {
        super(id, vardas, pavarde);
        this.rez = rez;
    }

    public StudentResult(int rez) {
        this.rez = rez;
    }

    @Override
    public String toString() {
        return "StudentResult{" +
                "rez=" + rez +
                "} " + super.toString();
    }
}
