package utilits;

import com.fasterxml.jackson.databind.ObjectMapper;
import exams.ExamTestAnswers;
import studentfiles.*;
import students.StudensExamAnswers;
import students.StudentResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Util {

    // Tikrinti testu rezultatus
    public static List<Rezults> tikrintiTestuRezultatus(HashMap<String, ExamTestAnswers> atsakymai, Path keliasrez) {
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
                    int rez = 0;
                    if (studAts.getTipas().equals(TestType.TEST)) {
                        rez = CountingExamRezult.getRezult(studAts, atsakymai.get(studAts.getExamID()));
                    }
                    if (studAts.getTipas().equals(TestType.TEST_WITH_FEW_ANSWERS)) {
                        rez = CountingExamRezult.getRezultWithFewAnswers(studAts, atsakymai.get(studAts.getExamID()));
                    }
                    if (studAts.getTipas().equals(TestType.TEST_WRITE_ANSWERS)) {
                        rez = CountingExamRezult.getRezultWithAnswers(studAts, atsakymai.get(studAts.getExamID()));
                    }

                    //jei pavyko paskaiciuoti rezultata
                    if (rez != -1) {
                        StudentResult studRez = new StudentResult(studAts.getStudent().getId(), studAts.getStudent().getVardas()
                                , studAts.getStudent().getPavarde(), rez);
                        studRez.setEgzam_data(studAts.getEgzamData());
                        studRez.setEgzam_trukme(studAts.getEgzamTrukme());
                        for (Rezults rezz : rezultatai) {
                            if (rezz.getExamID().equals(studAts.getExamID()))
                                rezz.addEgzaminoRezultatai(studRez);

                        }
                    }
                } else { //jei nepavyko paskaiciuoti rezultato
                    CountingExamRezult.LOG.warn("Klaida 01 Nera teisingu atsakymo failo egzaminui {} {} arba klaidingas egzamino kodas",
                            studAts.getExamID(), studAts.getPavadinimas());
                }
            }
        }
        return rezultatai;
    }

    //Lyginam naujus egzamino rezultatus su esamais
    public static List<Rezults> checkWithExists(Path kelias, List<Rezults> rezultatai) {

        List<String> atsFailai = new ArrayList<>();
        if (Files.exists(kelias)) {
            if (Files.isDirectory(kelias)) {
                atsFailai = UtilFiles.getFileList(kelias);
            } else {
                CountingExamRezult.LOG.error("Klaida 02: neteisingai nurodyta direktorija egzamino rezultatu failams", kelias.getFileName());
                System.exit(-1);
            }
        }

        for (Rezults resul : rezultatai) {
            String filename = "Exam" + resul.getExamID() + ".json";

            Path kurs = kelias.resolve(filename);

            if (atsFailai.contains(kurs.getFileName().toString())) {

                ObjectMapper om = new ObjectMapper();
                try {

                    Rezults rezultsold = om.readValue(kurs.toFile(), Rezults.class);

                    resul = Util.addingNewResults(rezultsold, resul);
                } catch (IOException e) {
                    CountingExamRezult.LOG.warn(" Klaida 03: nepavyko nuskaityti failo {}", kurs);
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
        reznew.setEgzaminoRezultatai(Stream.concat(studentNew.stream(), original.stream())
                .distinct()
                .collect(Collectors.toList()));
        return reznew;
    }

    // gauti enum reiksme
    public static TestType findEnum(String tipas) {
        for (TestType v : TestType.values()) {
            if (v.getTestas().equals(tipas)) {
                return v;
            }
        }
        return null;
    }
}

