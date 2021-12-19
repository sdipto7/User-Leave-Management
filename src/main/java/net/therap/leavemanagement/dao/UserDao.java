package net.therap.leavemanagement.dao;

import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.util.Constant;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

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
        return em.createNamedQuery("User.findAllTeamLead", User.class).getResultList();
    }

    public TypedQuery<Long> countTeamLead() {
        return em.createQuery("SELECT COUNT(*) FROM User u WHERE u.designation = 'TEAM_LEAD'", Long.class);
    }

    public List<User> findAllDeveloper() {
        return em.createNamedQuery("User.findAllDeveloper", User.class).getResultList();
    }

    public TypedQuery<Long> countDeveloper() {
        return em.createQuery("SELECT COUNT(*) FROM User u WHERE u.designation = 'DEVELOPER'", Long.class);
    }

    public List<User> findAllTester() {
        return em.createNamedQuery("User.findAllTester", User.class).getResultList();
    }

    public TypedQuery<Long> countTester() {
        return em.createQuery("SELECT COUNT(*) FROM User u WHERE u.designation = 'TESTER'", Long.class);
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
        em.flush();
        em.remove(em.getReference(User.class, user.getId()));
    }
}
