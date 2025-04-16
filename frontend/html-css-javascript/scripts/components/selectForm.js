export function createSelectForm() {
    const genreContainer = document.getElementById("favoriteGenre");

    genreContainer.innerHTML =  `
    <form id="genreForm" class="d-flex align-items-center gap-2">
      <select class="form-select form-select-sm w-auto" id="genre" required>
        <option value="">Selecionar gênero favorito...</option>
        <option value="FICTION">Ficção 🛸</option>
        <option value="FANTASY">Fantasia 🧙‍♂️</option>
        <option value="MYSTERY">Mistério 🕵️‍♀️</option>
        <option value="THRILLER">Terror 👻</option>
        <option value="HISTORY">História 🏺</option>
        <option value="ROMANCE">Romance ❤️</option>
        <option value="ADVENTURE">Aventura 🗺️</option>
        <option value="SCIENCE">Ciência 🔬</option>
        <option value="PHILOSOPHY">Filosofia 🧠</option>
      </select>
      <button type="submit" class="btn btn-secondary fw-bold btn-sm badge btnGenre">Salvar</button>
    </form>
  `;
}