package com.sitevisit.smartfieldoperations.repository;

import com.sitevisit.smartfieldoperations.entity.PaymentReminder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentReminderRepository extends JpaRepository<PaymentReminder, Long> {
}