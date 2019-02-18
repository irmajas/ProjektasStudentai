package studentfiles;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;

@SuppressWarnings("ALL")
public class StudensExamAnswers extends ExamTestAnswers implements GetFromFiles {
    private static final org.apache.logging.log4j.core.Logger LOG = (Logger) LogManager.getLogger(StudensExamAnswers.class);

    private Student student = new Student();
    private String egzam_data;
    private String egzam_trukme;

    public Student getStudent() {
        return student;
    }

    public String getEgzam_data() {
        return egzam_data;
    }

    public String getEgzam_trukme() {
        return egzam_trukme;
    }

    public void setEgzam_data(String egzam_data) {
        this.egzam_data = egzam_data;
    }

    public void setEgzam_trukme(String egzam_trukme) {
        this.egzam_trukme = egzam_trukme;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "StudensExamAnswers{" +
                "student=" + student +
                ", ats=" + Arrays.toString(ats) +
                ", examID='" + getExamID() + '\'' +
                ", pavadinimas='" + getPavadinimas() + '\'' +
                ", tipas='" + getTipas() + '\'' +
                '}';
    }

    @Override
    public void getFromFile(Path kelias) {
        JSONParser parser = new JSONParser();

        ObjectMapper om = new ObjectMapper();


        try {
            Object obj = parser.parse(new FileReader(String.valueOf(kelias)));
            JSONObject jsonOb = (JSONObject) obj;

   //bandom pridet data


            String dataStart = (String) jsonOb.get("testo_pradzia");
            String dataEnd = (String) jsonOb.get("testo_pabaiga");
            LocalTime trukme;
            LocalDate examDate;
            try {
                Time timeEnd = Time.valueOf(LocalTime.parse(dataEnd.substring(11)));
                Time timeStart = Time.valueOf(LocalTime.parse(dataStart.substring(11)));
                long duration = Duration.between( timeStart.toLocalTime(),timeEnd.toLocalTime()).toMinutes();
                examDate = LocalDate.parse(dataStart.substring(0,10));
                trukme=LocalTime.of((int)duration/60,(int)duration%60);

            } catch (Exception e){
                LOG.warn("klaida gaunant egzamino laika");
                trukme=LocalTime.of(0,0);
                examDate = LocalDate.parse("1900-01-01");

            }


            JSONObject ob1 = (JSONObject) jsonOb.get("studentas");
            String id = (String) ob1.get("id");
            String vardas = (String) ob1.get("vardas");
            String pavarde = (String) ob1.get("pavarde");

            JSONObject ob2 = (JSONObject) jsonOb.get("egzaminas");

            JSONArray obats = (JSONArray) jsonOb.get("atsakymai");
            String[] atsakym = new String[obats.size()];
            for (int i = 0; i < obats.size(); i++) {
                JSONObject ats = (JSONObject) obats.get(i);
                int kl = (int) Integer.parseInt(String.valueOf((long) ats.get("klausimas")));
                if (kl != i + 1) {

                    LOG.warn("Klaida faile {}", kelias.getFileName());

                    return;
                }
                atsakym[i] = (String) ats.get("atsakymas");

            }
            this.student = new Student(id, vardas, pavarde);
            this.setExamID((String) ob2.get("id"));
            this.setPavadinimas((String) ob2.get("pavadinimas"));
            this.setTipas((String) ob2.get("tipas"));
            this.setAts(atsakym);
            this.setEgzam_data(examDate.toString());
            this.setEgzam_trukme(trukme.toString());
        }
        // catch (JsonMappingException e) {e.printStackTrace();}
        catch (JsonParseException e) {
            LOG.error(" Klaida faile {}.Nnepavyko ikelti agzamino atsakymu", kelias.getFileName());
            /* e.printStackTrace();*/
        } catch (ParseException e) {
            LOG.error(" Klaida faile {}. Nepavyko ikelti agzamino atsakymu", kelias.getFileName());
            /* e.printStackTrace();*/
        } catch (IOException e) {
            LOG.error(" Klaida  nuskaitant failą {}. Nepavyko nuskaityti agzamino atsakymu failo", kelias.getFileName());
            /* e.printStackTrace();*/
        }
    }


}

