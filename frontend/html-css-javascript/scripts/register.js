import { exibirMensagem } from "./notificacao.js";

document.getElementById("registerForm").addEventListener("submit", async function(event) {
    event.preventDefault();

    const email = document.getElementById("email").value;
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    try {
        const response = await fetch("http://localhost:8080/login/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({username, password, email })
        });

        if (response.ok) {
            exibirMensagem("success", "Cadastro Relizado! Faça Login");
            setTimeout(() => {
                window.location.href = "login.html";
            },  2000);
        } else {
            const errorMsg = await response.text();
            exibirMensagem("danger", `Erro ao cadastrar: ${errorMsg}`);
        }
    } catch (error) {
        console.error("Erro ao cadastrar:", error);
        exibirMensagem("danger", "Erro ao cadastrar. Tente novamente mais tarde.");
    }
});
