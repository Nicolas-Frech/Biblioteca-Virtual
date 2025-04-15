export function exibirMensagem(tipo, mensagem) {
  const alerta = document.createElement("div");
  alerta.className = `alert alert-${tipo} alert-dismissible fade show fw-bold text-center`;
  alerta.role = "alert";
  alerta.innerHTML = `
      ${mensagem}
      <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Fechar"></button>
  `;

  const container = document.getElementById("mensagem-container");
  container.innerHTML = "";
  container.appendChild(alerta);
}
