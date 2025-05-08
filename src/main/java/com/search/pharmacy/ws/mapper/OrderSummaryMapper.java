package com.search.pharmacy.ws.mapper;

import com.search.pharmacy.common.exception.orm.AbstractMapper;
import com.search.pharmacy.domain.model.Order;
import com.search.pharmacy.ws.model.OrderSummaryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderSummaryMapper extends AbstractMapper<Order, OrderSummaryDTO> {

    @Override
    OrderSummaryDTO toDTO(final Order entity);

    @Override
    Order toEntity(final OrderSummaryDTO dto);
}
