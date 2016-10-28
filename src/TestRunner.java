import org.json.simple.JSONArray;

public class TestRunner {

    public Boolean testMachine(FiniteMachine machine, JSONArray cases) throws InvalidInputChar {
        Boolean result = true;
        for (Object aCase : cases) {
            result = machine.validateThroughDelta((String) aCase);
        }
        return result;
    }
}
