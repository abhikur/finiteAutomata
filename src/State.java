import org.json.simple.JSONArray;

public class State {
    private String state;

    public State(String state) {
        this.state = state;
    }

    public Boolean isFinal(JSONArray finalStates) {
        Boolean result = false;
        for (Object finalState : finalStates) {
            if(this.equals(new State((String) finalState))) {
                return true;
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state1 = (State) o;

        return state.equals(state1.state);

    }

    @Override
    public int hashCode() {
        return state.hashCode();
    }

    @Override
    public String toString() {
        return state;
    }
}
