document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("loginForm");

    form.addEventListener("submit", async function (e) {
        e.preventDefault();

        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;

        const errorMessage = document.getElementById("error-message");
        const errorContainer = document.getElementById("error-container");

        if (errorMessage) errorMessage.textContent = "";
        if (errorContainer) errorContainer.style.display = "none";

        try {
            const response = await fetch("/api/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    email: email,
                    password: password
                })
            });

            const data = await response.json();

            if (response.ok && data.success) {
                localStorage.setItem("fullName", data.fullName);
                localStorage.setItem("role", data.role);
                window.location.href = "/dashboard";
            } else {
                if (errorMessage) errorMessage.textContent = data.message || "Invalid email or password";
                if (errorContainer) errorContainer.style.display = "flex";
            }
        } catch (error) {
            console.error("Login error:", error);
            if (errorMessage) errorMessage.textContent = "Something went wrong. Please try again.";
            if (errorContainer) errorContainer.style.display = "flex";
        }
    });
});

function togglePassword() {
    const passwordInput = document.getElementById("password");
    const toggleIcon = document.getElementById("toggleIcon");

    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        if (toggleIcon) {
            toggleIcon.classList.remove("fa-eye");
            toggleIcon.classList.add("fa-eye-slash");
        }
    } else {
        passwordInput.type = "password";
        if (toggleIcon) {
            toggleIcon.classList.remove("fa-eye-slash");
            toggleIcon.classList.add("fa-eye");
        }
    }
}

function showMessage(message) {
    alert(message);
}