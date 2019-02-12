package studentfiles;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

public class ExamTestAnswers extends Exam implements GetFromFiles {
    protected String[] ats ;

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
    public void getFromFile(Path kelias){
        JSONParser parser= new JSONParser();

        ObjectMapper om = new ObjectMapper();
        try{
            Object obj = parser.parse (new FileReader(String.valueOf(kelias)));
            JSONObject jsonOb = (JSONObject)obj;
            JSONObject ob =(JSONObject)jsonOb.get("egzaminas");
            this.setExamID((String) ob.get("id"));
            this.setPavadinimas((String) ob.get("pavadinimas"));
            this.setTipas((String) ob.get("tipas"));

            JSONArray obats =(JSONArray)jsonOb.get("atsakymai");
            this.ats = new String[obats.size()];
            for (int i =0; i<obats.size();i++) {
                JSONObject ats = (JSONObject) obats.get(i);
                int kl= (int) Integer.parseInt(String.valueOf((long) ats.get("klausimas")));
                this.ats[i]=(String) ats.get("atsakymas");
            }
        }
        catch (JsonParseException e) {e.printStackTrace();}
        catch (JsonMappingException e) {e.printStackTrace();}
        catch (IOException e){e.printStackTrace();}
        catch (ParseException e) {
            e.printStackTrace();
        }



    }
}
