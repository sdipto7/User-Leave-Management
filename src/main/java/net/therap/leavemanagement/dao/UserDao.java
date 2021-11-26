package net.therap.leavemanagement.dao;

import net.therap.leavemanagement.domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author rumi.dipto
 * @since 11/22/21
 */
@Repository
public class UserDao {

    @PersistenceContext(unitName = "leave-management-persistence-unit")
    private EntityManager em;

    public User find(long id) {
        return em.find(User.class, id);
    }

    public User findByUsername(String username) {
        return em.createNamedQuery("User.findByUsername", User.class)
                .setParameter("username", username)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public List<User> findAllTeamlead() {
        return em.createNamedQuery("User.findAllTeamlead", User.class).getResultList();
    }

    public List<User> findAllDeveloper() {
        return em.createNamedQuery("User.findAllDeveloper", User.class).getResultList();
    }

    public List<User> findAllTester() {
        return em.createNamedQuery("User.findAllTester", User.class).getResultList();
    }

    public List<User> findAll() {
        return em.createNamedQuery("User.findAll", User.class).getResultList();
    }

    @Transactional
    public User saveOrUpdate(User user) {
        if (user.isNew()) {
            em.persist(user);
        } else {
            user = em.merge(user);
        }

        return user;
    }

    @Transactional
    public void delete(User user) {
        em.remove(user);
    }
}
