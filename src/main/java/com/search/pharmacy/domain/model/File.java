package com.search.pharmacy.domain.model;

import com.search.pharmacy.common.exception.orm.AbstractEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "FILES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "ID_FILE"))
public class File extends AbstractEntity<Long> {

    @Column(name = "url")
    @ToString.Include
    private String url;
}
