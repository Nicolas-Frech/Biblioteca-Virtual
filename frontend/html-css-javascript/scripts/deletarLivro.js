import { exibirMensagem } from "./notificacao.js";

const form = document.getElementById("bookForm");
const btn = document.getElementById("btn");

async function deletarLivro(event) {
    event.preventDefault();

    const title = document.getElementById("title").value;

    const options = {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
        }
    }

    try {
        const response = await fetch(`http://localhost:8080/book/${title}`, options);
        
        if (!response.ok) {
            const mensagemErro = await response.text() || "Erro ao excluir livro!";
            exibirMensagem("danger", `❌ ${mensagemErro}`);
            return;
        }
          
        exibirMensagem("success", "✅ Livro excluído com sucesso!");
        form.reset();
          
    } catch (error) {
        exibirMensagem("danger", "❌ Erro ao excluir livro!");
        console.error("Erro ao excluir livro:", error);
    }
}

form.addEventListener("submit", deletarLivro);