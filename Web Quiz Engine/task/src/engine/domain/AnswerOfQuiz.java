package engine.domain;

public class AnswerOfQuiz {
    private boolean success ;
    private String feedback;

    public AnswerOfQuiz(){}

    public boolean getSuccess(){
        return this.success;
    }
    public void setSuccess(boolean success){
        this.success = success;
    }

    public String getFeedback(){
        return this.feedback;
    }

    public void setFeedbackIf(boolean success){
        this.setSuccess(success);
        if(!this.getSuccess()){
            this.feedback = "Wrong answer! Please, try again.";
        }else{
            this.feedback = "Congratulations, you're right!";
        }
    }
}
