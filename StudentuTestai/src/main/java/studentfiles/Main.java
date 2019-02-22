package studentfiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {


        Path keliasrez = Paths.get(args[1]);
        Path kelias = Paths.get(args[0]);

//Nuskaitom teisingu atsakymu failus
        HashMap<String, ExamTestAnswers> atsakymai = new HashMap<>();
        atsakymai = UtilFiles.getAllAnswers(kelias);
//tikrinam nurodytam aplanke esancius studentu testus
        List<Rezults> studenturezultatai = Util.tikrintiTestuRezultatus(atsakymai, keliasrez);
 //gaunam kelia iki resultatu aplanko
         Path keliasBendras =kelias.getParent();

        Path keliasRezExam = keliasBendras.resolve("Rezult");

//Lyginam su ankstesniais rezultatu failais
        studenturezultatai = Util.checkWithExists(keliasRezExam, studenturezultatai);
//irasom rezultatus i diska
        UtilFiles.printToFile(studenturezultatai, keliasRezExam);
    }
}
