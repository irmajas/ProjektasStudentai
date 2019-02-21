package studentfiles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountingExamRezulttest {

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
        rez = CountingExamRezult.getRezult(studensExamAnswers, exam);
        assertEquals(100, rez);

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
        rez = CountingExamRezult.getRezult(studensExamAnswers, exam);
        assertEquals(-1, rez);

    }

    @Test
    public void getRezultWithfewAnswers_test() {
        Student stud = new Student("123", "Jonas", "JOnaitis");
        ExamTestAnswers exam = new ExamTestAnswers();
        exam.setExamID("124");
        exam.setPavadinimas("OOP");
        exam.setTipas("testas");
        String[] teisats = {"ab", "bd", "ca", "ab", "d", "cd"};
        String[] ats = {"ab", "bd", "cda", "abc", "dc", "ca"};
        exam.setAts(teisats);
        StudensExamAnswers studensExamAnswers = new StudensExamAnswers();
        studensExamAnswers.setStudent(stud);
        studensExamAnswers.setExamID("124");
        studensExamAnswers.setPavadinimas("OOP");
        studensExamAnswers.setTipas("testas");

        studensExamAnswers.setAts(ats);
        int rez;
        rez = CountingExamRezult.getRezultWithfewAnswers(studensExamAnswers, exam);
        assertEquals(70, rez);

    }
    @Test
    public void getRezultWithfewAnswers_test2() {
        Student stud = new Student("123", "Jonas", "JOnaitis");
        ExamTestAnswers exam = new ExamTestAnswers();
        exam.setExamID("124");
        exam.setPavadinimas("OOP");
        exam.setTipas("testas");
        String[] teisats = {"ab", "bd", "ca", "ab", "d", "cd"};
        String[] ats = {"ab", "bd", "cda", "abc", "dc"};
        exam.setAts(teisats);
        StudensExamAnswers studensExamAnswers = new StudensExamAnswers();
        studensExamAnswers.setStudent(stud);
        studensExamAnswers.setExamID("124");
        studensExamAnswers.setPavadinimas("OOP");
        studensExamAnswers.setTipas("testas");

        studensExamAnswers.setAts(ats);
        int rez;
        rez = CountingExamRezult.getRezultWithfewAnswers(studensExamAnswers, exam);
        assertEquals(-1, rez);

    }

}
