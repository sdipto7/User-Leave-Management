package net.therap.leavemanagement.dao;

import net.therap.leavemanagement.domain.Leave;
import net.therap.leavemanagement.util.Constant;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static net.therap.leavemanagement.util.Constant.pageSize;

/**
 * @author rumi.dipto
 * @since 12/1/21
 */
@Repository
public class LeaveDao {

    @PersistenceContext(unitName = Constant.PERSISTENCE_UNIT)
    private EntityManager em;

    public Leave find(long id) {
        return em.find(Leave.class, id);
    }

    public List<Leave> findUserLeaveList(long id) {
        return em.createNamedQuery("Leave.findUserLeaveList", Leave.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Leave> findUserLeaveList(long id, int page) {
        return em.createNamedQuery("Leave.findUserLeaveList", Leave.class)
                .setParameter("id", id)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public Long countUserLeave(long id) {
        return em.createNamedQuery("Leave.countUserLeave", Long.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<Leave> findUserPendingLeaveList(long id) {
        return em.createNamedQuery("Leave.findUserPendingLeaveList", Leave.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Leave> findUserPendingLeaveList(long id, int page) {
        return em.createNamedQuery("Leave.findUserPendingLeaveList", Leave.class)
                .setParameter("id", id)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public Long countUserPendingLeave(long id) {
        return em.createNamedQuery("Leave.countUserPendingLeave", Long.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<Leave> findAllLeave(int page) {
        return em.createNamedQuery("Leave.findAllLeave", Leave.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public Long countAllLeave() {
        return em.createNamedQuery("Leave.countAllLeave", Long.class)
                .getSingleResult();
    }

    public List<Leave> findAllPendingLeave(int page) {
        return em.createNamedQuery("Leave.findAllPendingLeave", Leave.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public Long countAllPendingLeave() {
        return em.createNamedQuery("Leave.countAllPendingLeave", Long.class)
                .getSingleResult();
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
        em.remove(em.getReference(Leave.class, leave.getId()));
    }
}
