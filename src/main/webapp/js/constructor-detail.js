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
        document.getElementById("constructorFullName").textContent = constructor.full_name || "—";
        document.getElementById("constructorNationality").textContent = constructor.nationality || "—";
        document.getElementById("constructorChampionships").textContent = constructor.total_championship_wins ?? "—";
        document.getElementById("constructorWins").textContent = constructor.total_race_wins ?? "—";
        document.getElementById("constructorPodiums").textContent = constructor.total_podiums ?? "—";
        document.getElementById("constructorPoints").textContent = constructor.total_points ?? "—";

        // Fill Edit Modal
        document.getElementById("editConstructorId").value = constructor.id;
        document.getElementById("editConstructorName").value = constructor.name || "";
        document.getElementById("editConstructorFullName").value = constructor.full_name || "";
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

document.getElementById("editConstructorForm").addEventListener("submit", async function(e) {
    e.preventDefault();
    const updatedConstructor = {
        id: document.getElementById("editConstructorId").value,
        name: document.getElementById("editConstructorName").value,
        fullName: document.getElementById("editConstructorFullName").value,
        nationality: document.getElementById("editConstructorNationality").value
    };

    try {
        const response = await fetch(`${API_BASE_URL}/constructors`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(updatedConstructor)
        });

        if (response.ok) {
            const modal = bootstrap.Modal.getInstance(document.getElementById("editConstructorModal"));
            modal.hide();
            loadConstructor();
            alert("Constructor updated successfully!");
        } else {
            const err = await response.json();
            alert("Error updating constructor: " + err.error);
        }
    } catch (error) {
        alert("Network error while updating constructor.");
    }
});

loadConstructor();