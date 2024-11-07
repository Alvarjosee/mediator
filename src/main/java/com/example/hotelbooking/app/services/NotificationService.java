package com.example.hotelbooking.app.services;

import com.example.hotelbooking.app.mediator.BookingMediator;
import com.example.hotelbooking.app.mediator.BookingEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {
    private BookingMediator mediator;
    private final List<Map<String, String>> notifications = new ArrayList<>();

    // Constantes para los mensajes de retorno
    private static final String CONFIRMATION_SENT_MSG = "Confirmation sent";
    private static final String CANCELLATION_SENT_MSG = "Cancellation sent";

    public void setMediator(BookingMediator mediator) {
        this.mediator = mediator;
    }

    public Map<String, Object> sendConfirmation(String bookingId) {
        if (bookingId == null) {
            return Map.of("status", "Error: Invalid booking ID");
        }
        notifications.add(Map.of("booking_id", bookingId, "type", "confirmation"));
        notifyMediator(BookingEvent.BOOKING_CREATED, bookingId);  // Usar el evento correspondiente
        return Map.of("status", CONFIRMATION_SENT_MSG);
    }

    public Map<String, Object> sendCancellation(String bookingId) {
        if (bookingId == null) {
            return Map.of("status", "Error: Invalid booking ID");
        }
        notifications.add(Map.of("booking_id", bookingId, "type", "cancellation"));
        notifyMediator(BookingEvent.BOOKING_CANCELLED, bookingId);  // Usar el evento correspondiente
        return Map.of("status", CANCELLATION_SENT_MSG);
    }

    public void sendOutOfRoomsNotification(String bookingId) {
        System.out.println("Booking ID " + bookingId + ": Sorry, no rooms are available at the moment.");
        // Ejemplo de lógica adicional para enviar notificación
        notifyMediator(BookingEvent.NO_ROOMS_AVAILABLE, bookingId);
    }

    public void sendMaxRoomsReachedNotification(String bookingId) {
        System.out.println("Booking ID " + bookingId + ": Maximum room capacity reached.");
        // Ejemplo de lógica adicional para enviar notificación
        notifyMediator(BookingEvent.MAX_ROOMS_REACHED, bookingId);
    }

    private void notifyMediator(BookingEvent event, String bookingId) {
        if (mediator != null) {
            mediator.notify(this, event, bookingId);
        }
    }
}
