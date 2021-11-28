package net.therap.leavemanagement.dao;

import net.therap.leavemanagement.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author rumi.dipto
 * @since 11/27/21
 */
@Repository
public class UserManagementDao {

    @PersistenceContext(unitName = "leave-management-persistence-unit")
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
}
