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
        <a href="deletarAutor.html" class="btn btn-outline-dark fw-bold mb-3">Excluir Autor 🗑️</a>
        <a href="cadastrarAutor.html" class="btn btn-outline-dark fw-bold mb-3">Cadastrar Autor ✍</a>
        `
        adminBook.innerHTML = `                    
        <a href="deletarLivro.html" class="btn btn-outline-dark fw-bold mb-3">Excluir Livro 🗑️</a>
        <a href="cadastrarLivro.html" class="btn btn-outline-dark fw-bold mb-3">Cadastrar Livro ✍</a>
        `
      }

      document.getElementById("username").textContent = user.username;
      document.getElementById("userEmail").textContent = user.email;
      document.getElementById("userRole").textContent = user.userRole;

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
                        <button class="btn btn-dark" id="remove-${book.id}">❌</button>
                    </div>
                </div>
            </a>

          `;
          const removeButton = col.querySelector(`#remove-${book.id}`);
          removeButton.addEventListener("click", async (event) => {
            event.preventDefault();
            try {
              const removeResponse = await fetch(`http://localhost:8080/user/book/remove/${book.title}`, {
                method: "DELETE",
                headers: {
                  "Authorization": `Bearer ${token}`,
                  "Content-Type": "application/json"
                }
              });

              if (!removeResponse.ok) {
                throw new Error("Erro ao remover livro");
              }

              col.remove();
              await fetchBookDetails();
            } catch (err) {
              exibirMensagem("danger", "❌ Erro ao remover o livro.");
              console.error(err);
            }
          });

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
    
    imageInput?.addEventListener("change", async () => {
      const file = imageInput.files[0];
      if (!file) return;
    
      const formData = new FormData();
      formData.append("image", file);
    
      try {
        const response = await fetch("http://localhost:8080/user/upload-profile", {
          method: "POST",
          headers: {
            Authorization: `Bearer ${token}`
          },
          body: formData
        });
    
        if (!response.ok) {
          throw new Error("Erro ao enviar imagem");
        }
        
        const imageUrl = `http://localhost:8080/uploads/${file.name}`;
        document.getElementById("profilePic").src = imageUrl;
    
        alert("✅ Foto de perfil atualizada!");
      } catch (error) {
        console.error("Erro no upload:", error);
        alert("❌ Erro ao atualizar imagem");
      }
    });
});

