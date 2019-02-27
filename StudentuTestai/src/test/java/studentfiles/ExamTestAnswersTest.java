package studentfiles;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ExamTestAnswersTest {
    //Tikrinam ar tesingai nuskaito teisingus duomenis
    @Test
    void getFromFileTestWithDate() {
        ExamTestAnswers atsakymai = new ExamTestAnswers();

        Path iskur = Paths.get("C:\\Users\\IrmaJ\\ProjektasStudentai\\StudentuTestai\\Duomenys\\Answers\\egzas1.json");
        atsakymai.getFromFile(iskur);
        String a[] = {"a", "a", "d", "c"};
        assertEquals("124", atsakymai.getExamID());
        assertEquals("OOP pagrindai", atsakymai.getPavadinimas());
        assertEquals(TestType.TEST, atsakymai.getTipas());
        assertArrayEquals(a, atsakymai.getAts());
    }

    //tikrinam ar teisingai nuskaito klaidingus duomenis
    @Test
    void getFromFileTestwWithWrongData() {
        ExamTestAnswers atsakymai = new ExamTestAnswers();
        Path iskur = Paths.get("C:\\Users\\IrmaJ\\ProjektasStudentai\\StudentuTestai\\Duomenys\\Answers\\egzas2.json");
        atsakymai.getFromFile(iskur);
        String a[] = {"a", "a", "d", "c"};

        assertEquals(null, atsakymai.getExamID());


    }
}
