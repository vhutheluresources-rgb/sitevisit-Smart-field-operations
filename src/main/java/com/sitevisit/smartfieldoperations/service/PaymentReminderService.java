package com.sitevisit.smartfieldoperations.service;

import com.sitevisit.smartfieldoperations.entity.PaymentReminder;
import com.sitevisit.smartfieldoperations.repository.PaymentReminderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentReminderService {

    private final PaymentReminderRepository paymentReminderRepository;
    private final EmailService emailService;

    public PaymentReminderService(PaymentReminderRepository paymentReminderRepository,
                                  EmailService emailService) {
        this.paymentReminderRepository = paymentReminderRepository;
        this.emailService = emailService;
    }

    public List<PaymentReminder> getAllReminders() {
        return paymentReminderRepository.findAll();
    }

    public PaymentReminder createReminder(PaymentReminder reminder) {
        reminder.setPaid(false);
        return paymentReminderRepository.save(reminder);
    }

    public PaymentReminder markAsPaid(Long id) {
        PaymentReminder reminder = paymentReminderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment reminder not found"));

        reminder.setPaid(true);
        reminder.setPaidDate(LocalDate.now());

        return paymentReminderRepository.save(reminder);
    }

    public void checkAndSendReminders() {
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysFromNow = today.plusDays(7);

        List<PaymentReminder> reminders = paymentReminderRepository.findAll();

        for (PaymentReminder reminder : reminders) {
            boolean dueSoon = !reminder.getPaymentDate().isBefore(today)
                    && !reminder.getPaymentDate().isAfter(sevenDaysFromNow);

            boolean overdue = reminder.getPaymentDate().isBefore(today);

            boolean alreadySentToday = today.equals(reminder.getLastReminderSentDate());

            if (!reminder.isPaid() && (dueSoon || overdue) && !alreadySentToday) {
                String subject = overdue
                        ? "Overdue Stipend Payment Reminder"
                        : "Upcoming Stipend Payment Reminder";

                String message = reminder.getMessage();

                if (message == null || message.isBlank()) {
                    message = overdue
                            ? "The stipend payment is overdue. Please process the payment as soon as possible."
                            : "The stipend payment is due within 7 days. Please ensure it is processed on time.";
                }

                emailService.sendEmail(
                        reminder.getRecipientEmail(),
                        subject,
                        message
                );

                reminder.setLastReminderSentDate(today);
                paymentReminderRepository.save(reminder);
            }
        }
    }
}