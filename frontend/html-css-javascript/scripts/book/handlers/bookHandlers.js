import { CONFIG } from "../../services/config.js";
import { exibirMensagem } from "../../utils/notificacao.js";

export function configurarListeners(book, bookService, token, refreshCallback) {
    const stars = document.querySelectorAll(".star-rating .star");
    let selectedRating = 0;

    stars.forEach(star => {
        const value = parseInt(star.dataset.value);
        star.addEventListener("mouseover", () => highlight(value));
        star.addEventListener("mouseout", () => highlight(selectedRating));
        star.addEventListener("click", async () => {
            selectedRating = value;
            highlight(selectedRating);
            try {
                await bookService.postRating(book.title, selectedRating);
                exibirMensagem("success", `⭐ Avaliado com ${selectedRating} estrela(s)!`);
                refreshCallback();
            } catch (err) {
                exibirMensagem("danger", "Erro ao avaliar.");
                console.error(err);
            }
        });
    });

    function highlight(rating) {
        stars.forEach(star => {
            star.classList.toggle("selected", parseInt(star.dataset.value) <= rating);
        });
    }

    document.getElementById("submit-review").addEventListener("click", async () => {
        const comentario = document.getElementById("review-input").value.trim();
        if (!comentario) return exibirMensagem("warning", "Digite um comentário.");

        try {
            await bookService.postReview(book.title, comentario);
            refreshCallback();
        } catch (err) {
            exibirMensagem("danger", "Erro ao enviar comentário.");
            console.error(err);
        }
    });

    const reserveButton = document.getElementById("reserve-book");
    if (reserveButton) {
        reserveButton.addEventListener("click", async () => {
            try {
                await fetch(`${CONFIG.API_URL}/user/${book.title}`, {
                    method: "PUT",
                    headers: { "Authorization": `Bearer ${token}` },
                });
                exibirMensagem("success", "Livro adicionado à biblioteca!");
                refreshCallback();
            } catch (err) {
                exibirMensagem("danger", "Erro ao reservar livro.");
                console.error(err);
            }
        });
    }
}
