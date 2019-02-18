package studentfiles;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExamTestAnswersTest {
    @Test
    void getFromFileTestWithDate() {
        ExamTestAnswers atsakymai = new ExamTestAnswers();
        Path iskur = Paths.get("C:\\Users\\irmaj\\ProjektasStudentai\\StudentuTestai\\Duomenys\\Answers\\egzas1.json");
        atsakymai.getFromFile(iskur);
        String a[] = {"a", "a", "d", "c"};
        assertEquals("124", atsakymai.getExamID());
        assertEquals("OOP pagrindai", atsakymai.getPavadinimas());
        assertEquals("testas", atsakymai.getTipas());
        assertArrayEquals(a, atsakymai.getAts());
    }
}
