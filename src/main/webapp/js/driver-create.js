document.getElementById("submitBtn").addEventListener("click", async function () {
    const id = document.getElementById("id").value.trim();
    const forename = document.getElementById("forename").value.trim();
    const surname = document.getElementById("surname").value.trim();
    const nationality = document.getElementById("nationality").value.trim();

    if (!id || !forename || !surname || !nationality) {
        showError("Please fill in all required fields.");
        return;
    }

    const driver = {
        id: id,
        forename: forename,
        surname: surname,
        code: document.getElementById("code").value.trim() || null,
        number: document.getElementById("number").value || null,
        nationality: nationality,
        dateOfBirth: document.getElementById("dateOfBirth").value || null
    };

    try {
        const response = await fetch(`${API_BASE_URL}/drivers`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(driver)
        });

        if (!response.ok) throw new Error(`HTTP Error: ${response.status}`);

        document.getElementById("errorMsg").classList.add("d-none");
        document.getElementById("successMsg").classList.remove("d-none");
        document.getElementById("submitBtn").disabled = true;

        setTimeout(() => {
            window.location.href = "drivers.html";
        }, 1500);

    } catch (error) {
        showError("Could not create driver. Please try again later.");
    }
});

function showError(message) {
    const errorMsg = document.getElementById("errorMsg");
    errorMsg.classList.remove("d-none");
    errorMsg.textContent = message;
}