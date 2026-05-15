async function loadConstructor() {
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    if (!id) {
        showError("No constructor ID provided.");
        return;
    }

    try {
        const constructor = await apiGet(`/constructors?id=${id}`);
        document.getElementById("loadingMsg").classList.add("d-none");
        document.getElementById("constructorDetail").classList.remove("d-none");

        document.getElementById("constructorName").textContent = constructor.name || "—";
        document.getElementById("constructorFullName").textContent = constructor.fullName || "—";
        document.getElementById("constructorNationality").textContent = constructor.nationality || "—";
        document.getElementById("constructorChampionships").textContent = constructor.totalChampionshipWins ?? "—";
        document.getElementById("constructorWins").textContent = constructor.totalRaceWins ?? "—";
        document.getElementById("constructorPodiums").textContent = constructor.totalPodiums ?? "—";
        document.getElementById("constructorPoints").textContent = constructor.totalPoints ?? "—";

        // Fill Edit Modal
        document.getElementById("editConstructorId").value = constructor.id;
        document.getElementById("editConstructorName").value = constructor.name || "";
        document.getElementById("editConstructorFullName").value = constructor.fullName || "";
        document.getElementById("editConstructorNationality").value = constructor.nationality || "";
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

document.getElementById("deleteBtn").addEventListener("click", async function () {
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    if (!confirm("Are you sure you want to delete this constructor? This action cannot be undone.")) return;

    try {
        const response = await fetch(`${API_BASE_URL}/constructors?id=${id}`, {
            method: "DELETE"
        });

        if (!response.ok) throw new Error(`HTTP Error: ${response.status}`);

        window.location.href = "constructors.html";
    } catch (error) {
        showError("Could not delete constructor. Please try again later.");
    }
});
