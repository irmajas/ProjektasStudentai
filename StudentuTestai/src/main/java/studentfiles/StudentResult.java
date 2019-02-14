package studentfiles;

public class StudentResult  extends Student {
    int ivertinimas;

    public int getRez() {
        return ivertinimas;
    }

    public void setRez(int rez) {
        this.ivertinimas = rez;
    }

    public StudentResult(String id, String vardas, String pavarde, int rez) {
        super(id, vardas, pavarde);
        this.ivertinimas = rez;
    }

    public StudentResult(int rez) {
        this.ivertinimas = rez;
    }

    @Override
    public String toString() {
        return "StudentResult{" +
                "ivertinimas=" + ivertinimas +
                "} " + super.toString();
    }
}
