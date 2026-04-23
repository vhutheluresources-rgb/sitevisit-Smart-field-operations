const API = "http://localhost:8080/api/notifications";

// Toggle notification panel
function toggleNotifications() {
    const panel = document.getElementById("notification-panel");
    panel.classList.toggle("hidden");
}

// Load notifications from backend
async function loadNotifications() {
    try {
        const res = await fetch(API);
        const data = await res.json();

        const list = document.getElementById("notification-list");
        list.innerHTML = "";

        let unreadCount = 0;

        data.forEach(n => {
            const li = document.createElement("li");
            li.className = "notification " + (!n.read ? "unread" : "");

            li.innerHTML = `
                <p>${n.message}</p>
                <span>${formatTime(n.createdAt)}</span>
            `;

            // Mark as read on click
            li.onclick = async () => {
                await fetch(`${API}/${n.id}/read`, { method: "PUT" });
                loadNotifications();
            };

            if (!n.read) unreadCount++;

            list.appendChild(li);
        });

        document.getElementById("notif-count").innerText = unreadCount;

    } catch (err) {
        console.error("Error loading notifications:", err);
    }
}

// Format time nicely
function formatTime(dateString) {
    const date = new Date(dateString);
    return date.toLocaleString();
}

// Auto refresh every 5 seconds
setInterval(loadNotifications, 5000);

// Load when page starts
document.addEventListener("DOMContentLoaded", loadNotifications);