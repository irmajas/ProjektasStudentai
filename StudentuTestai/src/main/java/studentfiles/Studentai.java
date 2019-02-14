package studentfiles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Studentai {
    private static final Logger LOG = (Logger) LogManager.getLogger(ExamTestAnswers.class);
    public static void main(String[] args) {

        Path keliasrez = Paths.get("C:\\Users\\irmaj\\IdeaProjects\\StudentuTestai\\Duomenys\\StudentsAnswers");

        Path kelias = Paths.get("C:\\Users\\irmaj\\IdeaProjects\\StudentuTestai\\Duomenys\\Answers");

        Path duomenys  =Paths.get("C:\\Users\\irmaj\\IdeaProjects\\StudentuTestai\\Duomenys");
        HashMap<String, ExamTestAnswers> atsakymai = new HashMap<>();
        atsakymai = Util.getAllAnswers(kelias);
  if (atsakymai.isEmpty()){
      LOG.warn("Teisingu atsakymu katalogas tuščias ");
      System.exit(1);

  }
       Path rezultatuDir =duomenys.resolve("Rezult");

        if(!Files.exists(rezultatuDir)|| !Files.isDirectory(rezultatuDir)){
            try {
                Files.createDirectory(rezultatuDir);
            } catch (IOException e) {
               LOG.error(" nepavyko sukurti Resultatų direktorijos ");
               System.exit(-2);
            }

        }
// tikrinam egzamino testo rezultatus
         List<Rezults> rezultatai = Util.tikrintiTestuRezultatus(atsakymai,keliasrez);
        System.out.println(rezultatai);
    }
}
