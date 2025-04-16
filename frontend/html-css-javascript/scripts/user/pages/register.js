import { exibirMensagem } from "../../utils/notificacao.js";
import { UserService } from "../../services/userService.js";

document.getElementById("registerForm").addEventListener("submit", async function(event) {
    event.preventDefault();

    const email = document.getElementById("email").value;
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const userService = new UserService("http://localhost:8080");

    try {
        const user = { username, password, email }
        await userService.registUser(user);
        exibirMensagem("success", "Cadastro Relizado! Faça Login");
        setTimeout(() => {
            window.location.href = "login.html";
        },  2000);
    } catch (error) {
        console.error("Erro ao cadastrar:", error);
        exibirMensagem("danger", "Erro ao cadastrar. Tente novamente mais tarde.");
    }
});
