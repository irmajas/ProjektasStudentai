package studentfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;


public class Util {

    private static final Logger LOG = (Logger) LogManager.getLogger(Util.class);

    //Nuskaityti visus atsakymu failus
    static HashMap<String, ExamTestAnswers> getAllAnswers(Path kelias) {
        HashMap<String, ExamTestAnswers> mas = new HashMap<>();
       List<String> collect= Util.getFileList(kelias);
                for (String s : collect
                ) {
                    Path isKur = kelias.resolve(s);
                    System.out.println(isKur);
                    ExamTestAnswers ats = new ExamTestAnswers();
                    ats.getFromFile(isKur);

                    mas.put(ats.getExamID(), ats);
                }


        return mas;
    }

    //Paskaiciuoti testo rezultata
    static int getRezult(StudensExamAnswers studentans, ExamTestAnswers trueAnswers) {
        int result = 0;
        int kiekKlausimu = studentans.getAts().length;
        if (kiekKlausimu != trueAnswers.getAts().length) {
            LOG.warn("Studentas {} {} laikė agzaminą {} {}. Pateiktų atsakymų skaičua nesutampa su pateikiamų klausimų skaičiumi",
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
    //gauti sarasa failu, esanciu direktorijoje
    static List<String> getFileList (Path kelias){
    if (Files.isDirectory(kelias)) {

        try {
            List<String> collect = Files.walk(Paths.get(String.valueOf(kelias)))
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
            return collect;
        } catch (IOException e) {
            LOG.error(" klaida nuskaitant  failus aplanke {}", kelias.getFileName());
            System.exit(-1);
        }
    }
        return null;
    }

    // Tikrinti testu rezultatus
    static List<Rezults> tikrintiTestuRezultatus(HashMap<String, ExamTestAnswers> atsakymai, Path keliasrez) {
        // paruosiam mapa kiekvieno egzamino rezultatams
        List<Rezults> rezultatai = new   ArrayList<>();

        for (Map.Entry<String, ExamTestAnswers> ent : atsakymai.entrySet()
        ) {

            rezultatai.add( new Rezults(ent.getValue().getExamID(),ent.getValue().getPavadinimas(),ent.getValue().getTipas()));

        }
        List<String> collect = Util.getFileList(keliasrez);

            for (String s : collect
            ) {
                Path iskur = keliasrez.resolve(s);
                StudensExamAnswers studAts = new StudensExamAnswers();

                studAts.getFromFile(iskur);

                if (studAts.getStudent().getId() == null) {

                    continue;
                } else {

                    if (atsakymai.containsKey(studAts.getExamID())) {
                        int rez = getRezult(studAts, atsakymai.get(studAts.getExamID()));
                        if (rez != -1) {
                            StudentResult studRez = new StudentResult(studAts.getStudent().getId(), studAts.getStudent().getVardas()
                                    , studAts.getStudent().getPavarde(), rez);
//
                 for (Rezults rezz: rezultatai){
                     if (rezz.getExamID().equals(studAts.getExamID()))
                         rezz.setEgzaminoRezultatai(studRez);

                 }


                        }
                    } else {
                        LOG.warn("Nera teisingu atsakymo failo egzaminui {} {} arba klaidingas egzamino kodas",
                                studAts.getExamID(), studAts.getPavadinimas());
                    }

                }
            }
            return rezultatai;
        }

    static List<Rezults> checkWithExists (Path kelias, List<Rezults> rezultatai){

        List<String> atsFailai= new ArrayList<>();
        if (Files.exists(kelias)) {
           if (Files.isDirectory(kelias)) {
               atsFailai = Util.getFileList(kelias);
           } else {
               LOG.error(" klaida : neteisingai nurodyta direktorija egzamino rezultatu failams", kelias.getFileName());
               System.exit(-1);
           }
           try {
               Files.createDirectory(kelias);
           } catch (IOException e) {
               LOG.error(" klaida : nepavyko sukurti direktorija egzamino rezultatu failams", kelias.getFileName());
               System.exit(-1);
           }

       }
//        if (!atsFailai.isEmpty()){
//            ObjectMapper om = new ObjectMapper();
//            for (String fail: atsFailai
//                 ) {
//                Path kurs =kelias.resolve(fail);
//                try {
//                    Rezults rezults= om.readValue(kurs.toFile(), Rezults.class);
//
//                } catch (IOException e) {
//                    LOG.warn("nepavyko nuskaityti failo {}",kurs);
//                }
//            }
//        }
        return rezultatai;
    }

}
