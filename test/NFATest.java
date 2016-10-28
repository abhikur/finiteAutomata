import junit.framework.Assert;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

public class NFATest {

    String input;
    NFA nfa;

    @Before
    public void setup() {
        input = "{\"tuple\":" +
                    "{\"states\":[\"q1\",\"q3\",\"q2\",\"q5\",\"q4\"]," +
                    "\"alphabets\":[\"1\",\"0\"]," +
                    "\"delta\":{\"q1\":{\"e\":[\"q2\",\"q4\"]}," +
                                "\"q2\":{\"0\":[\"q3\"],\"1\":[\"q2\"]}," +
                                "\"q3\":{\"0\":[\"q2\"],\"1\":[\"q3\"]}," +
                                "\"q4\":{\"1\":[\"q5\"],\"0\":[\"q4\"]}," +
                                "\"q5\":{\"1\":[\"q4\"],\"0\":[\"q5\"]}}," +
                    "\"start-state\":\"q1\"," +
                    "\"final-states\":[\"q2\",\"q4\"]}}";


        JSONObject obj = null;
        try {
            obj = (JSONObject) new JSONParser().parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        nfa = new NFA("name", (JSONObject) obj.get("tuple"));
    }

    @Test
    public void validateThroughDelta() {
        Boolean endWithZero = nfa.validateThroughDelta("00");
        Boolean endWithOne = nfa.validateThroughDelta("0001");
        Assert.assertTrue(endWithZero);
        Assert.assertFalse(endWithOne);
    }
}