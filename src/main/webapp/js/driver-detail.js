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
        document.getElementById("driverDob").textContent = driver.date_of_birth || "—";
        document.getElementById("driverPoints").textContent = driver.total_points ?? "—";
        document.getElementById("driverWins").textContent = driver.total_race_wins ?? "—";
        document.getElementById("driverPodiums").textContent = driver.total_podiums ?? "—";

        // Fill Edit Modal
        document.getElementById("editDriverId").value = driver.id;
        document.getElementById("editForename").value = driver.forename || "";
        document.getElementById("editSurname").value = driver.surname || "";
        document.getElementById("editCode").value = driver.code || "";
        document.getElementById("editNationality").value = driver.nationality || "";
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

document.getElementById("editDriverForm").addEventListener("submit", async function(e) {
    e.preventDefault();
    const updatedDriver = {
        id: document.getElementById("editDriverId").value,
        forename: document.getElementById("editForename").value,
        surname: document.getElementById("editSurname").value,
        code: document.getElementById("editCode").value,
        nationality: document.getElementById("editNationality").value
    };

    try {
        const response = await fetch(`${API_BASE_URL}/drivers`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(updatedDriver)
        });

        if (response.ok) {
            const modal = bootstrap.Modal.getInstance(document.getElementById("editDriverModal"));
            modal.hide();
            loadDriver();
            alert("Driver updated successfully!");
        } else {
            const err = await response.json();
            alert("Error updating driver: " + err.error);
        }
    } catch (error) {
        alert("Network error while updating driver.");
    }
});

loadDriver();