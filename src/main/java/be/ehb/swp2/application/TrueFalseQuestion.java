package be.ehb.swp2.application;

/**
 * Created by Ibrahim on 03-11-15.
 */
public class TrueFalseQuestion extends Question {

    private boolean answer;

    public TrueFalseQuestion(String name, String description, int time, boolean timeOn, boolean answer) {
        super();
        super(name, description, time, timeOn);
        this.answer = answer;
    }
  /*  public TrueFalseQuestion(String name, String description, int time, boolean timeOn)
    {
        super();
        super(name, description, time, timeOn);
        this.answer = false;
    }*/
    /*
    public void setAnswer(boolean answer)
    {
        this.answer = answer;
    }

    public void getAnswer ()
    {
        return this.answer;
    }
    */


    public boolean solve(boolean userAnswer)
    {

      /*  if (userAnswer == AnswerDAO)
      {
        return true;
      }
      else {
        return false;
      }
       */
       return true;


    }
}
