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

    public List<Leave> findProceededLeavesOfUser(long userId) {
        return em.createNamedQuery("Leave.findProceededLeavesOfUser", Leave.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Leave> findProceededLeavesOfUser(long userId, int page) {
        return em.createNamedQuery("Leave.findProceededLeavesOfUser", Leave.class)
                .setParameter("userId", userId)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<Leave> findPendingLeavesOfUser(long userId) {
        return em.createNamedQuery("Leave.findPendingLeavesOfUser", Leave.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Leave> findPendingLeavesOfUser(long userId, int page) {
        return em.createNamedQuery("Leave.findPendingLeavesOfUser", Leave.class)
                .setParameter("userId", userId)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<Leave> findAllProceededLeavesUnderTeamLead(long teamLeadId, int page) {
        return em.createNamedQuery("Leave.findAllProceededLeavesUnderTeamLead", Leave.class)
                .setParameter("teamLeadId", teamLeadId)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<Leave> findAllPendingLeavesUnderTeamLead(long teamLeadId, int page) {
        return em.createNamedQuery("Leave.findAllPendingLeavesUnderTeamLead", Leave.class)
                .setParameter("teamLeadId", teamLeadId)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<Leave> findAllProceededLeaves(int page) {
        return em.createNamedQuery("Leave.findAllProceededLeaves", Leave.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<Leave> findAllPendingLeaves(int page) {
        return em.createNamedQuery("Leave.findAllPendingLeaves", Leave.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public long countProceededLeavesOfUser(long userId) {
        return em.createNamedQuery("Leave.countProceededLeavesOfUser", Long.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    public long countPendingLeavesOfUser(long userId) {
        return em.createNamedQuery("Leave.countPendingLeavesOfUser", Long.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    public long countAllProceededLeavesUnderTeamLead(long teamLeadId) {
        return em.createNamedQuery("Leave.countAllProceededLeavesUnderTeamLead", Long.class)
                .setParameter("teamLeadId", teamLeadId)
                .getSingleResult();
    }

    public long countAllPendingLeavesUnderTeamLead(long teamLeadId) {
        return em.createNamedQuery("Leave.countAllPendingLeavesUnderTeamLead", Long.class)
                .setParameter("teamLeadId", teamLeadId)
                .getSingleResult();
    }

    public long countAllProceededLeaves() {
        return em.createNamedQuery("Leave.countAllProceededLeaves", Long.class)
                .getSingleResult();
    }

    public long countAllPendingLeaves() {
        return em.createNamedQuery("Leave.countAllPendingLeaves", Long.class)
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
