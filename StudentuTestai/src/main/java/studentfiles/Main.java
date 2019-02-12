package studentfiles;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        Path keliasrez = Paths.get("C:\\Users\\irmaj\\IdeaProjects\\Projektas_Studentai\\Duomenys\\StudentsAnswers");

        Path kelias = Paths.get("C:\\Users\\irmaj\\IdeaProjects\\Projektas_Studentai\\Duomenys\\Answers");

        HashMap<String, ExamTestAnswers> atsakymai = new HashMap<>();
        atsakymai = Util.getAllAnswers(kelias);


    }
}
