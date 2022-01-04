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

    public List<Leave> findAllLeavesOfUser(long userId) {
        return em.createNamedQuery("Leave.findAllLeavesOfUser", Leave.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Leave> findUserLeaveList(long userId) {
        return em.createNamedQuery("Leave.findUserLeaveList", Leave.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Leave> findUserLeaveList(long userId, int page) {
        return em.createNamedQuery("Leave.findUserLeaveList", Leave.class)
                .setParameter("userId", userId)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<Leave> findUserPendingLeaveList(long userId) {
        return em.createNamedQuery("Leave.findUserPendingLeaveList", Leave.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Leave> findUserPendingLeaveList(long userId, int page) {
        return em.createNamedQuery("Leave.findUserPendingLeaveList", Leave.class)
                .setParameter("userId", userId)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<Leave> findAllLeaveUnderTeamLead(long teamLeadId, int page) {
        return em.createNamedQuery("Leave.findAllLeaveUnderTeamLead", Leave.class)
                .setParameter("teamLeadId", teamLeadId)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<Leave> findAllPendingLeaveUnderTeamLead(long teamLeadId, int page) {
        return em.createNamedQuery("Leave.findAllPendingLeaveUnderTeamLead", Leave.class)
                .setParameter("teamLeadId", teamLeadId)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<Leave> findAllLeave(int page) {
        return em.createNamedQuery("Leave.findAllLeave", Leave.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<Leave> findAllPendingLeave(int page) {
        return em.createNamedQuery("Leave.findAllPendingLeave", Leave.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public long countUserLeave(long userId) {
        return em.createNamedQuery("Leave.countUserLeave", Long.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    public long countUserPendingLeave(long userId) {
        return em.createNamedQuery("Leave.countUserPendingLeave", Long.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    public long countAllLeaveUnderTeamLead(long teamLeadId) {
        return em.createNamedQuery("Leave.countAllLeaveUnderTeamLead", Long.class)
                .setParameter("teamLeadId", teamLeadId)
                .getSingleResult();
    }

    public long countAllPendingLeaveUnderTeamLead(long teamLeadId) {
        return em.createNamedQuery("Leave.countAllPendingLeaveUnderTeamLead", Long.class)
                .setParameter("teamLeadId", teamLeadId)
                .getSingleResult();
    }

    public long countAllLeave() {
        return em.createNamedQuery("Leave.countAllLeave", Long.class)
                .getSingleResult();
    }

    public long countAllPendingLeave() {
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
        em.remove(leave);
    }
}
