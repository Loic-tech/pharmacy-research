package com.search.pharmacy.ws.mapper;

import com.search.pharmacy.common.exception.orm.AbstractMapper;
import com.search.pharmacy.domain.model.Cart;
import com.search.pharmacy.domain.model.Medicine;
import com.search.pharmacy.domain.model.Order;
import com.search.pharmacy.ws.model.CartDTO;
import com.search.pharmacy.ws.model.MedicineListDTO;
import com.search.pharmacy.ws.model.OrderDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper extends AbstractMapper<Order, OrderDTO> {

    CartDTO cartToCartDTO(final Cart cart);
    List<CartDTO> cartListToCartDTOList(final List<Cart> cartList);
    Cart cartDTOToCart(final CartDTO cartDTO);
    MedicineListDTO medicineToMedicineListDTO(final Medicine medicine);
}
