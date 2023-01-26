package com.bader.infrastructure.persitence.rental.service;

import com.bader.domain.notification.NotificationService;
import com.bader.domain.payment.PaymentService;
import com.bader.domain.rental.RepositoryBasedRentalService;
import com.bader.domain.rental.repository.CartEntryRepository;
import com.bader.domain.rental.repository.ReservationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RentalServiceConfiguration {
    @Bean
    public RepositoryBasedRentalService repositoryBasedRentalService(CartEntryRepository cartEntryRepository, ReservationRepository reservationRepository, PaymentService paymentService, NotificationService notificationService) {
        return new RepositoryBasedRentalService(cartEntryRepository, reservationRepository, paymentService, notificationService);
    }
}
