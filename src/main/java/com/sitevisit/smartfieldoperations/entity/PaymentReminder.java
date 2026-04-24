package com.sitevisit.smartfieldoperations.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "payment_reminders")
public class PaymentReminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDate paymentDate;

    private String recipientEmail;

    @Column(length = 1000)
    private String message;

    private boolean paid = false;

    private LocalDate paidDate;

    private LocalDate lastReminderSentDate;

    public PaymentReminder() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public String getMessage() {
        return message;
    }

    public boolean isPaid() {
        return paid;
    }

    public LocalDate getPaidDate() {
        return paidDate;
    }

    public LocalDate getLastReminderSentDate() {
        return lastReminderSentDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void setPaidDate(LocalDate paidDate) {
        this.paidDate = paidDate;
    }

    public void setLastReminderSentDate(LocalDate lastReminderSentDate) {
        this.lastReminderSentDate = lastReminderSentDate;
    }
}