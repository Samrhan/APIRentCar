package com.ssa.adapters.internalcompanysoftware.reservationpreparation.configuration;

import com.ssa.adapters.internalcompanysoftware.reservationpreparation.APIBasedReservationPreparationSDK;
import com.ssa.domain.rental.ports.ReservationPreparationSDK;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReservationPreparationSDKConfiguration {
    @Bean
    public ReservationPreparationSDK reservationPreparationSDK() {
        return new APIBasedReservationPreparationSDK();
    }
}
