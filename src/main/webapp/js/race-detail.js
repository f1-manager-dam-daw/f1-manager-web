async function loadRace() {
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    if (!id) {
        showError("No race ID provided.");
        return;
    }

    try {
        const race = await apiGet(`/races/${id}`);
        document.getElementById("loadingMsg").classList.add("d-none");
        document.getElementById("raceDetail").classList.remove("d-none");

        document.getElementById("raceName").textContent = race.name || race.official_name || "—";
        document.getElementById("raceYear").textContent = race.year || "—";
        document.getElementById("raceRound").textContent = race.round || "—";
        document.getElementById("raceDate").textContent = race.date || "—";
        document.getElementById("raceCircuit").textContent = race.circuit_name || "—";
        document.getElementById("raceLaps").textContent = race.laps || "—";
        document.getElementById("raceDistance").textContent = race.distance ? `${race.distance} km` : "—";

        loadResults(id);
    } catch (error) {
        showError("Could not load race details. Please try again later.");
    }
}

async function loadResults(raceId) {
    try {
        const results = await apiGet(`/races/${raceId}/results`);
        document.getElementById("resultsLoadingMsg").classList.add("d-none");
        document.getElementById("resultsTableContainer").classList.remove("d-none");

        const tbody = document.getElementById("resultsTableBody");
        tbody.innerHTML = "";

        if (results.length === 0) {
            tbody.innerHTML = `<tr><td colspan="7" class="text-center text-muted">No results available.</td></tr>`;
            return;
        }

        results.forEach(result => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${result.position_text || result.position || "—"}</td>
                <td>${result.driver_id || "—"}</td>
                <td>${result.constructor_id || "—"}</td>
                <td>${result.grid ?? "—"}</td>
                <td>${result.laps ?? "—"}</td>
                <td>${result.points ?? "—"}</td>
                <td>${result.status || "—"}</td>
            `;
            tbody.appendChild(row);
        });
    } catch (error) {
        document.getElementById("resultsLoadingMsg").textContent = "Could not load race results.";
    }
}

function showError(message) {
    document.getElementById("loadingMsg").classList.add("d-none");
    const errorMsg = document.getElementById("errorMsg");
    errorMsg.classList.remove("d-none");
    errorMsg.textContent = message;
}

loadRace();