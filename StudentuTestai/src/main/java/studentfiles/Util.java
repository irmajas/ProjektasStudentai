package studentfiles;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Util {

    private static final Logger LOG = (Logger) LogManager.getLogger(Util.class);

    // Tikrinti testu rezultatus
    static List<Rezults> tikrintiTestuRezultatus(HashMap<String, ExamTestAnswers> atsakymai, Path keliasrez) {
        // paruosiam mapa kiekvieno egzamino rezultatams
        List<Rezults> rezultatai = new ArrayList<>();

        for (Map.Entry<String, ExamTestAnswers> ent : atsakymai.entrySet()) {
            rezultatai.add(new Rezults(ent.getValue().getExamID(), ent.getValue().getPavadinimas(), ent.getValue().getTipas()));
        }
//gaunam sarasa failu su studentu testo rezultatais
        List<String> collect = UtilFiles.getFileList(keliasrez);
//skaiciuojam kiekvieno testo rezultatus
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
                    //jei pavyko paskaiciuoti rezultata
                    if (rez != -1) {
                        StudentResult studRez = new StudentResult(studAts.getStudent().getId(), studAts.getStudent().getVardas()
                                , studAts.getStudent().getPavarde(), rez);
                        studRez.setEgzam_data(studAts.getEgzam_data());
                        studRez.setEgzam_trukme(studAts.getEgzam_trukme());
//
                        for (Rezults rezz : rezultatai) {
                            if (rezz.getExamID().equals(studAts.getExamID()))
                                rezz.addEgzaminoRezultatai(studRez);

                        }
                    }
                } else { //jei nepavyko paskaiciuoti rezultato
                    LOG.warn("Klaida 01 Nera teisingu atsakymo failo egzaminui {} {} arba klaidingas egzamino kodas",
                            studAts.getExamID(), studAts.getPavadinimas());
                }
            }
        }
        return rezultatai;
    }
//Luginam naujus egzamino rezultatus su esamais
    static List<Rezults> checkWithExists(Path kelias, List<Rezults> rezultatai) {

        List<String> atsFailai = new ArrayList<>();
        if (Files.exists(kelias)) {
            if (Files.isDirectory(kelias)) {
                atsFailai = UtilFiles.getFileList(kelias);
            } else {
                LOG.error("Klaida 02: neteisingai nurodyta direktorija egzamino rezultatu failams", kelias.getFileName());
                System.exit(-1);
            }


        }
        System.out.println(atsFailai);
        for (Rezults resul : rezultatai
        ) {
            String filename = "Exam" + resul.getExamID()+ ".json";

            Path kurs = kelias.resolve(filename);

            if (atsFailai.contains(kurs.getFileName().toString())) {

                ObjectMapper om = new ObjectMapper();
                try {
                    System.out.println(kurs.toFile());
                   Rezults rezultsold =om.readValue( kurs.toFile(),Rezults.class);

                    resul = Util.addingNewResults(rezultsold, resul);
                } catch (IOException e) {
                    LOG.warn(" Klaida 03: nepavyko nuskaityti failo {}", kurs);
                    System.out.println(e);
                }

            }
        }

        return rezultatai;
    }

    // lyginam du resultatu List'us
    static Rezults addingNewResults(Rezults rezold, Rezults reznew) {
        List<StudentResult> studentOld = rezold.getEgzaminoRezultatai();
        List<StudentResult> studentNew = reznew.getEgzaminoRezultatai();
        List<StudentResult> original = studentOld.stream()
                .filter(studentResult -> !studentNew.containsAll(studentOld))
                .collect(Collectors.toList());
        //test print
        System.out.println("ORIGINAL?");
        System.out.println(original);

        reznew.setEgzaminoRezultatai(Stream.concat(studentNew.stream(), original.stream())
                .collect(Collectors.toList()));
        return reznew;
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
        for (int i = 0; i < kiekKlausimu; i++) {

            if (studentans.getAts()[i].equals(trueAnswers.getAts()[i])) {
                result++;
            }
        }

        LOG.info(" Studentas {} {} laike agzamina {} {}. Rezultatas {}",
                studentans.getStudent().getVardas(), studentans.getStudent().getPavarde()
                , studentans.getExamID(), studentans.getPavadinimas(), result);
        return result;
    }
}

