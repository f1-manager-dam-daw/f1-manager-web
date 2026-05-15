async function loadConstructor() {
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    if (!id) {
        showError("No constructor ID provided.");
        return;
    }

    try {
        const constructor = await apiGet(`/constructors/${id}`);
        document.getElementById("loadingMsg").classList.add("d-none");
        document.getElementById("constructorDetail").classList.remove("d-none");

        document.getElementById("constructorName").textContent = constructor.name || "—";
        document.getElementById("constructorFullName").textContent = constructor.full_name || "—";
        document.getElementById("constructorNationality").textContent = constructor.nationality || "—";
        document.getElementById("constructorChampionships").textContent = constructor.total_championship_wins ?? "—";
        document.getElementById("constructorWins").textContent = constructor.total_race_wins ?? "—";
        document.getElementById("constructorPodiums").textContent = constructor.total_podiums ?? "—";
        document.getElementById("constructorPoints").textContent = constructor.total_points ?? "—";
    } catch (error) {
        showError("Could not load constructor details. Please try again later.");
    }
}

function showError(message) {
    document.getElementById("loadingMsg").classList.add("d-none");
    const errorMsg = document.getElementById("errorMsg");
    errorMsg.classList.remove("d-none");
    errorMsg.textContent = message;
}

loadConstructor();