import { verificarLogin } from "../../auth.js";
import { BookService } from "../../services/bookService.js";
import { renderBookHTML } from "../ui/bookUi.js";
import { configurarListeners } from "../handlers/bookHandlers.js";
import { exibirMensagem } from "../../utils/notificacao.js";

const token = verificarLogin();
const bookService = new BookService("http://localhost:8080");
const bookDetails = document.getElementById("book-details");
const bookId = new URLSearchParams(window.location.search).get("id");

document.addEventListener("DOMContentLoaded", fetchBookDetails);

async function fetchBookDetails() {
    try {
        const book = await bookService.fetchBookById(bookId);

        const response = await fetch(`http://localhost:8080/user/myLibrary/${book.title}`, {
            headers: { "Authorization": `Bearer ${token}` }
        });

        const isInLibrary = await response.text();

        const reviewsHtml = book.reviews?.length > 0
            ? book.reviews.map(r => `<li class="list-group-item"><strong>💬 ${r.username}:</strong> ${r.content}</li>`).join("")
            : `<p class="text-muted">Nenhum comentário ainda.</p>`;

        bookDetails.innerHTML = renderBookHTML(book, isInLibrary, reviewsHtml);
        configurarListeners(book, bookService, token, fetchBookDetails);

    } catch (err) {
        exibirMensagem("danger", "Erro ao carregar livro.");
        console.error(err);
    }
}
