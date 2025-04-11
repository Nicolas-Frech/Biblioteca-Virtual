import { exibirMensagem } from "./notificacao.js";

export class BookService {
    constructor(baseUrl) {
        this.baseUrl = `${baseUrl}/book`;
        this.token = localStorage.getItem("token"); 
    }

    async createBook(book) {
        return this.request("", "POST", book);
    }

    async fetchBooks(page = 0, filter = "", pageSize = 8) {
        return this.request(`${filter}?page=${page}&size=${pageSize}`, "GET");
    }

    async fetchBooksByGenre(genre) {
        return this.request(`/genre/${encodeURIComponent(genre)}`, "GET");
    }

    async fetchBooksByAuthor(author) {
        return this.request(`/author/${encodeURIComponent(author)}`, "GET");
    }

    async fetchBookById(id) {
        return this.request(`/${id}`, "GET");
    }

    async deleteBook(title) {
        return this.request(`/${encodeURIComponent(title)}`, "DELETE");
    }

    async postReview(title, review) {
        const body = { title, review };
        return this.request("/review", "PUT", body);
    }

    async postRating(title, rating) {
        const body = { title, rating }
        return this.request(`/rate`, "PUT", body)
    }


    async request(endpoint, method, body = null) {
        try {
            if(!this.token) {
                exibirMensagem("danger", "Você precisa estar logado!");
                setTimeout(() => {
                    window.location.href = "login.html";
                },  2000);
            }

            const options = {
                method,
                headers: { 
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${this.token}`
                },
                body: body ? JSON.stringify(body) : null,
            };

            const response = await fetch(`${this.baseUrl}${endpoint}`, options);

            if (!response.ok) {
                const mensagemErro = await response.text() || "Erro na requisição!";
                throw new Error(mensagemErro);
            }

            return response.status !== 204 ? await response.json() : null; 
        } catch (error) {
            console.error("Erro na requisição:", error);
            throw error;
        }
    }
}
