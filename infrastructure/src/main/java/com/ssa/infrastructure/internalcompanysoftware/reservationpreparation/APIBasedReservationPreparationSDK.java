package com.ssa.infrastructure.internalcompanysoftware.reservationpreparation;

import com.ssa.domain.rental.model.Reservation;
import com.ssa.domain.rental.ports.ReservationPreparationSDK;

public class APIBasedReservationPreparationSDK implements ReservationPreparationSDK {
    @Override
    public void prepareReservation(Reservation reservation) {
        // Here we would contact the other software and delegate to it the proper actions so that the
        // car would be prepared on time for the reservation
        System.out.println("***************************************************************************************\n");
        System.out.println("Reservation preparation company software received order to prepare a "
                + reservation.getCar().getYear() + " " + reservation.getCar().getBrand() + " " + reservation.getCar().getModel()
                + "\nfor the " + reservation.getStartDate() + " for a duration of " + reservation.getReservationDurationInDays()
                + " day(s)\nfor customer " + reservation.getCustomer().getFirstName() + " " + reservation.getCustomer().getLastName());
        System.out.println("\n***************************************************************************************");
    }
}
