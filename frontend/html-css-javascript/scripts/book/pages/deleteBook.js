import { exibirMensagem } from "../../utils/notificacao.js";
import { BookService } from "../../services/bookService.js";
import { CONFIG } from "../../services/config.js";

const form = document.getElementById("bookForm");
const bookService = new BookService(CONFIG.API_URL);

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
