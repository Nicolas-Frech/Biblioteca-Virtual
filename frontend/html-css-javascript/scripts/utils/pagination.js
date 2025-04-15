export function renderPagination(container, totalPages, currentPage, onPageChange) {
    container.innerHTML = "";

    for (let i = 0; i < totalPages; i++) {
        const button = document.createElement("button");
        button.textContent = i + 1;
        button.className = `btn btn-dark mx-1 ${i === currentPage ? "active" : ""}`;
        button.addEventListener("click", () => onPageChange(i));
        container.appendChild(button);
    }
}
