const API_URL = "/api/payment-reminders";

document.addEventListener("DOMContentLoaded", () => {
    fetchPaymentReminders();

    const form = document.getElementById("paymentReminderForm");

    if (form) {
        form.addEventListener("submit", async (e) => {
            e.preventDefault();

            const reminder = {
                title: document.getElementById("title").value.trim(),
                paymentDate: document.getElementById("paymentDate").value,
                recipientEmail: document.getElementById("recipientEmail").value.trim(),
                message: document.getElementById("message").value.trim()
            };

            if (!reminder.title || !reminder.paymentDate || !reminder.recipientEmail) {
                alert("Please fill in title, payment date, and recipient email.");
                return;
            }

            try {
                const response = await fetch(API_URL, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(reminder)
                });

                if (!response.ok) {
                    throw new Error("Failed to save reminder");
                }

                alert("Payment reminder saved successfully.");
                form.reset();
                fetchPaymentReminders();

            } catch (error) {
                console.error(error);
                alert("Failed to save payment reminder.");
            }
        });
    }
});

async function fetchPaymentReminders() {
    const tbody = document.getElementById("paymentReminderTableBody");

    if (!tbody) return;

    tbody.innerHTML = `
        <tr>
            <td colspan="6" style="text-align:center;">Loading reminders...</td>
        </tr>
    `;

    try {
        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error("Failed to load reminders");
        }

        const reminders = await response.json();

        tbody.innerHTML = "";

        if (reminders.length === 0) {
            tbody.innerHTML = `
                <tr>
                    <td colspan="6" style="text-align:center;">No payment reminders yet.</td>
                </tr>
            `;
            return;
        }

        reminders.forEach(reminder => {
            const row = document.createElement("tr");

            const status = getReminderStatus(reminder);

            row.innerHTML = `
                <td>${escapeHtml(reminder.title)}</td>
                <td>${reminder.paymentDate || "-"}</td>
                <td>${escapeHtml(reminder.recipientEmail)}</td>
                <td>${status}</td>
                <td>${reminder.lastReminderSentDate || "-"}</td>
                <td>
                    ${
                reminder.paid
                    ? `<span class="status completed">Paid</span>`
                    : `<button class="btn btn-primary small-btn" onclick="markAsPaid(${reminder.id})">Mark as Paid</button>`
            }
                </td>
            `;

            tbody.appendChild(row);
        });

    } catch (error) {
        console.error(error);
        tbody.innerHTML = `
            <tr>
                <td colspan="6" style="text-align:center; color:red;">Failed to load reminders.</td>
            </tr>
        `;
    }
}

function getReminderStatus(reminder) {
    if (reminder.paid) {
        return `<span class="status completed">Paid</span>`;
    }

    const today = new Date();
    today.setHours(0, 0, 0, 0);

    const paymentDate = new Date(reminder.paymentDate);
    paymentDate.setHours(0, 0, 0, 0);

    const diffTime = paymentDate - today;
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

    if (diffDays < 0) {
        return `<span class="status pending">Overdue</span>`;
    }

    if (diffDays <= 7) {
        return `<span class="status progress">Due Soon</span>`;
    }

    return `<span class="status completed">Upcoming</span>`;
}

async function markAsPaid(id) {
    if (!confirm("Mark this stipend payment as paid?")) return;

    try {
        const response = await fetch(`${API_URL}/${id}/paid`, {
            method: "PUT"
        });

        if (!response.ok) {
            throw new Error("Failed to mark as paid");
        }

        alert("Payment marked as paid.");
        fetchPaymentReminders();

    } catch (error) {
        console.error(error);
        alert("Failed to mark payment as paid.");
    }
}

async function sendReminderCheckNow() {
    try {
        const response = await fetch(`${API_URL}/send-now`, {
            method: "POST"
        });

        if (!response.ok) {
            throw new Error("Failed to run reminder check");
        }

        alert("Reminder check completed. Check console/email output.");
        fetchPaymentReminders();

    } catch (error) {
        console.error(error);
        alert("Failed to send reminder check.");
    }
}

function escapeHtml(text) {
    if (!text) return "";
    const div = document.createElement("div");
    div.textContent = text;
    return div.innerHTML;
}