package net.therap.leavemanagement.dao;

import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.util.Constant;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static net.therap.leavemanagement.util.Constant.pageSize;

/**
 * @author rumi.dipto
 * @since 11/22/21
 */
@Repository
public class UserDao {

    @PersistenceContext(unitName = Constant.PERSISTENCE_UNIT)
    private EntityManager em;

    public User find(long id) {
        return em.find(User.class, id);
    }

    public User findHrExecutive() {
        return em.createNamedQuery("User.findHrExecutive", User.class)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public User findByUsername(String username) {
        return em.createNamedQuery("User.findByUsername", User.class)
                .setParameter("username", username)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public List<User> findAllTeamLead() {
        return em.createNamedQuery("User.findAllTeamLead", User.class)
                .getResultList();
    }

    public List<User> findAllTeamLead(int page) {
        return em.createNamedQuery("User.findAllTeamLead", User.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public long countTeamLead() {
        return em.createNamedQuery("User.countTeamLead", Long.class)
                .getSingleResult();
    }

    public List<User> findAllDeveloper(int page) {
        return em.createNamedQuery("User.findAllDeveloper", User.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public long countDeveloper() {
        return em.createNamedQuery("User.countDeveloper", Long.class)
                .getSingleResult();
    }

    public List<User> findAllTester(int page) {
        return em.createNamedQuery("User.findAllTester", User.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public long countTester() {
        return em.createNamedQuery("User.countTester", Long.class)
                .getSingleResult();
    }

    public List<User> findAll(int page) {
        return em.createNamedQuery("User.findAll", User.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public long countAll() {
        return em.createNamedQuery("User.countAll", Long.class)
                .getSingleResult();
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
        em.remove(em.getReference(User.class, user.getId()));
    }
}
