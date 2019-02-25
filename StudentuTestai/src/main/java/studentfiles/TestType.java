package studentfiles;

public enum TestType {
    TEST("testas"),
    TEST_WITH_FEW_ANSWERS("testas su keliais teisingais atsakymais"),
    TEST_WRITE_ANSWERS("testas irasyti atsakynus");
    private String testas;

    TestType(String testas) {
        this.testas = testas;
    }

    public String getTestas() {
        return testas;
    }

    public void setTestas(String testas) {
        this.testas = testas;
    }
}
