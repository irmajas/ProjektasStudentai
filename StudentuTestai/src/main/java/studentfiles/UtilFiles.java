package studentfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class UtilFiles {
    private static final Logger LOG = (Logger) LogManager.getLogger(UtilFiles.class);

    //Nuskaityti visus atsakymu failus
    static HashMap<String, ExamTestAnswers> getAllAnswers(Path kelias) {
        HashMap<String, ExamTestAnswers> mas = new HashMap<>();
        List<String> collect = getFileList(kelias);
        for (String s : collect) {
            Path isKur = kelias.resolve(s);
            ExamTestAnswers ats = new ExamTestAnswers();
            ats.getFromFile(isKur);
            if (!(ats.getPavadinimas() == null))
                mas.put(ats.getExamID(), ats);
            LOG.info("Ikelti egzamino {} {} teisingi atsakymai", ats.getExamID(), ats.getPavadinimas());
        }
        return mas;

    }

    //gauti sarasa failu, esanciu direktorijoje
    static List<String> getFileList(Path kelias) {
        if (Files.isDirectory(kelias)) {
            try {

                List<String> collect = Files.walk(Paths.get(String.valueOf(kelias)))
                        .filter(Files::isRegularFile)
                        .map(path -> path.getFileName().toString())
                        .collect(Collectors.toList());
                return collect;
            } catch (IOException e) {
                LOG.error(" Klaida 01 nuskaitant failus aplanke {}", kelias.getFileName());
                System.exit(-1);
            }
        }
        return null;
    }

    //isvesti rezultatus i failus
    static void printToFile(List<Rezults> rezultatai, Path kelias) {
        ObjectMapper om = new ObjectMapper();
        if (Files.notExists(kelias)) {
            try {
                Files.createDirectory(kelias);
            } catch (IOException e) {
                LOG.error(" klaida 02: nepavyko sukurti direktorija egzamino rezultatu failams", kelias.getFileName());
                System.exit(-1);
            }
        }
        for (Rezults rez : rezultatai) {
            File fileatsalymas = new File(String.valueOf(Paths.get(String.valueOf(kelias)).resolve("Exam" + rez.getExamID() +  ".json")));
            try {
               om.writeValue(fileatsalymas, rez);
               LOG.info("Įrašyti egzamino {} {} rezultatai",rez.getExamID(), rez.getPavadinimas());
            } catch (IOException e) {
                LOG.warn("Klaida 03: nepavyko įrašyti egzamino rezultatų failo  {}", "Exam" + rez.getExamID() + rez.getPavadinimas());

            }
        }
    }
}
