const PAGE_SIZE = 20;
let currentPage = 1;
let allRaces = [];
let filteredRaces = [];

async function loadRaces() {
    try {
        allRaces = await apiGet("/races");

        const year = document.getElementById("yearFilter").value;
        filteredRaces = year ? allRaces.filter(r => r.year == year) : allRaces;

        const totalPages = Math.ceil(filteredRaces.length / PAGE_SIZE);
        if (currentPage > totalPages) currentPage = totalPages || 1;

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

document.getElementById("yearFilter").addEventListener("change", function () {
    const year = this.value;
    filteredRaces = year ? allRaces.filter(r => r.year == year) : allRaces;
    currentPage = 1;
    renderTable();
    renderPagination();
});

loadRaces();