package students;

import com.fasterxml.jackson.core.JsonParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import exams.ExamTestAnswers;
import utilits.GetFromFiles;
import utilits.Util;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;


public class StudensExamAnswers extends ExamTestAnswers implements GetFromFiles {
    private static final org.apache.logging.log4j.core.Logger LOG = (Logger) LogManager.getLogger(StudensExamAnswers.class);

    private Student student = new Student();
    private String egzamData;
    private String egzamTrukme;

    public Student getStudent() {
        return student;
    }

    public String getEgzamData() {
        return egzamData;
    }

    public String getEgzamTrukme() {
        return egzamTrukme;
    }

    public void setEgzamData(String egzamData) {
        this.egzamData = egzamData;
    }

    public void setEgzamTrukme(String egzamTrukme) {
        this.egzamTrukme = egzamTrukme;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
//getting test aswers one Student
    public void getFromFile(Path kelias) {
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(String.valueOf(kelias)));
            JSONObject jsonOb = (JSONObject) obj;
            String dataStart = (String) jsonOb.get("testo_pradzia");
            String dataEnd = (String) jsonOb.get("testo_pabaiga");
            LocalTime trukme;
            LocalDate examDate;
// skaiciuojam, kiek laiko studentas laike agzamina
            try {
                LocalTime timeEnd = LocalTime.parse(dataEnd.substring(11));
                LocalTime timeStart = LocalTime.parse(dataStart.substring(11));
                LocalTime zeroTime = LocalTime.of(0, 0);
                LocalTime endTime = LocalTime.of(23, 59, 59);
                long duration;
                if (timeStart.isBefore(timeEnd)) {
                    duration = Duration.between(timeStart, timeEnd).toMinutes();
                } else {
                    duration = Duration.between(timeStart, endTime).toMinutes() + 1 +
                            Duration.between(zeroTime, timeEnd).toMinutes();
                }
                examDate = LocalDate.parse(dataStart.substring(0, 10));
                trukme = LocalTime.of((int) duration / 60, (int) duration % 60);

            } catch (Exception e) {
                LOG.warn("Klaida 01 gaunant egzamino laika :{}", kelias.getFileName());
                trukme = LocalTime.of(0, 0);
                examDate = LocalDate.parse("1900-01-01");
            }
//nuskaitom studento duomenis

            JSONObject ob1 = (JSONObject) jsonOb.get("studentas");
            String id = (String) ob1.get("id");
            String vardas = (String) ob1.get("vardas");
            String pavarde = (String) ob1.get("pavarde");
            JSONObject ob2 = (JSONObject) jsonOb.get("egzaminas");

//Nuskaitom studento atsakymus
            JSONArray obats = (JSONArray) jsonOb.get("atsakymai");
            String[] atsakym = new String[obats.size()];
            for (int i = 0; i < obats.size(); i++) {
                JSONObject ats = (JSONObject) obats.get(i);
                int kl = Integer.parseInt(String.valueOf((long) ats.get("klausimas")));
                if (kl != i + 1) {
                    LOG.warn("Klaida 02 faile {}. Neteisingas atsakymo numeris", kelias.getFileName());
                    return;
                }
                atsakym[i] = (String) ats.get("atsakymas");
            }

            this.student = new Student(id, vardas, pavarde);
            if (this.student.getPavarde() == null || this.student.getVardas() == null) {
                this.student.setId(null);
            }
            this.setExamID((String) ob2.get("id"));
            this.setPavadinimas((String) ob2.get("pavadinimas"));
            this.setTipas(Util.findEnum((String) ob2.get("tipas")));
            if (this.getExamID() == null || this.getPavadinimas() == null || this.getTipas() == null) {
                this.student.setId(null);
            }
            if (this.student.getId() == null) {
                LOG.warn("Klaida 06 faile {}. Nepavyko įkelti studento testo atsakymų", kelias.getFileName());
            }
            this.setAts(atsakym);
            this.setEgzamData(examDate.toString());
            this.setEgzamTrukme(trukme.toString());

        } catch (JsonParseException e) {
            LOG.error(" Klaida 03 faile {}. Nnepavyko ikelti agzamino atsakymu", kelias.getFileName());
        } catch (ParseException e) {
            LOG.error(" Klaida 04 faile {}. Nepavyko ikelti agzamino atsakymu", kelias.getFileName());
        } catch (IOException e) {
            LOG.error(" Klaida 05 nuskaitant failą {}. Nepavyko nuskaityti agzamino atsakymu failo", kelias.getFileName());
        }
    }
}

