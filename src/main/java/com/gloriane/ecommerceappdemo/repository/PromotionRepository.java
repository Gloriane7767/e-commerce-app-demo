package com.gloriane.ecommerceappdemo.repository;

import com.gloriane.ecommerceappdemo.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    List<Promotion> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(
            LocalDate date1, LocalDate date2
    );
    List<Promotion> findByStartDateAfter(LocalDate date);
    List<Promotion> findByEndDateBefore(LocalDate date);
    List<Promotion> findByEndDateIsNull();
}
