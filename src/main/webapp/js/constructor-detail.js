async function loadConstructor() {
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    if (!id) {
        const lastConstructorId = sessionStorage.getItem("lastConstructorId");
        if (lastConstructorId) {
            window.location.replace(`constructor-detail.html?id=${encodeURIComponent(lastConstructorId)}`);
            return;
        }
        showError("No constructor ID provided.");
        return;
    }

    sessionStorage.setItem("lastConstructorId", id);

    try {
        const constructor = await apiGet(`/constructors?id=${id}`);
        document.getElementById("loadingMsg").classList.add("d-none");
        document.getElementById("constructorDetail").classList.remove("d-none");

        document.getElementById("constructorName").textContent = constructor.name || "—";
        document.getElementById("constructorFullName").textContent = constructor.fullName || "—";
        document.getElementById("constructorNationality").textContent = constructor.nationality || "—";
        document.getElementById("constructorChampionships").textContent = constructor.totalChampionshipWins ?? "—";
        document.getElementById("constructorWins").textContent = constructor.totalRaceWins ?? "—";
        document.getElementById("constructorPodiums").textContent = constructor.totalPodiums ?? "—";
        document.getElementById("constructorPoints").textContent = constructor.totalPoints ?? "—";

        // Fill Edit Modal
        document.getElementById("editConstructorId").value = constructor.id;
        document.getElementById("editConstructorName").value = constructor.name || "";
        document.getElementById("editConstructorFullName").value = constructor.fullName || "";
        document.getElementById("editConstructorNationality").value = constructor.nationality || "";

        loadRelatedDrivers(id);
    } catch (error) {
        showError("Could not load constructor details. Please try again later.");
    }
}

async function loadRelatedDrivers(constructorId) {
    try {
        const drivers = await apiGet(`/drivers?constructorId=${encodeURIComponent(constructorId)}`);
        document.getElementById("constructorDriversLoading").classList.add("d-none");

        if (!drivers || drivers.length === 0) {
            document.getElementById("constructorDriversEmpty").classList.remove("d-none");
            return;
        }

        const tbody = document.getElementById("constructorDriversTableBody");
        tbody.innerHTML = "";

        drivers.forEach(driver => {
            const fullName = `${driver.forename || ""} ${driver.surname || ""}`.trim() || driver.id;
            const row = document.createElement("tr");
            row.innerHTML = `
                <td><a href="driver-detail.html?id=${encodeURIComponent(driver.id)}">${fullName}</a></td>
                <td>${driver.code || "—"}</td>
                <td>${driver.nationality || "—"}</td>
                <td>${driver.totalRaceWins ?? "—"}</td>
                <td>${driver.totalPodiums ?? "—"}</td>
                <td>${driver.totalPoints ?? "—"}</td>
            `;
            tbody.appendChild(row);
        });

        document.getElementById("constructorDriversContainer").classList.remove("d-none");
    } catch (error) {
        document.getElementById("constructorDriversLoading").textContent = "Could not load related drivers.";
    }
}

function showError(message) {
    document.getElementById("loadingMsg").classList.add("d-none");
    const errorMsg = document.getElementById("errorMsg");
    errorMsg.classList.remove("d-none");
    errorMsg.textContent = message;
}

loadConstructor();

const deleteBtn = document.getElementById("deleteBtn");
if (deleteBtn) {
    deleteBtn.addEventListener("click", async function () {
        const params = new URLSearchParams(window.location.search);
        const id = params.get("id");

        if (!confirm("Are you sure you want to delete this constructor? This action cannot be undone.")) return;

        try {
            const response = await fetch(`${API_BASE_URL}/constructors?id=${id}`, {
                method: "DELETE"
            });

            if (!response.ok) throw new Error(`HTTP Error: ${response.status}`);

            window.location.href = "constructors.html";
        } catch (error) {
            showError("Could not delete constructor. Please try again later.");
        }
    });
}


async function updateConstructor() {
    const constructor = {
        id: document.getElementById("editConstructorId").value,
        name: document.getElementById("editConstructorName").value,
        fullName: document.getElementById("editConstructorFullName").value,
        nationality: document.getElementById("editConstructorNationality").value
    };

    try {
        const response = await fetch(`${API_BASE_URL}/constructors`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(constructor)
        });

        if (!response.ok) throw new Error(`HTTP Error: ${response.status}`);

        sessionStorage.setItem("lastConstructorId", constructor.id);
        window.location.replace(`constructor-detail.html?id=${encodeURIComponent(constructor.id)}`);
    } catch (error) {
        showError("Could not update constructor. Please try again later.");
    }
}

const editConstructorForm = document.getElementById("editConstructorForm");
if (editConstructorForm) {
    editConstructorForm.addEventListener("submit", function (event) {
        event.preventDefault();
        return false;
    });
}

const updateConstructorBtn = document.getElementById("updateConstructorBtn");
if (updateConstructorBtn) {
    updateConstructorBtn.addEventListener("click", updateConstructor);
}
