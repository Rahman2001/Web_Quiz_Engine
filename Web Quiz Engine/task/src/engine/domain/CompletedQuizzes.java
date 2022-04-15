package engine.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CompletedQuizzes{

    @Column
    private Long quiz_id; // quiz id

    @Id
    @Column(name = "completion_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long completionId;


    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long user_id;

    @Column
    private LocalDateTime completedAt;

    public Long getId() {
        return this.quiz_id;
    }

    public void setId(Long id) {
        this.quiz_id = id;
    }

    public Long getCompletionId() {
        return this.completionId;
    }

    public void setCompletionId(Long completionId) {
        this.completionId = completionId;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Long getUserId() {
        return this.user_id;
    }

    public void setUserId(Long user_id) {
        this.user_id = user_id;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

}
