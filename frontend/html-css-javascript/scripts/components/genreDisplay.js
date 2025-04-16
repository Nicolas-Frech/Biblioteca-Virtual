import { getGenreName } from "../utils/formatters.js";

export function renderGenreDisplay(genre, onChangeClick) {
  const container = document.getElementById("favoriteGenre");

  container.innerHTML = `
    <div class="d-flex align-items-center gap-2">
      <p class="mt-3 genre">🎭 Gênero Favorito:</p>
      <span class="favorite-genre">${getGenreName(genre)}</span>
      <button type="button" class="btn btn-secondary fw-bold btn-sm badge btnGenre" id="change">Trocar</button>
    </div>
  `;

  document.getElementById("change").addEventListener("click", onChangeClick);
}
