package org.launchcode.stripe_api.controllers;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "http://localhost:3000")  // Allow requests from React frontend
public class PaymentController {

    @PostMapping("/create-payment-intent")
    public Map<String, Object> createPaymentIntent(@RequestBody Map<String, Object> data) throws StripeException {
        int amount = (int) data.get("amount");

        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount((long) amount)
                        .setCurrency("usd")
                        .setPaymentMethodTypes(java.util.List.of("card"))
                        .build();

        PaymentIntent intent = PaymentIntent.create(params);

        Map<String, Object> response = new HashMap<>();
        response.put("clientSecret", intent.getClientSecret());

        return response;
    }
}