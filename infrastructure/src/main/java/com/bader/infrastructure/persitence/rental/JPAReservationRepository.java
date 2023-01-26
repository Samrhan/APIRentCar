package com.bader.infrastructure.persitence.rental;

import com.bader.infrastructure.persitence.IAM.entity.CustomerEntity;
import com.bader.infrastructure.persitence.catalog.entity.CarEntity;
import com.bader.infrastructure.persitence.rental.entity.ReservationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JPAReservationRepository extends CrudRepository<ReservationEntity, UUID> {

    @Query("SELECT reservation FROM ReservationEntity reservation WHERE reservation.customer = :customer AND reservation.startDate >= :searchStartDate")
    Optional<List<ReservationEntity>> findAllForCustomerAfter(@Param("customer") CustomerEntity customer, @Param("searchStartDate") Date searchStartDate);

    @Query("SELECT reservation FROM ReservationEntity reservation WHERE reservation.customer = :customer AND reservation.startDate <= :searchEndDate")
    Optional<List<ReservationEntity>> findAllForCustomerBefore(@Param("customer") CustomerEntity customer, @Param("searchEndDate") Date searchEndDate);

    @Query("SELECT reservation FROM ReservationEntity reservation WHERE reservation.car = :car AND reservation.startDate <= :searchEndDate AND :searchStartDate <= reservation.endDate")
    Optional<List<ReservationEntity>> findAllForCarBetween(@Param("car") CarEntity carEntity, @Param("searchStartDate") Date searchStartDate, @Param("searchEndDate") Date searchEndDate);
}
