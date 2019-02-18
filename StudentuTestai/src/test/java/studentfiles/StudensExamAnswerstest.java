package studentfiles;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudensExamAnswerstest {
    @Test
    void getFromFileTestWithDate() {
        StudensExamAnswers studAts = new StudensExamAnswers();
        Path iskur = Paths.get("C:\\Users\\irmaj\\ProjektasStudentai\\StudentuTestai\\Duomenys\\StudentsAnswers\\rezult1.json");
        studAts.getFromFile(iskur);
        String a[] = {"c", "a", "d", "c"};
        assertEquals("01:18", studAts.getEgzam_trukme());
        assertEquals("2019-05-06", studAts.getEgzam_data());
        assertEquals("1256", studAts.getStudent().getId());
        assertEquals("Petras", studAts.getStudent().getVardas());
        assertEquals("Petraitis", studAts.getStudent().getPavarde());
        assertArrayEquals(a, studAts.ats);
    }
}