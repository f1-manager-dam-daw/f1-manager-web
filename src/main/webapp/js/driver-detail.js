async function loadDriver() {
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    if (!id) {
        const lastDriverId = sessionStorage.getItem("lastDriverId");
        if (lastDriverId) {
            window.location.replace(`driver-detail.html?id=${encodeURIComponent(lastDriverId)}`);
            return;
        }
        showError("No driver ID provided.");
        return;
    }

    sessionStorage.setItem("lastDriverId", id);

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

        // Fill Edit Modal
        document.getElementById("editDriverId").value = driver.id;
        document.getElementById("editForename").value = driver.forename || "";
        document.getElementById("editSurname").value = driver.surname || "";
        document.getElementById("editCode").value = driver.code || "";
        document.getElementById("editNationality").value = driver.nationality || "";

        loadRelatedConstructors(id);
    } catch (error) {
        showError("Could not load driver details. Please try again later.");
    }
}

async function loadRelatedConstructors(driverId) {
    try {
        const constructors = await apiGet(`/constructors?driverId=${encodeURIComponent(driverId)}`);
        document.getElementById("driverConstructorsLoading").classList.add("d-none");

        if (!constructors || constructors.length === 0) {
            document.getElementById("driverConstructorsEmpty").classList.remove("d-none");
            return;
        }

        const tbody = document.getElementById("driverConstructorsTableBody");
        tbody.innerHTML = "";

        constructors.forEach(constructor => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td><a href="constructor-detail.html?id=${encodeURIComponent(constructor.id)}">${constructor.name || constructor.fullName || constructor.id}</a></td>
                <td>${constructor.nationality || "—"}</td>
                <td>${constructor.totalRaceWins ?? "—"}</td>
                <td>${constructor.totalPodiums ?? "—"}</td>
                <td>${constructor.totalPoints ?? "—"}</td>
            `;
            tbody.appendChild(row);
        });

        document.getElementById("driverConstructorsContainer").classList.remove("d-none");
    } catch (error) {
        document.getElementById("driverConstructorsLoading").textContent = "Could not load related constructors.";
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

async function updateDriver() {
    const driver = {
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
            body: JSON.stringify(driver)
        });

        if (!response.ok) throw new Error(`HTTP Error: ${response.status}`);

        sessionStorage.setItem("lastDriverId", driver.id);
        window.location.replace(`driver-detail.html?id=${encodeURIComponent(driver.id)}`);
    } catch (error) {
        showError("Could not update driver. Please try again later.");
    }
}

const editDriverForm = document.getElementById("editDriverForm");
if (editDriverForm) {
    editDriverForm.addEventListener("submit", function (event) {
        event.preventDefault();
        return false;
    });
}

const updateDriverBtn = document.getElementById("updateDriverBtn");
if (updateDriverBtn) {
    updateDriverBtn.addEventListener("click", updateDriver);
}
