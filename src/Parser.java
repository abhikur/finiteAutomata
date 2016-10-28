import com.sun.javafx.tools.resource.DeployResource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Parser {


    private String readFile(String fileName) {
        BufferedReader br = null;
        String data = "";
        try {
            br = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            String line;
            while( (line  = br.readLine()) != null) {
                data = data.concat(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public JSONArray getJSONArrayFrom(String fileName) {
        String data = readFile(fileName).replaceFirst("\"", "").replaceAll("\\\\", "");
        JSONArray jsonArray = null;
        try {
            jsonArray = (JSONArray) new JSONParser().parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public JSONArray parse(String fileName) {
        JSONArray machinesWithCases = new JSONArray();
        JSONArray jsonArray = getJSONArrayFrom(fileName);
        for (Object finiteAutomata : jsonArray) {
            JSONObject jsonObject = new JSONObject();
            JSONArray passCases = (JSONArray) ((JSONObject) finiteAutomata).get("pass-cases");
            JSONArray failCases = (JSONArray) ((JSONObject) finiteAutomata).get("fail-cases");
            jsonObject.put("passCases", passCases);
            jsonObject.put("failCases", failCases);
            String name = (String) ((JSONObject) finiteAutomata).get("name");
            JSONObject tupleJSON = (JSONObject) ((JSONObject) finiteAutomata).get("tuple");
            String type = (String) ((JSONObject) finiteAutomata).get("type");
            if( type.equals("dfa")) {
                jsonObject.put("machine", new DFA(name, tupleJSON));
            }
            else if(type.equals("nfa")){
                jsonObject.put("machine", new NFA(name, tupleJSON));
            }
            machinesWithCases.add(jsonObject);
        }
        return machinesWithCases;
    }


}
