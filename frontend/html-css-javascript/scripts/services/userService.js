import { exibirMensagem } from "../utils/notificacao.js";

export class UserService {
    constructor(baseUrl) {
        this.baseUrl = `${baseUrl}`;
        this.token = localStorage.getItem("token"); 
    }

    async registUser(user) {
        return this.request("/login/register", "POST", user);
    }

    async fetchUser() {
        return this.request("/user", "GET");
    }

    async removeBookByTitle(title) {
        return this.request(`/user/book/remove/${title}`, "DELETE");
    }

    async uploadProfileImage(file) {
        const formData = new FormData();
        formData.append("image", file);

        const response = await fetch(`${this.baseUrl}/user/upload-profile`, {
            method: "PUT",
            headers: {
                "Authorization": `Bearer ${this.token}`
            },
            body: formData
        });

        if (!response.ok) {
            const erro = await response.text() || "Erro ao enviar imagem";
            throw new Error(erro);
        }

        return await response.json();
    }

    async request(endpoint, method, body = null) {
        try {
            if(!this.token) {
                exibirMensagem("danger", "Você precisa estar logado!");
                setTimeout(() => {
                    window.location.href = "login.html";
                },  2000);
            
            }
            const options = {
                method,
                headers: { 
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${this.token}`
                },
                body: body ? JSON.stringify(body) : null,
            };
    
            const response = await fetch(`${this.baseUrl}${endpoint}`, options);
    
            if (!response.ok) {
                const mensagemErro = await response.text() || "Erro na requisição!";
                throw new Error(mensagemErro);
            }
    
            return response.status !== 204 ? await response.json() : null; 
        } catch (error) {
            console.error("Erro na requisição:", error);
            throw error;
        }
    }

}