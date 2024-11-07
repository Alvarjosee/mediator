package com.example.hotelbooking.app.mediator;

public interface BookingMediator {
    void notify(Object sender, BookingEvent event, String bookingId);
}
