package bot.Game;

public class Player {

    private int count;
    private String expectedAnswer;
    private String currentAnswer;


    public void setExpectedAnswer(String expectedAnswer){
        this.expectedAnswer = expectedAnswer;
    }
    public void setCurrentAnswer(String currentAnswer) {this.currentAnswer = currentAnswer;}
    public String getCurrentAnswer() { return currentAnswer; }
    public void upCount(){count++;}

    @Override
    public String toString() {
        return "Scores: " + count + "\n" + expectedAnswer + "\n";
    }
}