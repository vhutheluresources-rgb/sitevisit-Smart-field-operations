document.getElementById("changePasswordForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const currentPassword = document.getElementById("currentPassword").value.trim();
    const newPassword = document.getElementById("newPassword").value.trim();
    const confirmPassword = document.getElementById("confirmPassword").value.trim();
    const messageBox = document.getElementById("messageBox");

    messageBox.style.display = "none";
    messageBox.textContent = "";
    messageBox.className = "message-box";

    if (newPassword !== confirmPassword) {
        messageBox.style.display = "block";
        messageBox.textContent = "New password and confirm password do not match.";
        messageBox.classList.add("error");
        return;
    }

    if (newPassword.length < 8) {
        messageBox.style.display = "block";
        messageBox.textContent = "New password must be at least 8 characters long.";
        messageBox.classList.add("error");
        return;
    }

    if (currentPassword === newPassword) {
        messageBox.style.display = "block";
        messageBox.textContent = "New password must be different from current password.";
        messageBox.classList.add("error");
        return;
    }

    try {
        const response = await fetch("/api/auth/change-password", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "same-origin",
            body: JSON.stringify({
                currentPassword,
                newPassword,
                confirmPassword
            })
        });

        const data = await response.json();

        messageBox.style.display = "block";
        messageBox.textContent = data.message;

        if (response.ok && data.success) {
            messageBox.classList.add("success");
            document.getElementById("changePasswordForm").reset();

            setTimeout(() => {
                window.location.href = "/login";
            }, 2000);
        } else {
            messageBox.classList.add("error");
        }
    } catch (error) {
        messageBox.style.display = "block";
        messageBox.textContent = "Something went wrong. Please try again.";
        messageBox.classList.add("error");
    }
});