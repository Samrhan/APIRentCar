package com.bader.infrastructure.persitence.rental.configuration;

import com.bader.domain.notification.NotificationService;
import com.bader.domain.payment.PaymentService;
import com.bader.domain.rental.RepositoryBasedRentalService;
import com.bader.domain.rental.ports.CartEntryRepository;
import com.bader.domain.rental.ports.ReservationPreparationSDK;
import com.bader.domain.rental.ports.ReservationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RentalServiceConfiguration {
    @Bean
    public RepositoryBasedRentalService repositoryBasedRentalService(CartEntryRepository cartEntryRepository, ReservationRepository reservationRepository, PaymentService paymentService, NotificationService notificationService, ReservationPreparationSDK reservationPreparationSDK) {
        return new RepositoryBasedRentalService(cartEntryRepository, reservationRepository, paymentService, notificationService, reservationPreparationSDK);
    }
}
