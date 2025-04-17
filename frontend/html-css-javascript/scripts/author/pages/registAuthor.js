import { exibirMensagem } from "../../utils/notificacao.js";
import { verificarLogin } from "../../auth.js";
import { CONFIG } from "../../services/config.js";

const token = verificarLogin();

document.getElementById("authorForm").addEventListener("submit", async function(event) {
    event.preventDefault();
    
    const form = document.getElementById("authorForm");
    const name = document.getElementById("name").value;
    const birthdate = document.getElementById("birthDate").value;
    const autor = { name, birthdate };
    
    const options = {
        method: "POST",
        headers: {  
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
         },
        body: JSON.stringify(autor),
    };
    
    try {
       const response = await fetch(`${CONFIG.API_URL}/author`, options);
         
           if (!response.ok) {
             const mensagemErro = await response.text() || "Erro ao cadastrar autor!";
             exibirMensagem("danger", `❌ ${mensagemErro}`);
             return;
           }
         
           exibirMensagem("success", "✅ Autor cadastrado com sucesso!");
           form.reset();
         
    } catch (error) {
       exibirMensagem("danger", "❌ Erro ao cadastrar autor!");
        console.error("Erro ao cadastrar livro:", error);
    }
});