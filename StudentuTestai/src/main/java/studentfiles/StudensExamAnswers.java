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

@SuppressWarnings("ALL")
public class StudensExamAnswers extends ExamTestAnswers implements GetFromFiles {
    private Student student = new Student();

    public Student getStudent() {
        return student;
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
        JSONParser parser= new JSONParser();

        ObjectMapper om = new ObjectMapper();
        try{
            Object obj = parser.parse(new FileReader(String.valueOf(kelias)));
            JSONObject jsonOb = (JSONObject)obj;
            JSONObject ob1=(JSONObject) jsonOb.get("studentas") ;
            String id= (String)ob1.get("id");
            String vardas= (String)ob1.get("vardas");
            String pavarde= (String)ob1.get("pavarde");
            this.student = new Student(id, vardas, pavarde);
            JSONObject ob2 =(JSONObject)jsonOb.get("egzaminas");
            this.setExamID((String) ob2.get("id"));
            this.setPavadinimas((String) ob2.get("pavadinimas"));
            this.setTipas((String) ob2.get("tipas"));
            JSONArray obats =(JSONArray)jsonOb.get("atsakymai");
            String[] atsakym = new String[obats.size()];
            for (int i =0; i<obats.size();i++) {
                JSONObject ats = (JSONObject) obats.get(i);
                int kl= (int) Integer.parseInt(String.valueOf((long) ats.get("klausimas")));
                atsakym[i]=(String) ats.get("atsakymas");
            }
            this.setAts(atsakym);
        }
        catch (JsonParseException e) {e.printStackTrace();}
        catch (JsonMappingException e) {e.printStackTrace();}
        catch (IOException e){e.printStackTrace();}
        catch (ParseException e) {
            e.printStackTrace();
        }


    }
}
