package com.search.pharmacy.common.exception.orm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Persistable;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity<PK extends Serializable> implements Persistable<PK> {
    private static final long serialVersionUID = -5554308939380869754L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private PK id;

    @Temporal(TemporalType.TIMESTAMP)
    private @Nullable Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private @Nullable Date lastModifiedDate;

    /**
     * Must be {@link Transient} in order to ensure that no JPA provider complains because of a
     * missing setter.
     *
     * @see org.springframework.data.domain.Persistable#isNew()
     */
    @Transient // DATAJPA-622
    @JsonIgnore
    public boolean isNew() {
        return null == getId();
    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = new Date();
        this.lastModifiedDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastModifiedDate = new Date();
    }
}
