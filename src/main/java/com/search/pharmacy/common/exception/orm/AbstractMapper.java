package com.search.pharmacy.common.exception.orm;

import com.search.pharmacy.common.exception.orm.AbstractEntity;

public interface AbstractMapper<E extends AbstractEntity, D>  {
    /**
     * Convert the <code>E</code> entity object to its corresponding DTO <code>D</code>.
     *
     * @param entity of <code>E</code> entity object to convert
     * @return <code>D</code> DTO result
     */
    D toDTO(final E entity);

    /**
     * Convert the <code>D</code> DTO object to its corresponding entity <code>E</code>.
     *
     * @param dto of <code>D</code> DTO to convert
     * @return <code>E</code> entity object result
     */
    E toEntity(final D dto);
}
