package studentfiles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilTest {
    @Test
    public void enumTest () {
        assertEquals(TestType.TEST, Util.findEnum("testas"));
        assertEquals(TestType.TEST_WITH_FEW_ANSWERS, Util.findEnum("testas su keliais teisingais atsakymais"));
        assertEquals(TestType.TEST_WRITE_ANSWERS, Util.findEnum("testas irasyti atsakynus"));
        assertEquals(null, Util.findEnum("kryziazodis"));

    }
}
