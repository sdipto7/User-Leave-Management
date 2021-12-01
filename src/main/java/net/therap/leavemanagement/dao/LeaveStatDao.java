package net.therap.leavemanagement.dao;

import net.therap.leavemanagement.domain.LeaveStat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author rumi.dipto
 * @since 11/28/21
 */
@Repository
public class LeaveStatDao {

    @PersistenceContext(unitName = "leave-management-persistence-unit")
    private EntityManager em;

    public LeaveStat findLeaveStatByUserId(long id) {
        return em.createNamedQuery("LeaveStat.findLeaveStatByUserId", LeaveStat.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Transactional
    public LeaveStat saveOrUpdate(LeaveStat leaveStat) {
        if (leaveStat.isNew()) {
            em.persist(leaveStat);
        } else {
            leaveStat = em.merge(leaveStat);
        }

        return leaveStat;
    }
}
