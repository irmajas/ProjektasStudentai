package studentfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;


public class Util {

    private static final Logger LOG = (Logger) LogManager.getLogger(Util.class);

    //Nuskaityti visus atsakymu failus
    static HashMap<String, ExamTestAnswers> getAllAnswers(Path kelias) {
        HashMap<String, ExamTestAnswers> mas = new HashMap<>();
        try {
            if (Files.isDirectory(kelias)) {

                List<String> collect = Files.walk(Paths.get(String.valueOf(kelias)))
                        .filter(Files::isRegularFile)
                        .map(path -> path.getFileName().toString())
                        .collect(Collectors.toList());
                for (String s : collect
                ) {
                    Path isKur = kelias.resolve(s);
                    System.out.println(isKur);
                    ExamTestAnswers ats = new ExamTestAnswers();
                    ats.getFromFile(isKur);

                    mas.put(ats.getExamID(), ats);
                }
                //  return mas;
            } else {
                LOG.error("Neteisingai nurodytaa teisingu atsakymų direktorija aplankas: {}", kelias);

                System.exit(-1);
            }
        } catch (IOException e) {
            LOG.error("IO klaida nuskaitat teisingu atsakymu faila: {}", e.getMessage());

        }
        return mas;
    }

    //Paskaiciuoti testo rezultata
    static int getRezult(StudensExamAnswers studentans, ExamTestAnswers trueAnswers) {
        int result = 0;
        int kiekKlausimu = studentans.getAts().length;
        if (kiekKlausimu != trueAnswers.getAts().length) {
            LOG.error("Studentas {} {} laikė agzaminą {} {}. Pateiktų atsakymų skaičua nesutampa su pateikiamų klausimų skaičiumi",
                    studentans.getStudent().getVardas(), studentans.getStudent().getPavarde(),
                    studentans.getExamID(), studentans.getPavadinimas());
            return -1;
        }
        for (int i = 0; i < kiekKlausimu; i++)
            if (studentans.getAts()[i].equals(trueAnswers.getAts()[i])) result++;
        LOG.info(" Studentas {} {} laike agzamina {} {}. Rezultatas {}",
                studentans.getStudent().getVardas(), studentans.getStudent().getPavarde()
                , studentans.getExamID(), studentans.getPavadinimas(), result);
        return result;
    }

    // Tikrinti testu rezultatus
    static HashMap<String, List<StudentResult>> tikrintiTestuRezultatus(HashMap<String, ExamTestAnswers> atsakymai, Path keliasrez) {
        // paruosiam mapa kiekvieno egzamino rezultatams
        HashMap<String, List<StudentResult>> rezultatai = new HashMap<>();

        for (Map.Entry<String, ExamTestAnswers> ent : atsakymai.entrySet()
        ) {
            System.out.println(ent.getKey());
            System.out.println(ent.getValue());
            rezultatai.put(ent.getKey(), new ArrayList<StudentResult>());
        }
        List<String> collect = new ArrayList<>();
        if (Files.isDirectory(keliasrez)) {
            try {
                collect = Files.walk(Paths.get(String.valueOf(keliasrez)))
                        .filter(Files::isRegularFile)
                        .map(path -> path.getFileName().toString())
                        .collect(Collectors.toList());
            } catch (IOException e) {
                LOG.error(" klaida nuskaitant  stutentu rezultatu failus ");
                System.exit(-1);
            }
            for (String s : collect
            ) {
                Path iskur = keliasrez.resolve(s);
                StudensExamAnswers studAts = new StudensExamAnswers();

                studAts.getFromFile(iskur);

                if (atsakymai.containsKey(studAts.getExamID())) {
                    int rez = getRezult(studAts, atsakymai.get(studAts.getExamID()));
                    if (rez != -1) {
                        StudentResult studRez = new StudentResult(studAts.getStudent().getId(), studAts.getStudent().getVardas()
                                , studAts.getStudent().getPavarde(), rez);

                        List<StudentResult> yrarez;
                        yrarez = rezultatai.get(studAts.getExamID());

                        yrarez.add(studRez);
                        rezultatai.put(studAts.getExamID(), yrarez);

                    }
                } else {
                    LOG.warn("Nera teisingu atsakymo failo egzaminui {} {} arba klaidingas egzamino kodas",
                            studAts.getExamID(), studAts.getPavadinimas());
                }

            }
        }

        return rezultatai;
    }
}
