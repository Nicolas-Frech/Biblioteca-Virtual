import { UserService } from "../../services/userService.js";
import { createBookCard } from "../../components/bookCard.js";

document.addEventListener("DOMContentLoaded", async () => {
    const profileInfo = document.getElementById("profile-info");
    const bookList = document.getElementById("bookList");

    const userService = new UserService("http://localhost:8080")
    try {
      const user = await userService.fetchUser();

      if (user.profileImage) {
        const fileName = user.profileImage.split("/").pop().split("\\").pop();
        document.querySelector("#profile-info img").src = `http://localhost:8080/uploads/${fileName}`;
      }
      
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
          const card = createBookCard(book, {
            showRemoveButton: true,
            onRemove: async () => {
                try {
                    await userService.removeBookByTitle(book.title);
                    col.remove();
                    await fetchBookDetails();
                } catch (err) {
                    exibirMensagem("danger", "❌ Erro ao remover o livro.");
                    console.error(err);
                }
            }
          });

        col.appendChild(card);
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
        const result = await userService.uploadProfileImage(file);
        const imageUrl = `http://localhost:8080/uploads/${result.fileName}`;
        document.getElementById("profilePic").src = imageUrl;
    
        alert("✅ Foto de perfil atualizada!");
      } catch (error) {
        console.error("Erro no upload:", error);
        alert("❌ Erro ao atualizar imagem");
      }
    });
});

