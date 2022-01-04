package net.therap.leavemanagement.dao;

import net.therap.leavemanagement.domain.Notification;
import net.therap.leavemanagement.util.Constant;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author rumi.dipto
 * @since 12/12/21
 */
@Repository
public class NotificationDao {

    @PersistenceContext(unitName = Constant.PERSISTENCE_UNIT)
    private EntityManager em;

    public List<Notification> findAllNotifications(long userId) {
        return em.createNamedQuery("Notification.findAllNotifications", Notification.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Notification> findAllUnseenNotifications(long userId) {
        return em.createNamedQuery("Notification.findAllUnseenNotifications", Notification.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Transactional
    public Notification saveOrUpdate(Notification notification) {
        if (notification.isNew()) {
            em.persist(notification);
        } else {
            notification = em.merge(notification);
        }

        return notification;
    }

    @Transactional
    public void delete(Notification notification) {
        em.remove(notification);
    }
}
