export function exibirMensagem(tipo, mensagem) {
    const divMensagem = tipo === "success" ? document.getElementById("mensagemSucesso") : document.getElementById("mensagemErro");
  
    divMensagem.textContent = mensagem;
    divMensagem.classList.remove("d-none");
  
    setTimeout(() => {
      divMensagem.classList.add("d-none");
    }, 5000);
  }