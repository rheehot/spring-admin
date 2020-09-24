package com.noyo0123.backoffice.service;

import com.noyo0123.backoffice.controller.ifs.CrudInterface;
import com.noyo0123.backoffice.model.entity.OrderGroup;
import com.noyo0123.backoffice.model.network.Header;
import com.noyo0123.backoffice.model.network.request.OrderGroupApiRequest;
import com.noyo0123.backoffice.model.network.response.OrderGroupApiResponse;
import com.noyo0123.backoffice.repository.OrderGroupRepository;
import com.noyo0123.backoffice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderGroupApiService implements CrudInterface<OrderGroupApiRequest, OrderGroupApiResponse> {

    @Autowired
    private OrderGroupRepository orderGroupRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Header<OrderGroupApiResponse> create(Header<OrderGroupApiRequest> req) {

        OrderGroupApiRequest body = req.getData();

        OrderGroup orderGroup = OrderGroup.builder()
                .status(body.getStatus())
                .orderType(body.getOrderType())
                .revAddress(body.getRevAddress())
                .revName(body.getRevName())
                .paymentType(body.getPaymentType())
                .totalPrice(body.getTotalPrice())
                .totalQuantity(body.getTotalQuantity())
                .orderAt(LocalDateTime.now())
                .arrivalDate(body.getArrivalDate())
                .user(userRepository.getOne(body.getUserId()))
                .build();

        OrderGroup newOrderGroup = orderGroupRepository.save(orderGroup);
        return resposne(newOrderGroup);
    }

    @Override
    public Header<OrderGroupApiResponse> read(Long id) {
        return null;
    }

    @Override
    public Header<OrderGroupApiResponse> update(Header<OrderGroupApiRequest> req) {
        return null;
    }

    @Override
    public Header delete(Long id) {
        return null;
    }

    // response의 형태가 Header<OrderGroupApiResponse>로 동일하게 구성되어있어서 공통메서드로 빼내는 작업 수행

    public Header<OrderGroupApiResponse> resposne(OrderGroup orderGroup) {
        OrderGroupApiResponse body = OrderGroupApiResponse.builder()
                .id(orderGroup.getId())
                .status(orderGroup.getStatus())
                .orderType(orderGroup.getOrderType())
                .revAddress(orderGroup.getRevAddress())
                .revName(orderGroup.getRevName())
                .paymentType(orderGroup.getPaymentType())
                .totalPrice(orderGroup.getTotalPrice())
                .totalQuantity(orderGroup.getTotalQuantity())
                .orderAt(orderGroup.getOrderAt())
                .arrivalDate(orderGroup.getArrivalDate())
                .userId(orderGroup.getUser().getId())
                .build();
        return Header.OK(body);
    }
}