package studentfiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {

        Path keliasrez = Paths.get("C:\\Users\\irmaj\\ProjektasStudentai\\StudentuTestai\\Duomenys\\StudentsAnswers");

        Path kelias = Paths.get("C:\\Users\\irmaj\\ProjektasStudentai\\StudentuTestai\\Duomenys\\Answers");

        HashMap<String, ExamTestAnswers> atsakymai = new HashMap<>();
        atsakymai = Util.getAllAnswers(kelias);

        List<Rezults> studenturezultatai =Util.tikrintiTestuRezultatus(atsakymai,keliasrez);
     // laikinas testas json eilutes
        ObjectMapper om = new ObjectMapper();
        for (Rezults r: studenturezultatai
             ) {
            String st= om.writeValueAsString(r);
            System.out.println(st);
        }
        //==================
    }
}
