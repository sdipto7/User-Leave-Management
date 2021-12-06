package net.therap.leavemanagement.dao;

import net.therap.leavemanagement.domain.Leave;
import net.therap.leavemanagement.util.Constant;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author rumi.dipto
 * @since 12/1/21
 */
@Repository
public class LeaveDao {

    @PersistenceContext(unitName = Constant.UNIT_NAME)
    private EntityManager em;

    public Leave find(long id) {
        return em.find(Leave.class, id);
    }

    public List<Leave> findUserLeaveList(long id) {
        return em.createNamedQuery("Leave.findUserLeaveList", Leave.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Leave> findUserPendingLeaveList(long id) {
        return em.createNamedQuery("Leave.findUserPendingLeaveList", Leave.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Leave> findAllLeave() {
        return em.createNamedQuery("Leave.findAllLeave", Leave.class).getResultList();
    }

    public List<Leave> findAllPendingLeave() {
        return em.createNamedQuery("Leave.findAllPendingLeave", Leave.class).getResultList();
    }

    @Transactional
    public Leave saveOrUpdate(Leave leave) {
        if (leave.isNew()) {
            em.persist(leave);
        } else {
            leave = em.merge(leave);
        }

        return leave;
    }

    @Transactional
    public void delete(Leave leave) {
        em.remove(leave);
    }
}
