package net.therap.leavemanagement.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author rumi.dipto
 * @since 12/12/21
 */
@Entity
@Table(name = "lm_notification")
@NamedQueries({
        @NamedQuery(name = "Notification.findAllNotifications",
                query = "SELECT n FROM Notification n WHERE n.user.id = :userId"),

        @NamedQuery(name = "Notification.findAllUnseenNotifications",
                query = "SELECT n FROM Notification n WHERE n.user.id = :userId AND n.seen = FALSE")
})
public class Notification extends Persistent {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @Size(min = 2, max = 100)
    @NotNull
    private String message;

    @NotNull
    private boolean seen;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
