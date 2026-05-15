document.getElementById("submitBtn").addEventListener("click", async function () {
    const id = document.getElementById("id").value.trim();
    const name = document.getElementById("name").value.trim();
    const nationality = document.getElementById("nationality").value.trim();

    if (!id || !name || !nationality) {
        showError("Please fill in all required fields.");
        return;
    }

    const constructor = {
        id: id,
        name: name,
        fullName: document.getElementById("fullName").value.trim() || null,
        nationality: nationality
    };

    try {
        const response = await fetch(`${API_BASE_URL}/constructors`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(constructor)
        });

        if (!response.ok) throw new Error(`HTTP Error: ${response.status}`);

        document.getElementById("errorMsg").classList.add("d-none");
        document.getElementById("successMsg").classList.remove("d-none");
        document.getElementById("submitBtn").disabled = true;

        setTimeout(() => {
            window.location.href = "constructors.html";
        }, 1500);

    } catch (error) {
        showError("Could not create constructor. Please try again later.");
    }
});

function showError(message) {
    const errorMsg = document.getElementById("errorMsg");
    errorMsg.classList.remove("d-none");
    errorMsg.textContent = message;
}