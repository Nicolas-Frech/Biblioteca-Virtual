import { UserService } from "../../services/userService.js";
import { createBookCard } from "../../components/bookCard.js";
import { renderGenreForm } from "../../genreEvents.js";
import { renderGenreDisplay } from "../../components/genreDisplay.js";
import { CONFIG } from "../../services/config.js";

const userService = new UserService(CONFIG.API_URL);

document.addEventListener("DOMContentLoaded", async () => {
  const profileInfo = document.getElementById("profile-info");
  const bookList = document.getElementById("bookList");

  try {
    const user = await userService.fetchUser();

    if (user.profileImage) {
      const fileName = user.profileImage.split("/").pop().split("\\").pop();
      document.querySelector("#profile-info img").src = `http://localhost:8080/uploads/${fileName}`;
    }

    if (user.userRole === "ADMIN") renderAdminControls();

    if (!user.favoriteGenre) {
      renderGenreForm();
    } else {
      renderGenreDisplay(user.favoriteGenre, renderGenreForm);
    }

    renderProfileInfo(user);
    renderBookList(user.myLibrary || []);
  } catch (error) {
    console.error("Erro:", error);
    profileInfo.innerHTML = `<div class="alert alert-danger">Erro ao carregar os dados do perfil.</div>`;
  }
});

function renderAdminControls() {
  document.getElementById("adminAuthor").innerHTML = `
    <a href="deletarAutor.html" class="btn btn-outline-dark fw-bold mt-2 mb-2">Excluir Autor 🗑️</a>
    <a href="cadastrarAutor.html" class="btn btn-outline-dark fw-bold mt-2 mb-2">Cadastrar Autor ✍</a>
  `;
  document.getElementById("adminBook").innerHTML = `
    <a href="deletarLivro.html" class="btn btn-outline-dark fw-bold mt-2 mb-2">Excluir Livro 🗑️</a>
    <a href="cadastrarLivro.html" class="btn btn-outline-dark fw-bold mt-2 mb-2">Cadastrar Livro ✍</a>
  `;
}

function renderProfileInfo(user) {
  document.getElementById("username").textContent = user.username;
  document.getElementById("userEmail").textContent = user.email;
  document.getElementById("userRole").textContent = user.userRole;
}

function renderBookList(books) {
  const bookList = document.getElementById("bookList");
  if (books.length === 0) {
    bookList.innerHTML = `<li class="list-group-item">Nenhum livro adicionado.</li>`;
    return;
  }

  const bookRow = document.createElement("div");
  bookRow.className = "row g-3";

  books.forEach(book => {
    const col = document.createElement("div");
    col.className = "col-sm-12 col-md-3";
    const card = createBookCard(book, {
      showRemoveButton: true,
      onRemove: async () => {
        try {
          await userService.removeBookByTitle(book.title);
          col.remove();
        } catch (err) {
          console.error("Erro ao remover livro:", err);
          alert("❌ Erro ao remover o livro.");
        }
      }
    });

    col.appendChild(card);
    bookRow.appendChild(col);
  });

  bookList.innerHTML = "";
  bookList.appendChild(bookRow);
}
