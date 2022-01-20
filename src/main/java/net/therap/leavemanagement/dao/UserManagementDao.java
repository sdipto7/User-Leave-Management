package net.therap.leavemanagement.dao;

import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.domain.UserManagement;
import net.therap.leavemanagement.util.Constant;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static net.therap.leavemanagement.util.Constant.pageSize;

/**
 * @author rumi.dipto
 * @since 11/27/21
 */
@Repository
public class UserManagementDao {

    @PersistenceContext(unitName = Constant.PERSISTENCE_UNIT)
    private EntityManager em;

    public List<UserManagement> findAllUserManagementByTeamLeadId(long teamLeadId) {
        return em.createNamedQuery("UserManagement.findAllUserManagementByTeamLeadId", UserManagement.class)
                .setParameter("teamLeadId", teamLeadId)
                .getResultList();
    }

    public UserManagement findUserManagementByUserId(long userId) {
        return em.createNamedQuery("UserManagement.findUserManagementByUserId", UserManagement.class)
                .setParameter("userId", userId)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public User findTeamLeadByUserId(long id) {
        return em.createNamedQuery("UserManagement.findTeamLead", User.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public List<User> findAllDeveloperUnderTeamLead(long id) {
        return em.createNamedQuery("UserManagement.findAllDeveloperUnderTeamLead", User.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<User> findAllDeveloperUnderTeamLead(long id, int page) {
        return em.createNamedQuery("UserManagement.findAllDeveloperUnderTeamLead", User.class)
                .setParameter("id", id)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public long countDeveloperUnderTeamLead(long id) {
        return em.createNamedQuery("UserManagement.countDeveloperUnderTeamLead", Long.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<User> findAllTesterUnderTeamLead(long id) {
        return em.createNamedQuery("UserManagement.findAllTesterUnderTeamLead", User.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<User> findAllTesterUnderTeamLead(long id, int page) {
        return em.createNamedQuery("UserManagement.findAllTesterUnderTeamLead", User.class)
                .setParameter("id", id)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public long countTesterUnderTeamLead(long id) {
        return em.createNamedQuery("UserManagement.countTesterUnderTeamLead", Long.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Transactional
    public UserManagement saveOrUpdate(UserManagement userManagement) {
        if (userManagement.isNew()) {
            em.persist(userManagement);
        } else {
            userManagement = em.merge(userManagement);
        }
        return userManagement;
    }

    @Transactional
    public void delete(UserManagement userManagement) {
        em.remove(userManagement);
    }
}
