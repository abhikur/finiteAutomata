import junit.framework.Assert;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

public class DFATest {

    JSONObject inputJson;
    FiniteMachine dfa;

    @Before
    public void setup() {
        String input = "{\"tuple\":{" +
                "\"states\":[\"q1\",\"q2\"]," +
                "\"alphabets\":[\"1\",\"0\"]," +
                "\"delta\":{\"q1\":{\"0\":\"q2\",\"1\":\"q1\"},\"q2\":{\"0\":\"q1\",\"1\":\"q2\"}}" +
                "\"start-state\":\"q1\"," +
                "\"final-states\":[\"q2\"]}," +
                "\"pass-cases\":[\"0\",\"000\",\"00000\",\"10\",\"101010\",\"010101\"]," +
                "\"fail-cases\":[\"00\",\"0000\",\"1001\",\"1010\",\"001100\"]}";
        try {
            inputJson = (JSONObject) new JSONParser().parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dfa = new DFA("dfa", (JSONObject) inputJson.get("tuple"));
    }

    @Test
    public void validateThroughDeltaShouldValidatePassCasesToBeRegularLanguage() throws InvalidInputChar {
        JSONArray passCases = (JSONArray) inputJson.get("pass-cases");
        Boolean result = false;
        for (Object passCase : passCases) {
            result = dfa.validateThroughDelta((String) passCase);
        }
        Assert.assertTrue(result);
    }

    @Test
    public void validateThroughDeltaShouldNotValidateFailCasesToBeRegularLanguage() throws InvalidInputChar {
        JSONArray passCases = (JSONArray) inputJson.get("fail-cases");
        Boolean result = false;
        for (Object passCase : passCases) {
            result = dfa.validateThroughDelta((String) passCase);
        }
        Assert.assertFalse(result);
    }

    @Test(expected = InvalidInputChar.class)
    public void shouldThrowExceptionWhenCharacterPassedIsNotRecognised() throws InvalidInputChar {
       dfa.validateThroughDelta("022");
    }
}