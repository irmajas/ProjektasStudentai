package utilits;

import exams.ExamTestAnswers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import students.StudensExamAnswers;

public class CountingExamRezult {

    static final Logger LOG = (Logger) LogManager.getLogger(CountingExamRezult.class);

    //Paskaiciuoti testo rezultata
    public static int getRezult(StudensExamAnswers studentans, ExamTestAnswers trueAnswers) {
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

        double procentai = (double) result / (double) kiekKlausimu * 100.0d;
        result = (int) procentai;
        LOG.info(" Studentas {} {} laike agzamina {} {}. Rezultatas {}",
                studentans.getStudent().getVardas(), studentans.getStudent().getPavarde()
                , studentans.getExamID(), studentans.getPavadinimas(), result);
        return result;
    }


    //Paskaiciuoti testo su irasomais atsakymais rezultata
    public static int getRezultWithAnswers(StudensExamAnswers studentans, ExamTestAnswers trueAnswers) {
        int result = 0;

        double visorez = 0.0d;
        double visoatsakymu = 0.0d;
        int kiekKlausimu = studentans.getAts().length;
        if (kiekKlausimu != trueAnswers.getAts().length) {
            LOG.warn("Studentas {} {} laikė agzaminą {} {}. Pateiktų atsakymų skaičua nesutampa su pateikiamų klausimų skaičiumi",
                    studentans.getStudent().getVardas(), studentans.getStudent().getPavarde(),
                    studentans.getExamID(), studentans.getPavadinimas());
            return -1;
        }
        for (int i = 0; i < kiekKlausimu; i++) {
            String[] pateikti = studentans.getAts()[i].split(",");
            String[] teisingi = trueAnswers.getAts()[i].split(",");
            visoatsakymu += teisingi.length;
            double skrezult = 0.0d;
            int kiek = teisingi.length < pateikti.length ? teisingi.length : pateikti.length;
            for (int a = 0; a < kiek; a++) {
                if (teisingi[a].strip().equals(pateikti[a].strip())) {
                    skrezult++;
                }
            }

            visorez += skrezult;
        }

        double procentai = visorez / visoatsakymu * 100.0d;
        result = (int) procentai;
        LOG.info(" Studentas {} {} laike agzamina {} {}. Rezultatas {}",
                studentans.getStudent().getVardas(), studentans.getStudent().getPavarde()
                , studentans.getExamID(), studentans.getPavadinimas(), result);
        return result;
    }

    //Paskaiciuoti testo su kelais teisingais atsakymais rezultata
    public static int getRezultWithFewAnswers(StudensExamAnswers studentans, ExamTestAnswers trueAnswers) {
        int result = 0;

        double visorez = 0.0d;
        int kiekKlausimu = studentans.getAts().length;
        if (kiekKlausimu != trueAnswers.getAts().length) {
            LOG.warn("Studentas {} {} laikė agzaminą {} {}. Pateiktų atsakymų skaičua nesutampa su pateikiamų klausimų skaičiumi",
                    studentans.getStudent().getVardas(), studentans.getStudent().getPavarde(),
                    studentans.getExamID(), studentans.getPavadinimas());
            return -1;
        }
        for (int i = 0; i < kiekKlausimu; i++) {
            String[] pateikti = studentans.getAts()[i].split("");
            double skrezult = 0.0d;
            double teisingoverte = 1.0d / trueAnswers.getAts()[i].length();
            for (String s : pateikti) {
                if (trueAnswers.getAts()[i].contains(s)) {
                    skrezult = skrezult + teisingoverte;
                } else skrezult = skrezult - teisingoverte / 2d;
            }
            if (skrezult < 0) {
                skrezult = 0;
            }
            visorez += skrezult;
        }

        double procentai = visorez / (double) kiekKlausimu * 100.0d;
        result = (int) procentai;
        LOG.info(" Studentas {} {} laike agzamina {} {}. Rezultatas {}",
                studentans.getStudent().getVardas(), studentans.getStudent().getPavarde()
                , studentans.getExamID(), studentans.getPavadinimas(), result);
        return result;
    }
}
