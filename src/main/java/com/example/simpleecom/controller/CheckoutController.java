package com.example.simpleecom.controller;

import com.example.simpleecom.dto.CountryDto;
import com.example.simpleecom.dto.Purchase;
import com.example.simpleecom.dto.PurchaseResponse;
import com.example.simpleecom.dto.StateDto;
import com.example.simpleecom.service.CheckoutService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300"})
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    @GetMapping("/countries")
    public List<CountryDto> getAllCountries() {
        return checkoutService.getAllCountries();
    }

    @GetMapping("/states")
    public List<StateDto> getAllStates() {
        return checkoutService.getAllStates();
    }

    @GetMapping("/states/{countryCode}")
    public List<StateDto> getStatesByCountryCode(@PathVariable String countryCode) {
        return checkoutService.getStatesByCountryCode(countryCode);
    }

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {
        PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchase);
        return purchaseResponse;
    }
}
