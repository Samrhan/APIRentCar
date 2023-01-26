package com.bader.infrastructure.internalcompanysoftware.reservationpreparation.configuration;

import com.bader.domain.rental.ports.ReservationPreparationSDK;
import com.bader.infrastructure.internalcompanysoftware.reservationpreparation.APIBasedReservationPreparationSDK;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReservationPreparationSDKConfiguration {
    @Bean
    public ReservationPreparationSDK reservationPreparationSDK() {
        return new APIBasedReservationPreparationSDK();
    }
}
