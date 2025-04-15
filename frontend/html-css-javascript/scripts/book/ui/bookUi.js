import { dateFormatter, getGenreName, getStars } from "../../utils/formatters.js";

export function renderBookHTML(book, isInLibrary, reviewsHtml) {
    const genre = getGenreName(book.genre);
    const starsGet = getStars(book.rating);

    const libraryMessage = isInLibrary === 'true'
        ? '<button class="btn btn-danger w-75 fw-bold">📖 Já está na sua Biblioteca</button>'
        : '<button id="reserve-book" class="btn btn-success w-75 fw-bold">📖 Adicionar a sua Biblioteca</button>';

    return `
        <div class="row">
            <div class="col-md-4 d-flex flex-column align-items-center">
                <img src="${book.cover}" class="img-fluid book-cover mb-3" alt="Capa de ${book.title}">
                ${libraryMessage}
                <div class="mt-3 text-center w-100">
                    <label class="form-label fw-bold">Avalie este livro:</label>
                    <div id="rating-stars" class="star-rating">
                        ${[1, 2, 3, 4, 5].map(i => `<span class="star" data-value="${i}" style="cursor:pointer; font-size: 1.8rem;">☆</span>`).join("")}
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
}
