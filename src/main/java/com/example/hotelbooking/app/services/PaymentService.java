package com.example.hotelbooking.app.services;

import com.example.hotelbooking.app.mediator.BookingMediator;
import com.example.hotelbooking.app.mediator.BookingEvent;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {
    private BookingMediator mediator;
    private final Map<String, String> transactions = new HashMap<>();

    public void setMediator(BookingMediator mediator) {
        this.mediator = mediator;
    }

    public Map<String, Object> processPayment(String bookingId) {
        transactions.put(bookingId, "completed");
        if (mediator != null) {
            mediator.notify(this, BookingEvent.PAYMENT_PROCESSED, bookingId); 
        }
        return Map.of("status", "Payment processed successfully");
    }

    public Map<String, Object> refundPayment(String bookingId) {
        if (transactions.containsKey(bookingId)) {
            transactions.put(bookingId, "refunded");
            if (mediator != null) {
                mediator.notify(this, BookingEvent.PAYMENT_REFUNDED, bookingId);  
            }
            return Map.of("status", "Payment refunded successfully");
        }
        return Map.of("status", "Transaction not found");
    }
}
