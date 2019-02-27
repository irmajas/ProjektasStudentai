package studentfiles;

import org.junit.jupiter.api.Test;
import students.StudensExamAnswers;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudensExamAnswerstest {
    //tikrinam, ar teisingai nuskaito failus su teisingais duomenimis
    @Test
    void getFromFileTestWithDate() {
        StudensExamAnswers studAts = new StudensExamAnswers();
        Path iskur = Paths.get("src\\main\\resources\\result2.json");
        studAts.getFromFile(iskur);
        String a[] = {"c", "c", "c", "c"};
        assertEquals("00:51", studAts.getEgzamTrukme());
        assertEquals("2019-02-07", studAts.getEgzamData());
        assertEquals("2365", studAts.getStudent().getId());
        assertEquals("Rima", studAts.getStudent().getVardas());
        assertEquals("Rimute", studAts.getStudent().getPavarde());
        assertArrayEquals(a, studAts.getAts());
    }
    //tikrinam, ar teisingai nuskaito failus su klaidingais duomenimis

    @Test
    void getFromFileTestWithDate_testBadData() {
        StudensExamAnswers studAts = new StudensExamAnswers();
        Path iskur = Paths.get("src\\main\\resources\\result1.json");
        studAts.getFromFile(iskur);
        String a[] = {"c", "a", "d", "c"};
        assertEquals("01:18", studAts.getEgzamTrukme());
        assertEquals("2019-05-06", studAts.getEgzamData());
        assertEquals(null, studAts.getStudent().getId());
        assertEquals(null, studAts.getStudent().getVardas());
//        assertEquals("null", studAts.getStudent().getPavarde());
        // assertArrayEquals(a, studAts.getAts());
    }
}