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


public class ExamTestAnswers extends Exam implements GetFromFiles {
    private static final Logger LOG = (Logger) LogManager.getLogger(ExamTestAnswers.class);
    private String[] ats;

    public ExamTestAnswers() {
        super();
    }

    public ExamTestAnswers(String examID, String pavadinimas, TestType tipas, String[] ats) {
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
    // to read true answers file for one rxam
    public void getFromFile(Path kelias) {
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(String.valueOf(kelias)));
            JSONObject jsonOb = (JSONObject) obj;
            JSONObject ob = (JSONObject) jsonOb.get("egzaminas");
            JSONArray obats = (JSONArray) jsonOb.get("atsakymai");
            this.ats = new String[obats.size()];
            for (int i = 0; i < obats.size(); i++) {
                JSONObject ats = (JSONObject) obats.get(i);
                int kl = Integer.parseInt(String.valueOf((long) ats.get("klausimas")));
                try {
                    this.ats[kl - 1] = (String) ats.get("atsakymas");
                } catch (ArrayIndexOutOfBoundsException r) {
                    LOG.warn(" Klaida 04 faile {}. Pateiktas klaidingas atsakymo numeris", kelias.getFileName());
                    return;
                }
            }
            this.setExamID((String) ob.get("id"));
            this.setPavadinimas((String) ob.get("pavadinimas"));
            this.setTipas(Util.findEnum((String) ob.get("tipas")));
            if (this.getPavadinimas() == null || this.getTipas() == null) {
                this.setExamID(null);
            }
            if (this.getExamID() == null) {
                LOG.warn("Klaida 05 faile {}. Nepavyko įkelti teisingu atsakymų", kelias.getFileName());
            }

        }
        // catch (JsonMappingException e) {e.printStackTrace();}
        catch (JsonParseException e) {
            LOG.warn(" Klaida 01 faile {}. Nepavyko įkelti agzamino atsakymų", kelias.getFileName());

        } catch (ParseException e) {
            LOG.warn(" Klaida 02 faile {}. Nepavyko įkelti agzamino atsakymų", kelias.getFileName());

        } catch (IOException e) {
            LOG.warn(" Klaida 03 nuskaitant failą {}. Nepavyko įkelti agzamino atsakymų", kelias.getFileName());

        }
    }
}
