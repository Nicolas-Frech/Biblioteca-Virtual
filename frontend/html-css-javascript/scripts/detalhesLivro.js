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

        const reviewsHtml = book.reviews && book.reviews.length > 0
            ? book.reviews.map(review => `
                <div class="mb-2">
                    <li class="list-group-item"><strong>💬 ${review.username}:</strong> ${review.content}</li>
                </div>
            `).join("")
            : `<p class="text-muted">Nenhum comentário ainda.</p>`;

        bookDetails.innerHTML = `
            <div class="row">
                <div class="col-md-4 d-flex flex-column align-items-center">
                    <img src="${book.cover}" class="img-fluid book-cover mb-3" alt="Capa de ${book.title}">
                    ${
                        !book.reserved
                            ? `<button id="reserve-book" class="btn btn-success w-75 fw-bold">📖 Reservar livro</button>`
                            : ""
                    }
                </div>
                <div class="col-md-8">
                    <h3 class="fw-bold">${book.title}</h3>
                    <h5 class="text-muted mb-5">Autor: ${book.authorName}</h5>

                    <div class="book-info-box p-3 mt-4">
                        <p><strong>Gênero:</strong> ${genre}</p>
                        <p><strong>Sinopse:</strong> ${book.synopsis}</p>
                        <p><strong>Data de Publicação:</strong> ${dateFormatter(book.releaseDate)}</p>
                        <p><strong>Avaliação:</strong> ${book.rating}</p>
                        ${availabilityMessage}
                    </div>

                    <div class="mt-4">
                        <div class="mb-3">
                            <label for="review-input" class="form-label fw-semibold">Adicionar um comentário:</label>
                            <div class="d-flex gap-2">
                                <textarea id="review-input" class="form-control" rows="1" placeholder="Escreva seu comentário..."></textarea>
                                <button id="submit-review" class="btn btn-dark fw-bold">Enviar</button>
                            </div>
                        </div>
                        <h5 class="fw-bold">💬 Comentários:</h5>
                        <ul class="list-group mb-3" id="review-list">
                            ${reviewsHtml}
                        </ul>


                    </div>
                </div>
            </div>
        `;

        document.getElementById("submit-review").addEventListener("click", async () => {
            const comentario = document.getElementById("review-input").value.trim();

            if (!comentario) {
                exibirMensagem("warning", "Digite um comentário antes de enviar.");
                return;
            }

            try {
                await bookService.postReview(book.title, comentario);
                fetchBookDetails();
            } catch (err) {
                exibirMensagem("danger", "Erro ao enviar comentário.");
                console.error(err);
            }
        });

        const reserveButton = document.getElementById("reserve-book");
        if (reserveButton) {
            reserveButton.addEventListener("click", async () => {
                try {
                    await bookService.reserveBook(book.title);
                    fetchBookDetails();
                } catch (err) {
                    exibirMensagem("danger", "❌ Erro ao reservar o livro.");
                    console.error(err);
                }
        });
}
    }

    await fetchBookDetails();
});
