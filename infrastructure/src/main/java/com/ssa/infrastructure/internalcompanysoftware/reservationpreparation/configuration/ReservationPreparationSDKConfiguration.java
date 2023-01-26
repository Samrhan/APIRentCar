package com.ssa.infrastructure.internalcompanysoftware.reservationpreparation.configuration;

import com.ssa.domain.rental.ports.ReservationPreparationSDK;
import com.ssa.infrastructure.internalcompanysoftware.reservationpreparation.APIBasedReservationPreparationSDK;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReservationPreparationSDKConfiguration {
    @Bean
    public ReservationPreparationSDK reservationPreparationSDK() {
        return new APIBasedReservationPreparationSDK();
    }
}
