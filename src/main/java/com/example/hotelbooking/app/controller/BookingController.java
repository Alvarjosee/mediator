package com.example.hotelbooking.app.controller;

import com.example.hotelbooking.app.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public Map<String, Object> createBooking(@RequestBody Map<String, Object> bookingData) {
        String bookingId = (String) bookingData.get("booking_id");
        Map<String, Object> userData = (Map<String, Object>) bookingData.get("user_data");
        return bookingService.createBooking(bookingId, userData);
    }

    @PostMapping("/cancel")
    public Map<String, Object> cancelBooking(@RequestBody Map<String, Object> bookingData) {
        String bookingId = (String) bookingData.get("booking_id");
        return bookingService.cancelBooking(bookingId);
    }
}
