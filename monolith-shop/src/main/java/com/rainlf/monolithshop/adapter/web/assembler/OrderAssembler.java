package com.rainlf.monolithshop.adapter.web.assembler;

import com.rainlf.monolithshop.adapter.web.dto.OrderDTO;
import com.rainlf.monolithshop.domain.order.model.Order;
import com.rainlf.monolithshop.domain.order.model.valueobject.Detail;
import com.rainlf.monolithshop.domain.order.model.valueobject.Status;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : rain
 * @date : 2021/1/27 20:34
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderAssembler {
    public static Order toOrder(OrderDTO orderDTO) {
        if (orderDTO == null) {
            return null;
        }
        Order order = new Order();
        BeanUtils.copyProperties(orderDTO, order);
        order.setStatus(Status.fromValue(orderDTO.getStatus()));
        order.setDetailList(toDetail(orderDTO.getDetailList()));
        return order;
    }

    public static OrderDTO orderDTO(Order order) {
        if (order == null) {
            return null;
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(order, orderDTO);
        orderDTO.setStatus(order.getStatus().getValue());
        orderDTO.setDetailList(toDetailDTO(order.getDetailList()));
        return orderDTO;
    }

    public static List<Detail> toDetail(List<OrderDTO.DetailDTO> detailDTOList) {
        if (detailDTOList == null) {
            return null;
        }
        return detailDTOList.stream()
                .map(detailDTO -> new Detail(detailDTO.getId(), detailDTO.getName(), detailDTO.getPrice(), detailDTO.getNumber()))
                .collect(Collectors.toList());
    }

    private static List<OrderDTO.DetailDTO> toDetailDTO(List<Detail> detailList) {
        if (detailList == null) {
            return null;
        }
        return detailList.stream()
                .map(detail -> {
                    OrderDTO.DetailDTO detailDTO = new OrderDTO.DetailDTO();
                    detailDTO.setId(detail.getGoodsId());
                    detailDTO.setName(detail.getGoodsName());
                    detailDTO.setPrice(detail.getGoodsPrice());
                    detailDTO.setNumber(detail.getGoodsNumber());
                    return detailDTO;
                })
                .collect(Collectors.toList());
    }
}
