package studentfiles;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudensExamAnswerstest {
    //tikrinam, ar teisingai nuskaito failus su teisingais duomenimis
    @Test
    void getFromFileTestWithDate() {
        StudensExamAnswers studAts = new StudensExamAnswers();
        Path iskur = Paths.get("C:\\Users\\IrmaJ\\ProjektasStudentai\\StudentuTestai\\Duomenys\\StudentsAnswers\\result2.json");
        studAts.getFromFile(iskur);
        String a[] = {"c", "c", "c", "c"};
        assertEquals("00:51", studAts.getEgzam_trukme());
        assertEquals("2019-02-07", studAts.getEgzam_data());
        assertEquals("2365", studAts.getStudent().getId());
        assertEquals("Rima", studAts.getStudent().getVardas());
        assertEquals("Rimute", studAts.getStudent().getPavarde());
        assertArrayEquals(a, studAts.getAts());
    }
    //tikrinam, ar teisingai nuskaito failus su klaidingais duomenimis

    @Test
    void getFromFileTestWithDate_testBadData() {
        StudensExamAnswers studAts = new StudensExamAnswers();
        Path iskur = Paths.get("C:\\Users\\IrmaJ\\ProjektasStudentai\\StudentuTestai\\Duomenys\\StudentsAnswers\\result1.json");
        studAts.getFromFile(iskur);
        String a[] = {"c", "a", "d", "c"};
        assertEquals("01:18", studAts.getEgzam_trukme());
        assertEquals("2019-05-06", studAts.getEgzam_data());
        assertEquals(null, studAts.getStudent().getId());
        assertEquals(null, studAts.getStudent().getVardas());
//        assertEquals("null", studAts.getStudent().getPavarde());
        // assertArrayEquals(a, studAts.getAts());
    }
}