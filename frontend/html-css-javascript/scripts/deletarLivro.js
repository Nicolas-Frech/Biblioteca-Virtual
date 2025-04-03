import { exibirMensagem } from "./notificacao.js";
import { BookService } from "./bookService.js";

const form = document.getElementById("bookForm");
const bookService = new BookService("http://localhost:8080");

async function deletarLivro(event) {
    event.preventDefault();

    const bookTitle = document.getElementById("title").value.trim();

    if (!bookTitle) {
        exibirMensagem("warning", "⚠️ Informe o título do livro para deletá-lo!");
        return;
    }

    try {
        await bookService.deleteBook(bookTitle);
        exibirMensagem("success", `✅ Livro "${bookTitle}" excluído com sucesso!`);
        form.reset();
    } catch (error) {
        exibirMensagem("danger", `❌ ${error.message}`);
    }
}

form.addEventListener("submit", deletarLivro);
