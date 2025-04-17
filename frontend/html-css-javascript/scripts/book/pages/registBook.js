import { BookService } from "../../services/bookService.js";
import { CONFIG } from "../../services/config.js";
import { exibirMensagem } from "../../utils/notificacao.js";

const bookService = new BookService(CONFIG.API_URL);
const form = document.getElementById("bookForm");

async function cadastrarLivro(event) {
  event.preventDefault();

  const title = document.getElementById("title").value;
  const genre = document.getElementById("genre").value;
  const authorName = document.getElementById("author").value;
  const releaseDate = document.getElementById("releaseDate").value;
  const cover = document.getElementById("cover").value;
  const synopsis = document.getElementById("synopsis").value;

  const livro = { title, genre, authorName, releaseDate, cover, synopsis };

  try {
    await bookService.createBook(livro);
    exibirMensagem("success", "✅ Livro cadastrado com sucesso!");
    form.reset();
  } catch (error) {
    exibirMensagem("danger", `❌ ${error.message}`);
  }
}
form.addEventListener("submit", cadastrarLivro);