const PAGE_SIZE = 20;
let currentPage = 1;
let allConstructors = [];
let filteredConstructors = [];

async function loadConstructors() {
    try {
        allConstructors = await apiGet("/constructors");
        filteredConstructors = allConstructors;
        document.getElementById("loadingMsg").classList.add("d-none");
        document.getElementById("constructorsTableContainer").classList.remove("d-none");
        renderTable();
        renderPagination();
    } catch (error) {
        document.getElementById("loadingMsg").classList.add("d-none");
        const errorMsg = document.getElementById("errorMsg");
        errorMsg.classList.remove("d-none");
        errorMsg.textContent = "Could not load constructors. Please try again later.";
    }
}

function renderTable() {
    const tbody = document.getElementById("constructorsTableBody");
    tbody.innerHTML = "";
    const start = (currentPage - 1) * PAGE_SIZE;
    const pageConstructors = filteredConstructors.slice(start, start + PAGE_SIZE);

    if (pageConstructors.length === 0) {
        tbody.innerHTML = `<tr><td colspan="7" class="text-center text-muted">No constructors found.</td></tr>`;
        return;
    }

    pageConstructors.forEach(constructor => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${constructor.name || "—"}</td>
            <td>${constructor.full_name || "—"}</td>
            <td>${constructor.nationality || "—"}</td>
            <td>${constructor.totalChampionshipWins ?? "—"}</td>
            <td>${constructor.totalRaceWins ?? "—"}</td>
            <td>${constructor.totalPoints ?? "—"}</td>
            <td><a href="constructor-detail.html?id=${constructor.id}" class="btn btn-sm btn-danger">View</a></td>
        `;
        tbody.appendChild(row);
    });
}

function renderPagination() {
    const container = document.getElementById("paginationContainer");
    container.innerHTML = "";
    const totalPages = Math.ceil(filteredConstructors.length / PAGE_SIZE);
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
    filteredConstructors = allConstructors.filter(c =>
        (c.name || "").toLowerCase().includes(query) ||
        (c.full_name || "").toLowerCase().includes(query) ||
        (c.nationality || "").toLowerCase().includes(query)
    );
    currentPage = 1;
    renderTable();
    renderPagination();
});

loadConstructors();