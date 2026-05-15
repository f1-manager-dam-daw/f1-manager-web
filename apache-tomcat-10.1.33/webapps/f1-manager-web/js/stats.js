async function loadStats() {
    try {
        const stats = await apiGet("/stats/summary");
        document.getElementById("loadingMsg").classList.add("d-none");
        document.getElementById("statsContainer").classList.remove("d-none");

        document.getElementById("totalDrivers").textContent = stats.totalDrivers ?? "—";
        document.getElementById("totalConstructors").textContent = stats.totalConstructors ?? "—";
        document.getElementById("totalRaces").textContent = stats.totalRaces ?? "—";
        document.getElementById("seasonsRange").textContent =
            stats.firstSeason && stats.lastSeason
                ? `${stats.firstSeason} – ${stats.lastSeason}`
                : "—";

        renderTopDrivers(stats.topDrivers || []);
        renderTopConstructors(stats.topConstructors || []);
    } catch (error) {
        document.getElementById("loadingMsg").classList.add("d-none");
        const errorMsg = document.getElementById("errorMsg");
        errorMsg.classList.remove("d-none");
        errorMsg.textContent = "Could not load statistics. Please try again later.";
    }
}

function renderTopDrivers(drivers) {
    const tbody = document.getElementById("topDriversBody");
    tbody.innerHTML = "";
    if (drivers.length === 0) {
        tbody.innerHTML = `<tr><td colspan="4" class="text-center text-muted">No data available.</td></tr>`;
        return;
    }
    drivers.forEach((driver, index) => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${index + 1}</td>
            <td>${driver.forename} ${driver.surname}</td>
            <td>${driver.nationality || "—"}</td>
            <td>${driver.total_race_wins ?? "—"}</td>
        `;
        tbody.appendChild(row);
    });
}

function renderTopConstructors(constructors) {
    const tbody = document.getElementById("topConstructorsBody");
    tbody.innerHTML = "";
    if (constructors.length === 0) {
        tbody.innerHTML = `<tr><td colspan="4" class="text-center text-muted">No data available.</td></tr>`;
        return;
    }
    constructors.forEach((constructor, index) => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${index + 1}</td>
            <td>${constructor.name || "—"}</td>
            <td>${constructor.nationality || "—"}</td>
            <td>${constructor.total_race_wins ?? "—"}</td>
        `;
        tbody.appendChild(row);
    });
}

loadStats();