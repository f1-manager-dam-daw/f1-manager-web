const PAGE_SIZE = 20;
let currentPage = 1;
let allDrivers = [];
let filteredDrivers = [];

async function loadDrivers() {
    try {
        allDrivers = await apiGet("/drivers");
        
        const query = document.getElementById("searchInput").value.toLowerCase();
        if (query) {
            filteredDrivers = allDrivers.filter(d =>
                (d.forename + " " + d.surname).toLowerCase().includes(query) ||
                (d.code || "").toLowerCase().includes(query) ||
                (d.nationality || "").toLowerCase().includes(query)
            );
        } else {
            filteredDrivers = allDrivers;
        }
        
        const totalPages = Math.ceil(filteredDrivers.length / PAGE_SIZE);
        if (currentPage > totalPages) currentPage = totalPages || 1;

        document.getElementById("loadingMsg").classList.add("d-none");
        document.getElementById("driversTableContainer").classList.remove("d-none");
        renderTable();
        renderPagination();
    } catch (error) {
        document.getElementById("loadingMsg").classList.add("d-none");
        const errorMsg = document.getElementById("errorMsg");
        errorMsg.classList.remove("d-none");
        errorMsg.textContent = "Could not load drivers. Please try again later.";
    }
}

function renderTable() {
    const tbody = document.getElementById("driversTableBody");
    tbody.innerHTML = "";
    const start = (currentPage - 1) * PAGE_SIZE;
    const pageDrivers = filteredDrivers.slice(start, start + PAGE_SIZE);

    if (pageDrivers.length === 0) {
        tbody.innerHTML = `<tr><td colspan="7" class="text-center text-muted">No drivers found.</td></tr>`;
        return;
    }

    pageDrivers.forEach(driver => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${driver.code || "—"}</td>
            <td>${driver.forename} ${driver.surname}</td>
            <td>${driver.nationality || "—"}</td>
            <td>${driver.number || "—"}</td>
            <td>${driver.totalRaceWins ?? "—"}</td>
            <td>${driver.totalPoints ?? "—"}</td>
            <td>
                <a href="driver-detail.html?id=${driver.id}" class="btn btn-sm btn-danger">View</a>
                <button class="btn btn-sm btn-outline-danger" onclick="deleteDriver('${driver.id}')">Delete</button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

function renderPagination() {
    const container = document.getElementById("paginationContainer");
    container.innerHTML = "";
    const totalPages = Math.ceil(filteredDrivers.length / PAGE_SIZE);
    if (totalPages <= 1) return;

    const createPageItem = (text, page, disabled, active) => {
        const li = document.createElement("li");
        li.className = `page-item ${disabled ? "disabled" : ""} ${active ? "active" : ""}`;
        li.innerHTML = `<a class="page-link" href="#">${text}</a>`;
        if (!disabled && !active) {
            li.addEventListener("click", (e) => {
                e.preventDefault();
                currentPage = page;
                renderTable();
                renderPagination();
            });
        } else {
            li.addEventListener("click", (e) => e.preventDefault());
        }
        return li;
    };

    container.appendChild(createPageItem("Prev", currentPage - 1, currentPage === 1, false));

    let startPage = Math.max(1, currentPage - 2);
    let endPage = Math.min(totalPages, currentPage + 2);
    if (startPage === 1) endPage = Math.min(totalPages, 5);
    if (endPage === totalPages) startPage = Math.max(1, totalPages - 4);

    for (let i = startPage; i <= endPage; i++) {
        container.appendChild(createPageItem(i, i, false, i === currentPage));
    }

    container.appendChild(createPageItem("Next", currentPage + 1, currentPage === totalPages, false));
}

document.getElementById("searchInput").addEventListener("input", function () {
    const query = this.value.toLowerCase();
    filteredDrivers = allDrivers.filter(d =>
        (d.forename + " " + d.surname).toLowerCase().includes(query) ||
        (d.code || "").toLowerCase().includes(query) ||
        (d.nationality || "").toLowerCase().includes(query)
    );
    currentPage = 1;
    renderTable();
    renderPagination();
});

document.getElementById("addDriverForm").addEventListener("submit", async function(e) {
    e.preventDefault();
    const newDriver = {
        id: document.getElementById("driverId").value,
        forename: document.getElementById("driverForename").value,
        surname: document.getElementById("driverSurname").value,
        code: document.getElementById("driverCode").value,
        nationality: document.getElementById("driverNationality").value
    };

    try {
        const response = await fetch(`${API_BASE_URL}/drivers`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(newDriver)
        });

        if (response.ok) {
            // Close modal
            const modal = bootstrap.Modal.getInstance(document.getElementById("addDriverModal"));
            modal.hide();
            // Reset form
            this.reset();
            // Reload data
            loadDrivers();
            alert("Driver added successfully!");
        } else {
            const err = await response.json();
            alert("Error adding driver: " + err.error);
        }
    } catch (error) {
        alert("Network error while adding driver.");
    }
});

async function deleteDriver(id) {
    if (!confirm(`Are you sure you want to delete driver ${id}?`)) {
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/drivers?id=${id}`, {
            method: "DELETE"
        });

        if (response.ok) {
            loadDrivers();
            alert("Driver deleted successfully!");
        } else {
            const err = await response.json();
            alert("Error deleting driver: " + err.error);
        }
    } catch (error) {
        alert("Network error while deleting driver.");
    }
}

// Make deleteDriver globally accessible since it's used in inline onclick handlers (or we can attach it in renderTable)
window.deleteDriver = deleteDriver;

loadDrivers();