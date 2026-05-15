const PAGE_SIZE = 20;
let currentPage = 1;
let allRaces = [];
let filteredRaces = [];

async function loadRaces() {
    try {
        allRaces = await apiGet("/races");
        filteredRaces = allRaces;
        document.getElementById("loadingMsg").classList.add("d-none");
        document.getElementById("racesTableContainer").classList.remove("d-none");
        populateYearFilter();
        renderTable();
        renderPagination();
    } catch (error) {
        document.getElementById("loadingMsg").classList.add("d-none");
        const errorMsg = document.getElementById("errorMsg");
        errorMsg.classList.remove("d-none");
        errorMsg.textContent = "Could not load races. Please try again later.";
    }
}

function populateYearFilter() {
    const select = document.getElementById("yearFilter");
    const years = [...new Set(allRaces.map(r => r.year))].sort((a, b) => b - a);
    years.forEach(year => {
        const option = document.createElement("option");
        option.value = year;
        option.textContent = year;
        select.appendChild(option);
    });
}

function renderTable() {
    const tbody = document.getElementById("racesTableBody");
    tbody.innerHTML = "";
    const start = (currentPage - 1) * PAGE_SIZE;
    const pageRaces = filteredRaces.slice(start, start + PAGE_SIZE);

    if (pageRaces.length === 0) {
        tbody.innerHTML = `<tr><td colspan="7" class="text-center text-muted">No races found.</td></tr>`;
        return;
    }

    pageRaces.forEach(race => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${race.year || "—"}</td>
            <td>${race.round || "—"}</td>
            <td>${race.name || race.official_name || "—"}</td>
            <td>${race.circuit_name || "—"}</td>
            <td>${race.date || "—"}</td>
            <td>${race.laps || "—"}</td>
            <td><a href="race-detail.html?id=${race.id}" class="btn btn-sm btn-danger">View</a></td>
        `;
        tbody.appendChild(row);
    });
}

function renderPagination() {
    const container = document.getElementById("paginationContainer");
    container.innerHTML = "";
    const totalPages = Math.ceil(filteredRaces.length / PAGE_SIZE);
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

document.getElementById("yearFilter").addEventListener("change", function () {
    const year = this.value;
    filteredRaces = year ? allRaces.filter(r => r.year == year) : allRaces;
    currentPage = 1;
    renderTable();
    renderPagination();
});

loadRaces();