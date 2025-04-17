import { BookService } from "./services/bookService.js";
import { verificarLogin } from "./auth.js";
import { createBookCard } from "./components/bookCard.js";
import { renderPagination } from "./utils/pagination.js";
import { clearElement } from "./utils/dom.js";
import { CONFIG } from "./services/config.js";

const token = verificarLogin();

document.addEventListener("DOMContentLoaded", () => {
    const bookList = document.getElementById("book-list");
    const pagination = document.getElementById("pagination");
    const searchInput = document.getElementById("search");
    const searchBtn = document.getElementById("searchBtn");
    const searchBtnGenre = document.getElementById("searchBtnGenre");
    const searchBtnAuthor = document.getElementById("searchBtnAuthor");

    const bookService = new BookService(CONFIG.API_URL);
    let currentPage = 0;
    let currentFilter = "";
    const pageSize = 8;

    async function fetchBooks(page = 0, filter = "") {
        try {
            const data = await bookService.fetchBooks(page, filter, pageSize);
            displayBooks(data.content);
            renderPagination(pagination, data.totalPages, page, (i) => fetchBooks(i, currentFilter));
        } catch (error) {
            console.error("Erro ao buscar livros:", error);
        }
    }

    function displayBooks(books) {
        clearElement(bookList);
        const row = document.createElement("div");
        row.className = "row g-3";

        books.forEach(book => {
            const col = document.createElement("div");
            col.className = "col-sm-12 col-md-3";
            const card = createBookCard(book);
            col.appendChild(card);
            row.appendChild(col);
        });

        bookList.appendChild(row);
    }

    searchBtn.addEventListener("click", () => {
        currentFilter = `/title/${searchInput.value.trim()}`;
        fetchBooks(0, currentFilter);
    });

    searchBtnGenre.addEventListener("click", () => {
        const genre = document.getElementById("genre").value;
        currentFilter = `/genre/${genre}`;
        fetchBooks(0, currentFilter);
    });

    searchBtnAuthor.addEventListener("click", () => {
        const searchAuthor = document.getElementById("searchAuthor").value.trim();
        currentFilter = `/author/${searchAuthor}`;
        fetchBooks(0, currentFilter);
    });

    fetchBooks();
});
