package be.ehb.swp2.entity.question;

import be.ehb.swp2.entity.Question;

/**
 * Created by Ibrahim on 03-11-15.
 */

public class TrueFalseQuestion extends Question {

    private boolean answer;

    public TrueFalseQuestion(String name, String description, int time, boolean timeOn, boolean answer) {
        super(name, description, time, timeOn);
        this.answer = answer;
    }
  /*  public TrueFalseQuestion(String name, String description, int time, boolean timeOn)
    {
        super();
        super(name, description, time, timeOn);
        this.answer = false;

    }*/

    public void setAnswer(boolean answer)
    {
        this.answer = answer;
    }

    public boolean getAnswer ()
    {
        return this.answer;
    }


    public boolean solve(){
         solve( answer);
return false;
    }

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
