package com.example.hotelbooking.app.mediator;

import com.example.hotelbooking.app.services.*;

public class HotelBookingMediator implements BookingMediator {
    private final BookingService bookingService;
    private final PaymentService paymentService;
    private final NotificationService notificationService;
    private final InventoryService inventoryService;

    public HotelBookingMediator(BookingService bookingService, PaymentService paymentService,
                                NotificationService notificationService, InventoryService inventoryService) {
        this.bookingService = bookingService;
        this.paymentService = paymentService;
        this.notificationService = notificationService;
        this.inventoryService = inventoryService;
    }

    @Override
    public void notify(Object sender, BookingEvent event, String bookingId) {
        switch (event) {
            case BOOKING_CREATED:
                inventoryService.updateAvailability(bookingId, false);
                paymentService.processPayment(bookingId);
                notificationService.sendConfirmation(bookingId);
                break;

            case BOOKING_CANCELLED:
                inventoryService.updateAvailability(bookingId, true);
                paymentService.refundPayment(bookingId);
                notificationService.sendCancellation(bookingId);
                break;

            case NO_ROOMS_AVAILABLE:
                notificationService.sendOutOfRoomsNotification(bookingId);
                break;

            case MAX_ROOMS_REACHED:
                notificationService.sendMaxRoomsReachedNotification(bookingId);
                break;

            case AVAILABILITY_UPDATED:
                // Lógica adicional si es necesario
                break;

            case AVAILABILITY_RESTORED:
                // Lógica adicional si es necesario
                break;

            case CONFIRMATION_SENT:
                // Lógica adicional si es necesario
                break;

            case CANCELLATION_SENT:
                // Lógica adicional si es necesario
                break;

            // Otros casos según sea necesario...
        }
    }
}
