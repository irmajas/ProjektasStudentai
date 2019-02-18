package studentfiles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilTest {

    @Test
    public void rezult_test() {
        Student stud = new Student("123", "Jonas", "JOnaitis");
        ExamTestAnswers exam = new ExamTestAnswers();
        exam.setExamID("124");
        exam.setPavadinimas("OOP");
        exam.setTipas("testas");
        String[] ats = {"a", "b", "c", "a", "d", "c"};
        exam.setAts(ats);
        StudensExamAnswers studensExamAnswers = new StudensExamAnswers();
        studensExamAnswers.setStudent(stud);
        studensExamAnswers.setExamID("124");
        studensExamAnswers.setPavadinimas("OOP");
        studensExamAnswers.setTipas("testas");
        studensExamAnswers.setAts(ats);
        int rez;
        rez = Util.getRezult(studensExamAnswers, exam);
        assertEquals(6, rez);

    }

    @Test
    public void rezult_testklaida() {
        Student stud = new Student("123", "Jonas", "JOnaitis");
        ExamTestAnswers exam = new ExamTestAnswers();
        exam.setExamID("124");
        exam.setPavadinimas("OOP");
        exam.setTipas("testas");
        String[] ats = {"a", "b", "c", "a", "d"};
        String[] ats1 = {"a", "b", "c", "a", "d", "c"};
        exam.setAts(ats);
        StudensExamAnswers studensExamAnswers = new StudensExamAnswers();
        studensExamAnswers.setStudent(stud);
        studensExamAnswers.setExamID("124");
        studensExamAnswers.setPavadinimas("OOP");
        studensExamAnswers.setTipas("testas");
        studensExamAnswers.setAts(ats1);
        int rez;
        rez = Util.getRezult(studensExamAnswers, exam);
        assertEquals(-1, rez);

    }
}
