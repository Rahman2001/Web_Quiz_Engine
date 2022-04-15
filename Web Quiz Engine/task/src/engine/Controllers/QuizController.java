package engine.Controllers;

import engine.Repositories.CompletedQuizzesRepo;
import engine.Repositories.QuizRepo;
import engine.Repositories.UserRepo;
import engine.domain.AnswerOfQuiz;
import engine.domain.CompletedQuizzes;
import engine.domain.Quiz;
import engine.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    private final QuizRepo quizRepo;
    private final UserRepo userRepo;
    private final CompletedQuizzesRepo compQuizRepo;

    @Autowired
    public QuizController(QuizRepo quizRepo, UserRepo userRepo, CompletedQuizzesRepo compQuizRepo){
        this.quizRepo = quizRepo;
        this.userRepo = userRepo;
        this.compQuizRepo = compQuizRepo;
    }

    @GetMapping
    public Page<Quiz> getAllQuizzes(@RequestParam Integer page) {
        Pageable pages = PageRequest.of(page, 10);
        return this.quizRepo.findAll(pages);
    }

    @GetMapping("/{id}")
    public Quiz getQuiz(@PathVariable Long id) {
        Optional<Quiz> quiz = this.quizRepo.findById(id);
        if(quiz.isPresent()) {
            return quiz.get();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/completed")
    public Page<CompletedQuizzes> getAllCompletedQuizzes(@RequestParam(defaultValue = "0") Integer page,
            @AuthenticationPrincipal UserDetails userDetails){
        User user = this.userRepo.findByEmail(userDetails.getUsername());
        Pageable pages = PageRequest.of(page, 10);
        return this.compQuizRepo.findByUserId(user.getId(), pages);
    }

    @PostMapping("/{id}/solve")
    public AnswerOfQuiz response(@PathVariable("id") Long quizId, @RequestBody String answer,
                                 @AuthenticationPrincipal UserDetails userDetails){
        AnswerOfQuiz answerOfQuiz = new AnswerOfQuiz();
        CompletedQuizzes completedQuizzes = new CompletedQuizzes();
        User user = userRepo.findByEmail(userDetails.getUsername());

        Optional<Quiz> quizOptional = this.quizRepo.findById(quizId);
        Quiz quiz;
        if(quizOptional.isPresent()) { // if there is a quiz by given id then proceed
            quiz = quizOptional.get();

            completedQuizzes.setId(quiz.getId()); // set quiz info
            completedQuizzes.setUserId(user.getId()); // set user info
            completedQuizzes.setCompletedAt(LocalDateTime.now()); // set current date and time info

            List<CompletedQuizzes> completedQuizzesCopy = compQuizRepo.findByQuizIdUserIdCompletedAt(completedQuizzes.getId(),
                    completedQuizzes.getUserId(), completedQuizzes.getCompletedAt()); //retrieve that completed quiz from db

            answer = answer.replaceAll("[^0-9]", ""); //using regex, we avoid all characters in string besides the numbers and delete/replace other characters
            if (quiz.hasAnswer()) { // if the quiz has an answer then proceed
                if (!answer.isEmpty()) { // if our answer choice is not null then proceed
                    int[] temp = Stream.of(answer.split("")).mapToInt(Integer::parseInt).toArray(); //converts answer sent in JSON format as a string to an integer array
                    answerOfQuiz.setFeedbackIf(quiz.checkAnswersById(temp));
                    if(answerOfQuiz.getSuccess()) { // only if a user could get correct answer, then save completed quiz into "Completed_quizzes" table

                        if( completedQuizzesCopy == null || completedQuizzesCopy.size() == 0) { // check if there is such quiz with same LocalDateTime in "Completed_Quizzes" table
                            compQuizRepo.save(completedQuizzes); // if there isn't such , save it into db
                        }
                    }
                } else {
                    answerOfQuiz.setFeedbackIf(false);
                }
            } else { // if the quiz doesn't have any answer then proceed
                answerOfQuiz.setFeedbackIf(answer.isEmpty()); //success is true only if user's answer is also empty

                if( answerOfQuiz.getSuccess() &&
                        (completedQuizzesCopy == null || completedQuizzesCopy.size() == 0)) { // check if there is such quiz with same LocalDateTime in "Completed_Quizzes" table
                    compQuizRepo.save(completedQuizzes); // if there isn't such , save it into db
                }
            }
            return answerOfQuiz; // if author id of that quiz is equal to the current user's id return answer

        }else { // if there is no quiz with such id then proceed
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public Quiz createQuiz( @Valid @RequestBody Quiz quiz, @AuthenticationPrincipal UserDetails userDetails){
        User user = this.userRepo.findByEmail(userDetails.getUsername());

        if(quiz.getAnswer() != null) { // if there exists any answer then proceed
            boolean checkIndexOfArray = Arrays.stream(quiz.getAnswer())
                    .filter(x -> x > quiz.getOptions().length - 1).findAny().isPresent(); //check whether answer choices are corresponding to the given options

            if (quiz.getAnswer().length <= quiz.getOptions().length && !checkIndexOfArray) {
                quiz.setUser(user);
                this.quizRepo.save(quiz);
                return this.quizRepo.findById(quiz.getId()).orElse(null);

            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }else{ // if there doesn't exist any answer then proceed
            this.quizRepo.save(quiz);
            return this.quizRepo.findById(quiz.getId()).orElse(null);
        }
    }

    @DeleteMapping("/{id}")
    public HttpStatus responseStatus(@PathVariable long id,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Quiz> quiz = this.quizRepo.findById(id);
        if(quiz.isPresent()) { // if such quiz exists, then ...
            Long userId = this.userRepo.findByEmail(userDetails.getUsername()).getId();
            Quiz realQuiz = quiz.get();
            if(realQuiz.getUser() != null) { //if that quiz has its author then proceed..
                if (Objects.equals(quiz.get().getUser().getId(), userId)) { // compare that quiz's user id and current user id
                    this.quizRepo.deleteById(id); // deletes that quiz
                    throw new ResponseStatusException(HttpStatus.NO_CONTENT); // if found equal, delete that quiz
                } else {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN); // if didn't find equal, then forbid for deletion
                }
            }
            throw new ResponseStatusException(HttpStatus.FORBIDDEN); // if that quiz doesn't have its author, then return forbidden
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND); // if such quiz doesn't exist, then throw status 404
        }
    }
}