package studentfiles;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;


@SuppressWarnings("ALL")
public class ExamTestAnswers extends Exam implements GetFromFiles {
    private static final Logger LOG = (Logger) LogManager.getLogger(ExamTestAnswers.class);
    protected String[] ats;

    public ExamTestAnswers() {
        super();
    }

    public ExamTestAnswers(String examID, String pavadinimas, String tipas, String[] ats) {
        super(examID, pavadinimas, tipas);
        this.ats = ats;
    }

    public String[] getAts() {
        return ats;
    }

    public void setAts(String[] ats) {
        this.ats = ats;
    }

    @Override
    public String toString() {
        return "ExamTestAnswers{" +
                " id='" + getExamID() + '\'' +
                ", pavadinimas='" + getPavadinimas() + '\'' +
                ", tipas='" + getTipas() + '\'' + ", ats=" + Arrays.toString(ats) +
                '}';
    }

    @Override
    public void getFromFile(Path kelias) {
        JSONParser parser = new JSONParser();

        ObjectMapper om = new ObjectMapper();
        try {
            Object obj = parser.parse(new FileReader(String.valueOf(kelias)));
            JSONObject jsonOb = (JSONObject) obj;
            JSONObject ob = (JSONObject) jsonOb.get("egzaminas");
            this.setExamID((String) ob.get("id"));
            this.setPavadinimas((String) ob.get("pavadinimas"));
            this.setTipas((String) ob.get("tipas"));

            JSONArray obats = (JSONArray) jsonOb.get("atsakymai");
            this.ats = new String[obats.size()];
            for (int i = 0; i < obats.size(); i++) {
                JSONObject ats = (JSONObject) obats.get(i);
                int kl = (int) Integer.parseInt(String.valueOf((long) ats.get("klausimas")));
                this.ats[i] = (String) ats.get("atsakymas");
            }
        }


        // catch (JsonMappingException e) {e.printStackTrace();}
        catch (JsonParseException e) {
            LOG.error(" Klaida faile {}.Nnepavyko ikelti agzamino atsakymu", kelias.getFileName());
            /* e.printStackTrace();*/
        } catch (ParseException e) {
            LOG.error(" Klaida faile {}. Nepavyko ikelti agzamino atsakymu", kelias.getFileName());
            /* e.printStackTrace();*/
        } catch (IOException e) {
            LOG.error(" Klaida  nuskaitant failą {}. Nepavyko ikelti agzamino atsakymu", kelias.getFileName());
            /* e.printStackTrace();*/
        }
    }
}
