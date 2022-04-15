package engine.Repositories;

import engine.domain.CompletedQuizzes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CompletedQuizzesRepo extends CrudRepository<CompletedQuizzes, Long> {
    @Query(value = "SELECT * FROM COMPLETED_QUIZZES WHERE user_id = ?1 ORDER BY completed_At DESC",
            countQuery = "SELECT COUNT(*) FROM COMPLETED_QUIZZES WHERE user_id = ?1",
            nativeQuery = true)
    Page<CompletedQuizzes> findByUserId(Long userId, Pageable pageable);

    @Query(value = "SELECT * FROM COMPLETED_QUIZZES WHERE quiz_id = ?1 and user_id = ?2 and " +
            "completed_At = ?3",
            nativeQuery = true)
    List<CompletedQuizzes> findByQuizIdUserIdCompletedAt(Long quizId, Long userId,
                                                         LocalDateTime localDateTime);
}
