import { exibirMensagem } from "./utils/notificacao.js";

export function verificarLogin() {
    const token = localStorage.getItem("token");
    if (!token) {
        exibirMensagem("danger", "Você precisa estar logado!");
        setTimeout(() => window.location.href = "login.html", 2000);
    }
    return token;
}
