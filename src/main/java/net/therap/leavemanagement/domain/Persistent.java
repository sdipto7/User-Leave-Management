package net.therap.leavemanagement.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author rumi.dipto
 * @since 11/22/21
 */
@MappedSuperclass
public class Persistent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @Version
    private int version;

    @PrePersist
    private void onCreate() {
        created = new Date();
    }

    @PreUpdate
    private void onUpdate() {
        updated = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isNew() {
        return getId() == 0;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (Objects.isNull(object) || !(object instanceof Persistent)) {
            return false;
        }

        return this.getId() == ((Persistent) object).getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
