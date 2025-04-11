document.addEventListener("DOMContentLoaded", async () => {
    const profileInfo = document.getElementById("profile-info");
    const bookList = document.getElementById("bookList");
  
    const token = localStorage.getItem("token");
  
    if (!token) {
      profileInfo.innerHTML = `<div class="alert alert-danger">Usuário não autenticado.</div>`;
      return;
    }
  
    try {
      const response = await fetch("http://localhost:8080/user", {
        method: "GET",
        headers: {
          "Authorization": `Bearer ${token}`,
          "Content-Type": "application/json"
        }
      });
  
      if (!response.ok) {
        throw new Error("Erro ao carregar perfil");
      }
  
      const user= await response.json();

      profileInfo.innerHTML = `
        <p><strong>Nome de Usuário:</strong> ${user.username}</p>
        <p><strong>Email:</strong> ${user.email}</p>
        <p><strong>Perfil:</strong> ${user.userRole}</p>
      `;

      if (user.books && user.books.length > 0) {
        usuario.books.forEach(book => {
          const li = document.createElement("li");
          li.className = "list-group-item";
          li.textContent = `${book.title} - ${book.authorName}`;
          bookList.appendChild(li);
        });
      } else {
        bookList.innerHTML = `<li class="list-group-item">Nenhum livro adicionado.</li>`;
      }
  
    } catch (error) {
      console.error("Erro:", error);
      profileInfo.innerHTML = `<div class="alert alert-danger">Erro ao carregar os dados do perfil.</div>`;
    }
  });

document.getElementById('upload-photo').addEventListener('change', (event) => {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e) => {
        document.getElementById('profile-picture').src = e.target.result;
      };
      reader.readAsDataURL(file);
    }
});