const PAGE_SIZE = 20;
let currentPage = 1;
let allDrivers = [];
let filteredDrivers = [];

async function loadDrivers() {
    try {
        allDrivers = await apiGet("/drivers");
        filteredDrivers = allDrivers;
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

    const ul = document.createElement("ul");
    ul.className = "pagination";

    const addPage = (label, page, disabled = false, active = false) => {
        const li = document.createElement("li");
        li.className = `page-item ${disabled ? "disabled" : ""} ${active ? "active" : ""}`;
        li.innerHTML = `<a class="page-link" href="#">${label}</a>`;
        if (!disabled) {
            li.addEventListener("click", (e) => {
                e.preventDefault();
                currentPage = page;
                renderTable();
                renderPagination();
            });
        }
        ul.appendChild(li);
    };

    addPage("«", currentPage - 1, currentPage === 1);

    const delta = 2;
    const range = [];
    for (let i = Math.max(1, currentPage - delta); i <= Math.min(totalPages, currentPage + delta); i++) {
        range.push(i);
    }

    if (range[0] > 1) {
        addPage(1, 1);
        if (range[0] > 2) addPage("...", null, true);
    }

    range.forEach(i => addPage(i, i, false, i === currentPage));

    if (range[range.length - 1] < totalPages) {
        if (range[range.length - 1] < totalPages - 1) addPage("...", null, true);
        addPage(totalPages, totalPages);
    }

    addPage("»", currentPage + 1, currentPage === totalPages);

    container.appendChild(ul);
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

async function deleteDriver(id) {
    if (!confirm(`Are you sure you want to delete driver ${id}?`)) return;

    try {
        const response = await fetch(`${API_BASE_URL}/drivers?id=${id}`, {
            method: "DELETE"
        });

        if (response.ok) {
            loadDrivers();
        } else {
            const err = await response.json();
            alert("Error deleting driver: " + err.error);
        }
    } catch (error) {
        alert("Network error while deleting driver.");
    }
}

window.deleteDriver = deleteDriver;

loadDrivers();