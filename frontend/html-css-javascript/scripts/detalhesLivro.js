import { exibirMensagem } from "./notificacao.js";
import { BookService } from "./bookService.js";

document.addEventListener("DOMContentLoaded", async () => {
    const bookDetails = document.getElementById("book-details");
    const bookId = new URLSearchParams(window.location.search).get("id");

    const bookService = new BookService("http://localhost:8080");

    async function fetchBookDetails() {
        try {
            const book = await bookService.fetchBookById(bookId);
            displayBookDetails(book);
        } catch (error) {
            exibirMensagem("danger", "❌ Erro ao buscar detalhes do livro!");
            console.error("Erro ao buscar detalhes do livro:", error);
        }
    }

    function dateFormatter(dataISO) {
        if (!dataISO) return "Data inválida";

        const data = new Date(dataISO);
        return new Intl.DateTimeFormat("pt-BR", {
            day: "2-digit",
            month: "2-digit",
            year: "numeric",
            hour12: false
        }).format(data);
    }

    function getGenreName(genreCode) {
        const genres = {
            "FANTASY": "Fantasia",
            "MISTERY": "Mistério",
            "HISTORY": "História",
            "ROMANCE": "Romance",
            "FICTION": "Ficção",
            "TERROR": "Terror",
            "ADVENTURE": "Aventura",
            "SCIENCE": "Ciência",
            "PHILOSOPHY": "Filosofia"
        };
        return genres[genreCode] || "Gênero desconhecido";
    }

    function displayBookDetails(book) {
        const genre = getGenreName(book.genre);

        const availabilityMessage = book.reserved
            ? `<p class="text-danger"><strong>Este livro já está reservado!</strong></p>`
            : `<p class="text-success"><strong>Disponível para reserva!</strong></p>`;

        bookDetails.innerHTML = `
            <div class="row">
                <div class="col-md-4">
                    <img src="${book.cover}" class="img-fluid book-cover" alt="Capa de ${book.title}">
                </div>
                <div class="col-md-8">
                    <h3 class="fw-bold">${book.title}</h3>
                    <h5 class="text-muted mb-5">Autor: ${book.authorName}</h5>

                    <div class="book-info-box p-3 mt-4">
                        <p><strong>Gênero:</strong> ${genre}</p>
                        <p><strong>Sinopse:</strong> ${book.synopsis}</p>
                        <p><strong>Data de Publicação:</strong> ${dateFormatter(book.releaseDate)}</p>
                        <p><strong>Avaliação: ⭐⭐⭐</strong></p>
                        ${availabilityMessage}
                    </div>
                </div>
            </div>
        `;
    }

    await fetchBookDetails();
});
