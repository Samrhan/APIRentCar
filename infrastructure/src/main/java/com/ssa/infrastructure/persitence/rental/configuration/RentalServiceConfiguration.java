package com.ssa.infrastructure.persitence.rental.configuration;

import com.ssa.domain.notification.NotificationService;
import com.ssa.domain.payment.PaymentService;
import com.ssa.domain.rental.RepositoryBasedRentalService;
import com.ssa.domain.rental.ports.CartEntryRepository;
import com.ssa.domain.rental.ports.ReservationPreparationSDK;
import com.ssa.domain.rental.ports.ReservationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RentalServiceConfiguration {
    @Bean
    public RepositoryBasedRentalService repositoryBasedRentalService(CartEntryRepository cartEntryRepository, ReservationRepository reservationRepository, PaymentService paymentService, NotificationService notificationService, ReservationPreparationSDK reservationPreparationSDK) {
        return new RepositoryBasedRentalService(cartEntryRepository, reservationRepository, paymentService, notificationService, reservationPreparationSDK);
    }
}
