package com.search.pharmacy.ws.mapper;

import com.search.pharmacy.common.exception.mapper.AssetQualifier;
import com.search.pharmacy.common.exception.orm.AbstractMapper;
import com.search.pharmacy.domain.model.Order;
import com.search.pharmacy.ws.model.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AssetQualifier.class)
public interface OrderMapper extends AbstractMapper<Order, OrderDTO> {

    @Override
    OrderDTO toDTO(final Order entity);

    @Override
    @Mapping(target = "user", source = "userId", qualifiedByName = "IdToUser")
    Order toEntity(final OrderDTO dto);
}
