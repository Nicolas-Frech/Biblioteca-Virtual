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
  
      const user = await response.json();
      
      if(user.userRole == "ADMIN") {
        let adminBook = document.getElementById("adminBook")
        let adminAuthor = document.getElementById("adminAuthor")
        adminAuthor.innerHTML = `
        <a href="deletarAutor.html" class="btn btn-dark fw-bold mb-3">Excluir Autor 🗑️</a>
        <a href="cadastrarAutor.html" class="btn btn-dark fw-bold mb-3">Cadastrar Autor ✍</a>
        `
        adminBook.innerHTML = `                    
        <a href="deletarLivro.html" class="btn btn-dark fw-bold mb-3">Excluir Livro 🗑️</a>
        <a href="cadastrarLivro.html" class="btn btn-dark fw-bold mb-3">Cadastrar Livro ✍</a>
        `
      }

      profileInfo.innerHTML = `
        <p><strong>Nome de Usuário:</strong> ${user.username}</p>
        <p><strong>Email:</strong> ${user.email}</p>
        <p class="mb-1"><strong>Perfil:</strong> ${user.userRole}</p>
      `;

      if (user.myLibrary && user.myLibrary.length > 0) {
        const bookRow = document.createElement("div");
        bookRow.className = "row g-3";
        
        user.myLibrary.forEach(book => {
          const col = document.createElement("div");
          col.className = "col-md-3";
        

          col.innerHTML = `
            <a href="detalhesLivro.html?id=${book.id}" class="text-decoration-none text-dark">
                <div class="card h-100 shadow-sm">
                    <img src="${book.cover}" class="card-img-top img-fluid book-cover" alt="Capa de ${book.title}">
                    <div class="card-body">
                        <h5 class="card-title">${book.title}</h5>
                        <p class="card-text">${book.author.name}</p>
                    </div>
                </div>
            </a>
          `;
        
          bookRow.appendChild(col);
        });
        
        bookList.innerHTML = "";
        bookList.appendChild(bookRow);
      } else {
        bookList.innerHTML = `<li class="list-group-item">Nenhum livro adicionado.</li>`;
      }
  
    } catch (error) {
      console.error("Erro:", error);
      profileInfo.innerHTML = `<div class="alert alert-danger">Erro ao carregar os dados do perfil.</div>`;
    }
  });

