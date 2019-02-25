package studentfiles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountingExamRezulttest {
    //tikrinam vieno teisingo atsakymo testą su teisingais duomenimis
    @Test
    public void rezult_test() {
        Student stud = new Student("123", "Jonas", "JOnaitis");
        ExamTestAnswers exam = new ExamTestAnswers();
        exam.setExamID("124");
        exam.setPavadinimas("OOP");
        exam.setTipas(TestType.TEST);
        String[] ats = {"a", "b", "c", "a", "d", "c"};
        exam.setAts(ats);
        StudensExamAnswers studensExamAnswers = new StudensExamAnswers();
        studensExamAnswers.setStudent(stud);
        studensExamAnswers.setExamID("124");
        studensExamAnswers.setPavadinimas("OOP");
        studensExamAnswers.setTipas(TestType.TEST);
        studensExamAnswers.setAts(ats);
        int rez;
        rez = CountingExamRezult.getRezult(studensExamAnswers, exam);
        assertEquals(100, rez);

    }

    //tikrinam vieno teisingo atsakymo testą su klaidingais duomenimis
    @Test
    public void rezult_testklaida() {
        Student stud = new Student("123", "Jonas", "JOnaitis");
        ExamTestAnswers exam = new ExamTestAnswers();
        exam.setExamID("124");
        exam.setPavadinimas("OOP");
        exam.setTipas(TestType.TEST);
        String[] ats = {"a", "b", "c", "a", "d"};
        String[] ats1 = {"a", "b", "c", "a", "d", "c"};
        exam.setAts(ats);
        StudensExamAnswers studensExamAnswers = new StudensExamAnswers();
        studensExamAnswers.setStudent(stud);
        studensExamAnswers.setExamID("124");
        studensExamAnswers.setPavadinimas("OOP");
        studensExamAnswers.setTipas(TestType.TEST);
        studensExamAnswers.setAts(ats1);
        int rez;
        rez = CountingExamRezult.getRezult(studensExamAnswers, exam);
        assertEquals(-1, rez);

    }

    //tikrinam testa su keliais galimais atsakymais su teisingais duomenimis
    @Test
    public void getRezultWithfewAnswers_test() {
        Student stud = new Student("123", "Jonas", "JOnaitis");
        ExamTestAnswers exam = new ExamTestAnswers();
        exam.setExamID("124");
        exam.setPavadinimas("OOP");
        exam.setTipas(TestType.TEST);
        String[] teisats = {"ab", "bd", "ca", "ab", "d", "cd"};
        String[] ats = {"ab", "bd", "cda", "abc", "dc", "ca"};
        exam.setAts(teisats);
        StudensExamAnswers studensExamAnswers = new StudensExamAnswers();
        studensExamAnswers.setStudent(stud);
        studensExamAnswers.setExamID("124");
        studensExamAnswers.setPavadinimas("OOP");
        studensExamAnswers.setTipas(TestType.TEST_WITH_FEW_ANSWERS);

        studensExamAnswers.setAts(ats);
        int rez;
        rez = CountingExamRezult.getRezultWithfewAnswers(studensExamAnswers, exam);
        assertEquals(70, rez);

    }

    //tikrinam testa su keliais galimais atsakymais su klaidingais duomenimis
    @Test
    public void getRezultWithfewAnswers_test2() {
        Student stud = new Student("123", "Jonas", "JOnaitis");
        ExamTestAnswers exam = new ExamTestAnswers();
        exam.setExamID("124");
        exam.setPavadinimas("OOP");
        exam.setTipas(TestType.TEST_WITH_FEW_ANSWERS);
        String[] teisats = {"ab", "bd", "ca", "ab", "d", "cd"};
        String[] ats = {"ab", "bd", "cda", "abc", "dc"};
        exam.setAts(teisats);
        StudensExamAnswers studensExamAnswers = new StudensExamAnswers();
        studensExamAnswers.setStudent(stud);
        studensExamAnswers.setExamID("124");
        studensExamAnswers.setPavadinimas("OOP");
        studensExamAnswers.setTipas(TestType.TEST_WITH_FEW_ANSWERS);

        studensExamAnswers.setAts(ats);
        int rez;
        rez = CountingExamRezult.getRezultWithfewAnswers(studensExamAnswers, exam);
        assertEquals(-1, rez);

    }

    //tikrinam testa irasomais atsakymais su teisingais duomenimis
    @Test
    public void getRezultWithAnswers_test1() {
        Student stud = new Student("123", "Jonas", "JOnaitis");
        ExamTestAnswers exam = new ExamTestAnswers();
        exam.setExamID("154");
        exam.setPavadinimas("Eglish");
        exam.setTipas(TestType.TEST_WRITE_ANSWERS);
        String[] teisats = {"and,or", "the,the,a", "are,am,is", "have,have,has", "was,been,have", "is,is,are"};
        String[] ats = {"and,or", "the,the,a", "are,am,is", "have,, has", "was,been", "is,is,are"};
        exam.setAts(teisats);
        StudensExamAnswers studensExamAnswers = new StudensExamAnswers();
        studensExamAnswers.setStudent(stud);
        studensExamAnswers.setExamID("124");
        studensExamAnswers.setPavadinimas("OOP");
        studensExamAnswers.setTipas(TestType.TEST_WRITE_ANSWERS);

        studensExamAnswers.setAts(ats);
        int rez;
        rez = CountingExamRezult.getRezultWithAnswers(studensExamAnswers, exam);
        assertEquals(88, rez);

    }

    //tikrinam testa irasomais atsakymais su klaidingais duomenimis
    @Test
    public void getRezultWithAnswers_test2() {
        Student stud = new Student("123", "Jonas", "JOnaitis");
        ExamTestAnswers exam = new ExamTestAnswers();
        exam.setExamID("154");
        exam.setPavadinimas("Eglish");
        exam.setTipas(TestType.TEST_WRITE_ANSWERS);
        String[] teisats = {"and,or", "the,the,a", "are,am,is", "have,have,has", "was,been,have", "is,is,are"};
        String[] ats = {"and,or", "the,the,a", "are,am,is", "have,, has", "was,been"};
        exam.setAts(teisats);
        StudensExamAnswers studensExamAnswers = new StudensExamAnswers();
        studensExamAnswers.setStudent(stud);
        studensExamAnswers.setExamID("124");
        studensExamAnswers.setPavadinimas("OOP");
        studensExamAnswers.setTipas(TestType.TEST_WRITE_ANSWERS);

        studensExamAnswers.setAts(ats);
        int rez;
        rez = CountingExamRezult.getRezultWithAnswers(studensExamAnswers, exam);
        assertEquals(-1, rez);

    }
}
