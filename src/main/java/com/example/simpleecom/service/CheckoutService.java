package com.example.simpleecom.service;

import com.example.simpleecom.dto.CountryDto;
import com.example.simpleecom.dto.Purchase;
import com.example.simpleecom.dto.PurchaseResponse;
import com.example.simpleecom.dto.StateDto;
import com.example.simpleecom.entity.*;
import com.example.simpleecom.repository.CountryRepository;
import com.example.simpleecom.repository.CustomerRepository;
import com.example.simpleecom.repository.StateRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CheckoutService {

    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final CustomerRepository customerRepository;

    public CheckoutService(CountryRepository countryRepository, StateRepository stateRepository, CustomerRepository customerRepository) {
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.customerRepository = customerRepository;
    }

    public List<CountryDto> getAllCountries() {
        return countryRepository.findAll()
                .stream()
                .map(this::countryEntityToDto)
                .collect(Collectors.toList());
    }

    public List<StateDto> getAllStates() {
        return stateRepository.findAll()
                .stream()
                .map(this::stateEntityToDto)
                .collect(Collectors.toList());
    }

    public List<StateDto> getStatesByCountryCode(String countryCode) {
        List<StateDto> stateDtoList = getAllStates();
        List<StateDto> result = new ArrayList<>();
        for (StateDto tempStateDto : stateDtoList) {
            if (tempStateDto.getCountryCode().equals(countryCode)) {
                result.add(tempStateDto);
            }
        }
        return result;
    }

    public PurchaseResponse placeOrder(Purchase purchase) {
        Order order = purchase.getOrder();

        String orderTrackingNumber = UUID.randomUUID().toString();
        order.setOrderTrackingNumber(orderTrackingNumber);

        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        order.setAddress(purchase.getOrder().getAddress());
        order.setCity(purchase.getOrder().getCity());
        order.setCountry(purchase.getOrder().getCountry());
        order.setState(purchase.getOrder().getState());
        order.setZipCode(purchase.getOrder().getZipCode());

        Customer customer = purchase.getCustomer();
        customer.add(order);
        customerRepository.save(customer);

        return new PurchaseResponse(orderTrackingNumber);
    }

    public CountryDto countryEntityToDto(Country country) {
        CountryDto tempCountryDto = new CountryDto();
        tempCountryDto.setId(country.getId());
        tempCountryDto.setName(country.getName());
        tempCountryDto.setCode(country.getCode());
        return tempCountryDto;
    }

    public StateDto stateEntityToDto(State state) {
        StateDto tempStateDto = new StateDto();
        tempStateDto.setId(state.getId());
        tempStateDto.setName(state.getName());
        tempStateDto.setCountryId(state.getCountry().getId());
        tempStateDto.setCountryCode(state.getCountry().getCode());
        tempStateDto.setCountryName(state.getCountry().getName());
        return tempStateDto;
    }

}
