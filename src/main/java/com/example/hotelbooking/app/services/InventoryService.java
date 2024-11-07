package com.example.hotelbooking.app.services;

import com.example.hotelbooking.app.mediator.BookingMediator;
import com.example.hotelbooking.app.mediator.BookingEvent;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    private BookingMediator mediator;
    private static final int MAX_ROOMS = 4;
    private int availableRooms = 2;

    public void setMediator(BookingMediator mediator) {
        this.mediator = mediator;
    }

    public String updateAvailability(String bookingId, boolean cancel) {
        if (cancel) {
            if (availableRooms < MAX_ROOMS) {
                availableRooms++;
            } else {
                if (mediator != null) {
                    mediator.notify(this, BookingEvent.MAX_ROOMS_REACHED, bookingId);
                }
                return "Cannot cancel more bookings as rooms have reached maximum availability";
            }
        } else {
            if (availableRooms > 0) {
                availableRooms--;
            } else {
                if (mediator != null) {
                    mediator.notify(this, BookingEvent.NO_ROOMS_AVAILABLE, bookingId);
                }
                return "No available rooms for new bookings";
            }
        }

        if (mediator != null) {
            mediator.notify(this, cancel ? BookingEvent.AVAILABILITY_RESTORED : BookingEvent.AVAILABILITY_UPDATED, bookingId);
        }

        return "Availability updated successfully";
    }
}
