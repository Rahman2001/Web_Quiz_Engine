package engine.Repositories;

import engine.domain.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepo extends CrudRepository<Quiz, Long> {
    @Query(value = "SELECT * FROM QUIZ q ORDER BY q.id",
            countQuery = "SELECT COUNT(*) FROM QUIZ ",
            nativeQuery = true)
    Page<Quiz> findAll(Pageable pageable);
}
