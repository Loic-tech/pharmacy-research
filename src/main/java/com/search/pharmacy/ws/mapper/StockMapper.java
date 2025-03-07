package com.search.pharmacy.ws.mapper;

import com.search.pharmacy.common.exception.orm.AbstractMapper;
import com.search.pharmacy.domain.model.Stock;
import com.search.pharmacy.utils.Qualifier;
import com.search.pharmacy.ws.model.StockDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { Qualifier.class })
public interface StockMapper extends AbstractMapper<Stock, StockDTO> {

    @Override
    @Mapping(target = "idMedicine", source = "medicine.id")
    @Mapping(target = "idPharmacy", source = "pharmacy.id")
    StockDTO toDTO(final Stock entity);

    @Override
    @Mapping(
            target = "pharmacy",
            source = "idPharmacy",
            qualifiedByName = {"Qualifier", "IdToPharmacy"}
    )
    @Mapping(
            target = "medicine",
            source = "idMedicine",
            qualifiedByName = {"Qualifier", "IdToMedicine"}
    )
    Stock toEntity(final StockDTO dto);
}

