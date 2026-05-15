const PAGE_SIZE = 20;
let currentPage = 1;
let allConstructors = [];
let filteredConstructors = [];

async function loadConstructors() {
    try {
        allConstructors = await apiGet("/constructors");

        const query = document.getElementById("searchInput").value.toLowerCase();
        if (query) {
            filteredConstructors = allConstructors.filter(c =>
                (c.name || "").toLowerCase().includes(query) ||
                (c.full_name || "").toLowerCase().includes(query) ||
                (c.nationality || "").toLowerCase().includes(query)
            );
        } else {
            filteredConstructors = allConstructors;
        }

        const totalPages = Math.ceil(filteredConstructors.length / PAGE_SIZE);
        if (currentPage > totalPages) currentPage = totalPages || 1;

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
            <td>${constructor.fullName || "—"}</td>
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
    filteredConstructors = allConstructors.filter(c =>
        (c.name || "").toLowerCase().includes(query) ||
        (c.full_name || "").toLowerCase().includes(query) ||
        (c.nationality || "").toLowerCase().includes(query)
    );
    currentPage = 1;
    renderTable();
    renderPagination();
});

document.getElementById("addConstructorForm").addEventListener("submit", async function(e) {
    e.preventDefault();
    const newConstructor = {
        id: document.getElementById("constructorId").value,
        name: document.getElementById("constructorNameForm").value,
        fullName: document.getElementById("constructorFullNameForm").value,
        nationality: document.getElementById("constructorNationalityForm").value
    };

    try {
        const response = await fetch(`${API_BASE_URL}/constructors`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(newConstructor)
        });

        if (response.ok) {
            const modal = bootstrap.Modal.getInstance(document.getElementById("addConstructorModal"));
            modal.hide();
            this.reset();
            loadConstructors();
            alert("Constructor added successfully!");
        } else {
            const err = await response.json();
            alert("Error adding constructor: " + err.error);
        }
    } catch (error) {
        alert("Network error while adding constructor.");
    }
});

async function deleteConstructor(id) {
    if (!confirm(`Are you sure you want to delete constructor ${id}?`)) return;

    try {
        const response = await fetch(`${API_BASE_URL}/constructors?id=${id}`, {
            method: "DELETE"
        });

        if (response.ok) {
            loadConstructors();
            alert("Constructor deleted successfully!");
        } else {
            const err = await response.json();
            alert("Error deleting constructor: " + err.error);
        }
    } catch (error) {
        alert("Network error while deleting constructor.");
    }
}

window.deleteConstructor = deleteConstructor;

loadConstructors();