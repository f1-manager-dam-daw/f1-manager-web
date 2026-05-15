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
            <td><a href="driver-detail.html?id=${driver.id}" class="btn btn-sm btn-danger">View</a></td>
        `;
        tbody.appendChild(row);
    });
}

function renderPagination() {
    const container = document.getElementById("paginationContainer");
    container.innerHTML = "";
    const totalPages = Math.ceil(filteredDrivers.length / PAGE_SIZE);
    if (totalPages <= 1) return;

    for (let i = 1; i <= totalPages; i++) {
        const li = document.createElement("li");
        li.className = `page-item ${i === currentPage ? "active" : ""}`;
        li.innerHTML = `<a class="page-link" href="#">${i}</a>`;
        li.addEventListener("click", (e) => {
            e.preventDefault();
            currentPage = i;
            renderTable();
            renderPagination();
        });
        container.appendChild(li);
    }
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

loadDrivers();