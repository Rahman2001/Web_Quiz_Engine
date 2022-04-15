package engine.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import engine.JPAConverter.IntegerArrayToString;
import engine.JPAConverter.StringArrayToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Quiz {

    @NotBlank(message = "Title should not be empty!")
    @Column
    private String title;

    @NotBlank(message = "Text should not be empty!")
    @Column
    private String text;

    @NotNull
    @Size(min = 2)
    @Column
    @Convert(converter = StringArrayToString.class)
    private String[] options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column
    @Convert(converter = IntegerArrayToString.class)
    private int[] answer;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {this.user = user;}

    public Quiz(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public void setAnswer( int[] answer) {
        this.answer = answer;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public int[] getAnswer(){return this.answer;}

    public boolean checkAnswersById(int[] answerOfClient){
        int checkNumber = 0;
        if(answerOfClient.length == this.answer.length) { // check if a client presented all the answers or not by comparing the length
            for (int j : this.answer) {
                for (int k : answerOfClient) {
                    if (k == j) {
                        checkNumber++;
                    }
                }
            }
            return answerOfClient.length == checkNumber;
        }else{
            return false;
        }
    }

    public boolean hasAnswer(){
        return this.answer != null && this.answer.length != 0;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return this.id;
    }
}