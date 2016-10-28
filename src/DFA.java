import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DFA implements FiniteMachine {
    private final JSONArray states;
    private final JSONArray alphabets;
    private final JSONObject delta;
    private final String startState;
    private final JSONArray finalStates;
    private String name;

    public DFA(String name, JSONObject tuple) {
        this.name = name;
        this.states = (JSONArray) tuple.get("states");
        this.alphabets = (JSONArray) tuple.get("alphabets");
        this.delta = (JSONObject) tuple.get("delta");
        this.startState = (String) tuple.get("start-state");
        this.finalStates = (JSONArray) tuple.get("final-states");
    }

    public Boolean validateThroughDelta(String inputStr) throws InvalidInputChar {
        State currentState = new State(startState);
        String[] testChars = inputStr.split("");
        for (String testChar : testChars) {
            if(!testChar.equals("")) {
                currentState = getNextState(currentState.toString(), testChar);
            }
        }
        return currentState.isFinal(finalStates);
    }

    private State getNextState(String currentState, String inputChar) throws InvalidInputChar {
        if(!alphabets.contains(inputChar))
            throw new InvalidInputChar(inputChar);
        List<State> allStates = getAllStates();
        JSONObject alphaStatePair = (JSONObject) delta.get(currentState);
        String newStates = (String) alphaStatePair.get(inputChar);
        return getState(allStates, newStates);
    }

    private State getState(List<State> allStates, String stateToGet) {
        State resultantState = null;
        for (State state : allStates) {
            if(state.equals(new State(stateToGet))) {
                resultantState = state;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DFA tuple = (DFA) o;

        if (!states.equals(tuple.states)) return false;
        if (!alphabets.equals(tuple.alphabets)) return false;
        if (!delta.equals(tuple.delta)) return false;
        if (startState != null ? !startState.equals(tuple.startState) : tuple.startState != null) return false;
        return finalStates.equals(tuple.finalStates);

    }

    @Override
    public String toString() {
        return name;
    }
}
