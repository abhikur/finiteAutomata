import junit.framework.Assert;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

public class ParserTest {

    JSONArray expectedJsonArray = new JSONArray();

    @Before
    public void setup() {
        String data = "[{\"tuple\":" +
                            "{\"alphabets\":[\"1\",\"0\"]," +
                            "\"delta\":{\"q2\":{\"1\":\"q2\",\"0\":\"q1\"},\"q1\":{\"1\":\"q1\",\"0\":\"q2\"}}," +
                            "\"states\":[\"q1\",\"q2\"]," +
                            "\"final-states\":[\"q2\"]," +
                            "\"start-state\":\"q1\"}," +
                        "\"pass-cases\":[\"0\",\"000\",\"00000\",\"10\",\"101010\",\"010101\"]," +
                        "\"name\":\"odd number of zeroes\"," +
                        "\"type\":\"dfa\"," +
                        "\"fail-cases\":[\"00\",\"0000\",\"1001\",\"1010\",\"001100\"]}]";

        JSONArray jsonArray = null;
        try {
            jsonArray = (JSONArray) new JSONParser().parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject tuple = (JSONObject) ((JSONObject) jsonArray.get(0)).get("tuple");
        FiniteMachine dfa = new DFA("odd number of zeroes", tuple);
        JSONArray passedCases = (JSONArray) ((JSONObject) jsonArray.get(0)).get("pass-cases");
        JSONArray failCases = (JSONArray) ((JSONObject) jsonArray.get(0)).get("fail-cases");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("failCases", failCases);
        jsonObject.put("machine", dfa);
        jsonObject.put("passCases", passedCases);
        expectedJsonArray.add(jsonObject);
    }

    @Test
    public void parseShouldParseContentOfFileIntoJSONArray() {
        Parser parser = new Parser();
        JSONArray jsonArray = parser.parse("testFile.json");
        Assert.assertEquals(jsonArray, expectedJsonArray);
    }

}