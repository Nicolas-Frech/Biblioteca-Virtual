import { BookService } from "./bookService.js";

document.addEventListener("DOMContentLoaded", () => {
    const bookList = document.getElementById("book-list");
    const pagination = document.getElementById("pagination");
    const searchInput = document.getElementById("search");
    const searchBtn = document.getElementById("searchBtn");
    const searchBtnGenre = document.getElementById("searchBtnGenre");
    const searchBtnAuthor = document.getElementById("searchBtnAuthor");

    let currentPage = 0;
    const pageSize = 8;
    let currentFilter = "";
    
    const bookService = new BookService("http://localhost:8080");

    async function fetchBooks(page = 0, filter = "") {
        try {
            const data = await bookService.fetchBooks(page, filter, pageSize);
            displayBooks(data.content);
            setupPagination(data.totalPages, page);
        } catch (error) {
            console.error("Erro ao buscar livros:", error);
        }
    }

    function setupPagination(totalPages, currentPage) {
        pagination.innerHTML = "";

        for (let i = 0; i < totalPages; i++) {
            const button = document.createElement("button");
            button.textContent = i + 1;
            button.className = `btn btn-dark mx-1 ${i === currentPage ? "active" : ""}`;
            button.addEventListener("click", () => fetchBooks(i, currentFilter));
            pagination.appendChild(button);
        }
    }

    async function fetchBooksBySearch() {
        currentFilter = `/title/${searchInput.value.trim()}`;
        fetchBooks(0, currentFilter);
    }

    async function fetchBooksByGenre() {
        const genre = document.getElementById("genre").value;
        currentFilter = `/genre/${genre}`;
        fetchBooks(0, currentFilter);
    }

    async function fetchBooksByAuthor() {
        const searchAuthor = document.getElementById("searchAuthor").value.trim();
        currentFilter = `/author/${searchAuthor}`;
        fetchBooks(0, currentFilter);
    }

    function displayBooks(books) {
        bookList.innerHTML = "";
        const row = document.createElement("div");
        row.className = "row g-3";

        books.forEach(book => {
            const col = document.createElement("div");
            col.className = "col-md-3";

            col.innerHTML = `
                <a href="detalhesLivro.html?id=${book.id}" class="text-decoration-none">
                    <div class="card h-100 shadow-sm">
                        <img src="${book.cover}" class="card-img-top img-fluid book-cover" alt="Capa de ${book.title}">
                        <div class="card-body">
                            <h5 class="card-title">${book.title}</h5>
                            <p class="card-text">${book.authorName}</p>
                        </div>
                    </div>
                </a>
            `;

            row.appendChild(col);
        });

        bookList.appendChild(row);
    }

    searchBtn.addEventListener("click", fetchBooksBySearch);
    searchBtnGenre.addEventListener("click", fetchBooksByGenre);
    searchBtnAuthor.addEventListener("click", fetchBooksByAuthor);

    fetchBooks();
});
