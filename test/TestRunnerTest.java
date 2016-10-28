import junit.framework.Assert;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class TestRunnerTest {

    private JSONArray machinesWithCases = new JSONArray();

    @Before
    public void setup() {
        machinesWithCases = new Parser().parse("dfa&nfa.json");
    }

    @Test
    public void testPassCasesThroughMachines() throws InvalidInputChar {
        TestRunner testRunner = new TestRunner();
        for (Object machineWithCases : machinesWithCases) {
            JSONArray passCases = (JSONArray) ((JSONObject) machineWithCases).get("passCases");
            FiniteMachine machine = (FiniteMachine) ((JSONObject) machineWithCases).get("machine");
            Assert.assertTrue(testRunner.testMachine(machine, passCases));
        }
    }

    @Test
    public void testFailCasesThroughMachines() throws InvalidInputChar {
        TestRunner testRunner = new TestRunner();
        for (Object machineWithCases : machinesWithCases) {
            JSONArray failCases = (JSONArray) ((JSONObject) machineWithCases).get("failCases");
            FiniteMachine machine = (FiniteMachine) ((JSONObject) machineWithCases).get("machine");
            Assert.assertFalse(testRunner.testMachine(machine, failCases));
        }
    }
}