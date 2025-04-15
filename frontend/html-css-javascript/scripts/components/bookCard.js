export function createBookCard(book, options = {}) {
    const {
        showRemoveButton = false,
        onRemove = null,
    } = options;

    const wrapper = document.createElement("div");

    wrapper.innerHTML = `
        <a href="detalhesLivro.html?id=${book.id}" class="text-decoration-none text-dark">
            <div class="card h-100 shadow-sm">
                <img src="${book.cover}" class="card-img-top img-fluid book-cover" alt="Capa de ${book.title}">
                <div class="card-body">
                    <h5 class="card-title">${book.title}</h5>
                    <p class="card-text">${book.author?.name || book.authorName || ""}</p>
                    ${showRemoveButton ? `<button class="btn btn-dark mt-2 remove-btn">❌</button>` : ""}
                </div>
            </div>
        </a>
    `;

    const card = wrapper.firstElementChild;

    if (showRemoveButton && onRemove) {
        const btn = card.querySelector(".remove-btn");
        btn.addEventListener("click", (event) => {
            event.preventDefault(); // Evita redirecionar
            onRemove();
        });
    }

    return card;
}