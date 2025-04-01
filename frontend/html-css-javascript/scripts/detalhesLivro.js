document.addEventListener("DOMContentLoaded", () => {
    const bookDetails = document.getElementById("book-details");
    const bookId = new URLSearchParams(window.location.search).get("id");

    async function fetchBookDetails() {
        try {
            let url = `http://localhost:8080/book/${bookId}`;
            const response = await fetch(url);
            const data = await response.json();

            console.log("Detalhes do livro:", data);

            displayBookDetails(data);
        } catch (error) {
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


    function displayBookDetails(book) {
        let message;
        if(book.reserved === true) {
            message = `<p class="text-danger"><strong>Este livro já está reservado!</p>`
        } else {
            message = `<p class="text-success"><strong>Disponível para reserva!</p>`
        }
        bookDetails.innerHTML = `
            <div class="row">
                <div class="col-md-4">
                    <img src="${book.cover}" class="img-fluid book-cover" alt="Capa de ${book.title}">
                </div>
                <div class="col-md-8">
                    <h3 class="fw-bold">${book.title}</h3>
                    <h5 class="text-muted mb-5">Autor: ${book.authorName}</h5>

                    <div class="book-info-box p-3 mt-4">
                        <p><strong>Gênero:</strong> ${book.genre}</p>
                        <p><strong>Sinopse:</strong> ${book.synopsis}</p>
                        <p><strong>Data de Publicação:</strong> ${dateFormatter(book.releaseDate)}</p>
                        <p><strong>Avaliação: ⭐⭐⭐</strong></p>
                        ${message}
                    </div>
                </div>
            </div>
        `;
    }

    fetchBookDetails();
});
