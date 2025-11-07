package jwp.dao;


import jwp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserDao {

    private final EntityManager em;

    @Transactional
    public void insert(User user){
        em.persist(user);
    }

    public void update(User user) {
        em.merge(user);
    }


    public List<User> findAll() {
        return em.createQuery("select u from User u").getResultList();
    }

    public User findByUserId(String userId) {
        return em.find(User.class, userId);
    }
}
