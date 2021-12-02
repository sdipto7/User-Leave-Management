package net.therap.leavemanagement.dao;

import net.therap.leavemanagement.domain.Leave;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author rumi.dipto
 * @since 12/1/21
 */
@Repository
public class LeaveDao {

    @PersistenceContext(unitName = "leave-management-persistence-unit")
    private EntityManager em;

    public Leave find(long id) {
        return em.find(Leave.class, id);
    }

    public List<Leave> findAll() {
        return em.createNamedQuery("Leave.findAll", Leave.class).getResultList();
    }
}
