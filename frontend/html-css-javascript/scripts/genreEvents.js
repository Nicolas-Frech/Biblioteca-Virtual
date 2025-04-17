import { createSelectForm } from "./components/selectForm.js";
import { renderGenreDisplay } from "./components/genreDisplay.js";
import { getGenreName } from "./utils/formatters.js";
import { UserService } from "./services/userService.js";
import { CONFIG } from "./services/config.js";

const userService = new UserService(CONFIG.API_URL);

export function renderGenreForm() {
  createSelectForm();
  document.getElementById("genreForm").addEventListener("submit", handleGenreSubmit);
}

async function handleGenreSubmit(e) {
  e.preventDefault();
  const selectedGenre = document.getElementById("genre").value;
  if (!selectedGenre) return;

  try {
    await userService.updateFavoriteGenre(selectedGenre);
    renderGenreDisplay(selectedGenre, renderGenreForm);
  } catch (err) {
    console.error("Erro ao salvar gênero favorito:", err);
    alert("❌ Erro ao salvar gênero.");
  }
}
