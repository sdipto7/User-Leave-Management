package net.therap.leavemanagement.dao;

import net.therap.leavemanagement.domain.LeaveStat;
import net.therap.leavemanagement.util.Constant;
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

    @PersistenceContext(unitName = Constant.PERSISTENCE_UNIT)
    private EntityManager em;

    public LeaveStat findLeaveStatByUserId(long userId) {
        return em.createNamedQuery("LeaveStat.findLeaveStatByUserId", LeaveStat.class)
                .setParameter("id", userId)
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

    @Transactional
    public void delete(LeaveStat leaveStat) {
        em.remove(leaveStat);
    }
}
