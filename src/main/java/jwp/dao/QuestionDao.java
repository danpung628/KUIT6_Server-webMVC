package jwp.dao;

import jwp.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionDao {

    private final EntityManager em;

    @Transactional
    public Question insert(Question question) {
        em.persist(question);
        return question;
    }

    @Transactional
    public void update(Question question) {
        em.merge(question);
    }

    @Transactional
    public void delete(int questionId) {
        Question question = em.find(Question.class, questionId);
        if (question != null) {
            em.remove(question);
        }
    }

    public List<Question> findAll() {
        return em.createQuery("select q from Question q order by q.questionId", Question.class)
                .getResultList();
    }

    public Question findByQuestionId(int questionId) {
        return em.find(Question.class, questionId);
    }
}