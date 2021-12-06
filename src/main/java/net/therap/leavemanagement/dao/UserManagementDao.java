package net.therap.leavemanagement.dao;

import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.domain.UserManagement;
import net.therap.leavemanagement.util.Constant;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author rumi.dipto
 * @since 11/27/21
 */
@Repository
public class UserManagementDao {

    @PersistenceContext(unitName = Constant.UNIT_NAME)
    private EntityManager em;

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

    public List<User> findAllTesterUnderTeamLead(long id) {
        return em.createNamedQuery("UserManagement.findAllTesterUnderTeamLead", User.class)
                .setParameter("id", id)
                .getResultList();
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
}
