async function loadDriver() {
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    if (!id) {
        showError("No driver ID provided.");
        return;
    }

    try {
        const driver = await apiGet(`/drivers?id=${id}`);
        document.getElementById("loadingMsg").classList.add("d-none");
        document.getElementById("driverDetail").classList.remove("d-none");

        document.getElementById("driverName").textContent = `${driver.forename} ${driver.surname}`;
        document.getElementById("driverCode").textContent = driver.code || "—";
        document.getElementById("driverNumber").textContent = driver.number || "—";
        document.getElementById("driverNationality").textContent = driver.nationality || "—";
        document.getElementById("driverDob").textContent = driver.dateOfBirth || "—";
        document.getElementById("driverPoints").textContent = driver.totalPoints ?? "—";
        document.getElementById("driverWins").textContent = driver.totalRaceWins ?? "—";
        document.getElementById("driverPodiums").textContent = driver.totalPodiums ?? "—";
    } catch (error) {
        showError("Could not load driver details. Please try again later.");
    }
}

function showError(message) {
    document.getElementById("loadingMsg").classList.add("d-none");
    const errorMsg = document.getElementById("errorMsg");
    errorMsg.classList.remove("d-none");
    errorMsg.textContent = message;
}

loadDriver();

document.getElementById("deleteBtn").addEventListener("click", async function () {
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    if (!confirm("Are you sure you want to delete this driver? This action cannot be undone.")) return;

    try {
        const response = await fetch(`${API_BASE_URL}/drivers?id=${id}`, {
            method: "DELETE"
        });

        if (!response.ok) throw new Error(`HTTP Error: ${response.status}`);

        window.location.href = "drivers.html";
    } catch (error) {
        showError("Could not delete driver. Please try again later.");
    }
});