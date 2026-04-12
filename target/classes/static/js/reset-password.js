document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("resetPasswordForm");
    const messageBox = document.getElementById("messageBox");
    const tokenInput = document.getElementById("token");

    const savedToken = localStorage.getItem("resetToken");
    if (savedToken && tokenInput) {
        tokenInput.value = savedToken;
    }

    form.addEventListener("submit", async function (e) {
        e.preventDefault();

        const token = document.getElementById("token").value;
        const newPassword = document.getElementById("newPassword").value;

        messageBox.style.display = "none";
        messageBox.textContent = "";

        try {
            const response = await fetch("/api/auth/reset-password", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ token, newPassword })
            });

            const data = await response.json();

            messageBox.style.display = "block";
            messageBox.textContent = data.message;

            if (response.ok && data.success) {
                localStorage.removeItem("resetToken");
                setTimeout(() => {
                    window.location.href = "/login";
                }, 1500);
            }
        } catch (error) {
            console.error("Reset password error:", error);
            messageBox.style.display = "block";
            messageBox.textContent = "Something went wrong. Please try again.";
        }
    });
});