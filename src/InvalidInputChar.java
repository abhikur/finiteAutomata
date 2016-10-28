public class InvalidInputChar extends Throwable {
    private String inputChar;

    public InvalidInputChar(String inputChar) {
        this.inputChar = inputChar;
    }
}
