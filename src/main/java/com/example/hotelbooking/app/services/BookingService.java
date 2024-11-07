package com.example.hotelbooking.app.services;

import com.example.hotelbooking.app.mediator.BookingMediator;
import com.example.hotelbooking.app.mediator.BookingEvent;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BookingService {
    private BookingMediator mediator;
    private final Map<String, Map<String, Object>> bookings = new HashMap<>();
    private final InventoryService inventoryService;

    public BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void setMediator(BookingMediator mediator) {
        this.mediator = mediator;
    }

    public Map<String, Object> createBooking(String bookingId, Map<String, Object> userData) {
        if (bookings.containsKey(bookingId)) {
            return Map.of("status", "Error: Booking ID already exists", "booking_id", bookingId);
        }

        String availabilityStatus = inventoryService.updateAvailability(bookingId, false);

        if (availabilityStatus.equals("No available rooms for new bookings")) {
            return Map.of("status", availabilityStatus);
        }

        bookings.put(bookingId, userData);

        if (mediator != null) {
            mediator.notify(this, BookingEvent.BOOKING_CREATED, bookingId);
        }

        return Map.of("status", "Booking created successfully", "booking_id", bookingId);
    }

    public Map<String, Object> cancelBooking(String bookingId) {
        if (bookings.remove(bookingId) != null) {
            inventoryService.updateAvailability(bookingId, true);
            if (mediator != null) {
                mediator.notify(this, BookingEvent.BOOKING_CANCELLED, bookingId);
            }
            return Map.of("status", "Booking cancelled successfully");
        }
        return Map.of("status", "Booking not found");
    }
}
