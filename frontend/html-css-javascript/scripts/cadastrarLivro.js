import { exibirMensagem } from "./notificacao.js";

const form = document.getElementById("bookForm");

const btn = document.getElementById("btn");

async function cadastrarLivro(event) {
  event.preventDefault();


  const title = document.getElementById("title").value;
  const genre = document.getElementById("genre").value;
  const authorName = document.getElementById("author").value;
  const releaseDate = document.getElementById("releaseDate").value;
  const cover = document.getElementById("cover").value;
  const synopsis = document.getElementById("synopsis").value

  const livro = { title, genre, authorName, releaseDate, cover, synopsis };

  const options = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(livro),
  };
  
  try {
    const response = await fetch("http://localhost:8080/book", options);
  
    if (!response.ok) {
      const mensagemErro = await response.text() || "Erro ao cadastrar livro!";
      exibirMensagem("danger", `❌ ${mensagemErro}`);
      return;
    }
  
    exibirMensagem("success", "✅ Livro cadastrado com sucesso!");
    form.reset();
  
  } catch (error) {
    exibirMensagem("danger", "❌ Erro ao cadastrar livro!");
    console.error("Erro ao cadastrar livro:", error);
  }
}

form.addEventListener("submit", cadastrarLivro);