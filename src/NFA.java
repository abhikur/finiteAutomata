import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NFA implements FiniteMachine {

    private final JSONArray states;
    private final JSONArray alphabets;
    private final JSONObject delta;
    private final String startState;
    private final JSONArray finalStates;
    private String name;

    public NFA(String name, JSONObject tuple) {
        this.name = name;
        this.states = (JSONArray) tuple.get("states");
        this.alphabets = (JSONArray) tuple.get("alphabets");
        this.delta = (JSONObject) tuple.get("delta");
        this.startState = (String) tuple.get("start-state");
        this.finalStates = (JSONArray) tuple.get("final-states");
    }

    public Boolean validateThroughDelta(String inputStr) {
        Boolean isValid = false;
        String[] testChars = inputStr.split("");
        Set<State> stateSequence = new HashSet<>();
        stateSequence.add(new State(startState));
        for (String testChar : testChars) {                // Todo: need to check if testAlpha present in alphabet set
            if(!testChar.equals("")) {
                Set<State> newStateSequence = new HashSet<>();
                for (State state : stateSequence) {
                    newStateSequence.addAll(getNextStates(state.toString(),testChar));
                }
                stateSequence = newStateSequence;
            }
        }
        for (State state : stateSequence) {
            if(state.isFinal(finalStates))
                return true;
        }
        return isValid;
    }

    private JSONArray epsilonClosure(String state) {
        JSONArray ec = new JSONArray();
        ec.add(state);
        try {
            ec.addAll((JSONArray) ((JSONObject) delta.get(state)).get("e"));
        }catch (Exception e) {
            return ec;
        }
        return ec;
    }

    private List<State> getNextStates(String currentState, String inputChar) {
        List<String>nextStates = new ArrayList<>();
        JSONArray epsilonClosure = epsilonClosure(currentState);
        List<String> statesAfterPassingInput = statesAfterPassingInputChar(inputChar, epsilonClosure);
        for (String state : statesAfterPassingInput) {
            nextStates.addAll(epsilonClosure(state));
        }
        return getStates(getAllStates(), nextStates);
    }

    private List<String> statesAfterPassingInputChar(String inputChar, JSONArray epsilonClosure) {
        List<String>statesAfterPassingInput = new ArrayList<>();
        for (Object ec : epsilonClosure) {
            JSONArray states = null;
            try {
                states = (JSONArray) ((JSONObject) delta.get(ec)).get(inputChar);
            }catch(Exception e) {

            }
            if(states != null) {
                statesAfterPassingInput.addAll(states);
            }
        }
        return statesAfterPassingInput;
    }

    private List<State> getStates(List<State> allStates, List<String> statesToGet) {
        List<State> resultantState = new ArrayList<>();
        for (State state : allStates) {
            for (Object stateToGet : statesToGet) {
                if(state.equals(new State((String) stateToGet))) {
                    resultantState.add(state);
                }
            }

        }
        return resultantState;
    }

    private List<State> getAllStates() {
        List<State> allStates = new ArrayList<>();
        for (Object state : states) {
            allStates.add(new State((String) state));
        }
        return allStates;
    }

    @Override
    public String toString() {
        return name;
    }
}
