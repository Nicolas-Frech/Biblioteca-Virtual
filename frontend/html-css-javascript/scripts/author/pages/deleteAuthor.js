import { exibirMensagem } from "../../utils/notificacao.js";
import { verificarLogin } from "../../auth.js";
import { CONFIG } from "../../services/config.js";

const form = document.getElementById("authorForm");

const token = verificarLogin();

async function deletarAutor(event) {
    event.preventDefault();

    const authorName = document.getElementById("name").value.trim();

    if (!authorName) {
        exibirMensagem("warning", "⚠️ Informe o nome do autor para deletá-lo!");
        return;
    }

    const options = {
        method: "DELETE",
        headers: {  
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        }
    };

    try {
        const response = await fetch(`${CONFIG.API_URL}/author/${authorName}`, options);

        if (!response.ok) {
            const mensagemErro = await response.text() || "Erro ao deletar autor!";
            exibirMensagem("danger", `❌ ${mensagemErro}`);
            return;
        }
    
        exibirMensagem("success", `✅ Autor "${authorName}" excluído com sucesso!`);
        form.reset();

    } catch (error) {
        exibirMensagem("danger", `❌ ${error.message}`);
    }
}

form.addEventListener("submit", deletarAutor);
