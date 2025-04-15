import { exibirMensagem } from "./notificacao.js";
import { BookService } from "./bookService.js";
import { dateFormatter, getGenreName, getStars } from "./formatters.js";

const token = localStorage.getItem("token");

if(!token) {
    exibirMensagem("danger", "Você precisa estar logado!");
    setTimeout(() => {
        window.location.href = "login.html";
    },  2000);
}

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

    async function displayBookDetails(book) {
        const genre = getGenreName(book.genre);
        const starsGet = getStars(book.rating);
    
        const reviewsHtml = book.reviews && book.reviews.length > 0
            ? book.reviews.map(review => `
                <div class="mb-2">
                    <li class="list-group-item"><strong>💬 ${review.username}:</strong> ${review.content}</li>
                </div>
            `).join("")
            : `<p class="text-muted">Nenhum comentário ainda.</p>`;

        const response = await fetch(`http://localhost:8080/user/myLibrary/${book.title}`, {
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        }
        });
        
        const isInLibrary = await response.text();
        let libraryMessage;
    
        if (isInLibrary === 'true') {
            libraryMessage = '<button class="btn btn-danger w-75 fw-bold">📖 Já está na sua Biblioteca</button>';
        } else {
            libraryMessage = '<button id="reserve-book" class="btn btn-success w-75 fw-bold">📖 Adicionar a sua Biblioteca</button>';
        }
    
        bookDetails.innerHTML = `
            <div class="row">
               
                <div class="col-md-4 d-flex flex-column align-items-center">
                    <img src="${book.cover}" class="img-fluid book-cover mb-3" alt="Capa de ${book.title}">
                    
                    ${libraryMessage}
                    
                    <div class="mt-3 text-center w-100">
                        <label class="form-label fw-bold">Avalie este livro:</label>
                        <div id="rating-stars" class="star-rating">
                            ${[1, 2, 3, 4, 5].map(i => `
                                <span class="star" data-value="${i}" style="cursor:pointer; font-size: 1.8rem;">
                                    ☆
                                </span>
                            `).join("")}
                        </div>
                    </div>
                </div>

    
                <div class="col-md-8">
                    <h3 class="fw-bold">${book.title}</h3>
                    <h5 class="text-muted mb-5">Autor: ${book.authorName}</h5>
    
                    <div class="book-info-box p-3 mt-4">
                        <p><strong>Gênero:</strong> ${genre}</p>
                        <p><strong>Sinopse:</strong> ${book.synopsis}</p>
                        <p><strong>Data de Publicação:</strong> ${dateFormatter(book.releaseDate)}</p>
                        <p><strong>Média das Avaliações:</strong> ${starsGet}</p>
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

        configurarListeners(book);
    }

    function configurarListeners(book) {
        const stars = document.querySelectorAll(".star-rating .star");
        let selectedRating = 0;

        function highlightStars(rating) {
            stars.forEach(star => {
                const value = parseInt(star.getAttribute("data-value"));
                star.classList.toggle("selected", value <= rating);
            });
        }

        stars.forEach(star => {
            star.addEventListener("mouseover", () => {
                const value = parseInt(star.getAttribute("data-value"));
                highlightStars(value);
            });

            star.addEventListener("mouseout", () => {
                highlightStars(selectedRating);
            });

            star.addEventListener("click", async () => {
                selectedRating = parseInt(star.getAttribute("data-value"));
                highlightStars(selectedRating);

                try {
                    await bookService.postRating(book.title, selectedRating);
                    exibirMensagem("success", `⭐ Você avaliou com ${selectedRating} estrela(s)!`);
                    await fetchBookDetails();
                } catch (err) {
                    exibirMensagem("danger", "Erro ao enviar avaliação.");
                    console.error(err);
                }
            });
        });

        document.getElementById("submit-review").addEventListener("click", async () => {
            const comentario = document.getElementById("review-input").value.trim();

            if (!comentario) {
                exibirMensagem("warning", "Digite um comentário antes de enviar.");
                return;
            }

            try {
                await bookService.postReview(book.title, comentario);
                await fetchBookDetails();
            } catch (err) {
                exibirMensagem("danger", "Erro ao enviar comentário.");
                console.error(err);
            }
        });


        const reserveButton = document.getElementById("reserve-book");
        if (reserveButton) {
            reserveButton.addEventListener("click", async () => {
                try {
                    await fetch(`http://localhost:8080/user/${book.title}`, {
                        method: "PUT",
                        headers: {
                            "Content-Type": "application/json",
                            "Authorization": `Bearer ${token}`
                        },
                    });
 
                    exibirMensagem("success", "Livro adicionado a sua Biblioteca!");
                    await fetchBookDetails();
                } catch (err) {
                    exibirMensagem("danger", "❌ Erro ao reservar o livro.");
                    console.error(err);
                }
            });
        } else {
            console.error("Botão de reserva não encontrado!");
        }
    }

    await fetchBookDetails();
});
