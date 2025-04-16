export function dateFormatter(dataISO) {
    if (!dataISO) return "Data inválida";
    const data = new Date(dataISO);
    return new Intl.DateTimeFormat("pt-BR", {
        day: "2-digit",
        month: "2-digit",
        year: "numeric"
    }).format(data);
}

export function getGenreName(genreCode) {
    const genres = {
        FANTASY: "Fantasia 🧙‍♂️",
        MYSTERY: "Mistério 🕵️‍♀️",
        HISTORY: "História 🏺",
        ROMANCE: "Romance ❤️",
        FICTION: "Ficção 🛸",
        THRILLER: "Terror 👻",
        ADVENTURE: "Aventura 🗺️",
        SCIENCE: "Ciência 🔬",
        PHILOSOPHY: "Filosofia 🧠"
    };
    return genres[genreCode] || "Gênero desconhecido";
}

export function getStars(rating) {
    if (typeof rating !== "number" || rating <= 0) return "Ainda não foi avaliado!";
    return "⭐".repeat(Math.floor(rating));
}
