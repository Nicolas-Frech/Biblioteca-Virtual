document.addEventListener("DOMContentLoaded", () => {
    const bookList = document.getElementById("book-list");
    const searchInput = document.getElementById("search");
    const searchBtn = document.getElementById("searchBtn");

    async function fetchBooks(searchTerm = "") {
        try {
            let url = "http://localhost:8080/book";

            if (searchTerm) {
                url += `/${encodeURIComponent(searchTerm)}`;
            }

            const response = await fetch(url);
            const data = await response.json();

            console.log("Resposta da API:", data);
            displayBooks(data.content);
        } catch (error) {
            console.error("Erro ao buscar livros:", error);
        }
    }

    function displayBooks(books) {
        bookList.innerHTML = "";
        
        const row = document.createElement("div");
        row.className = "row g-3";
    
        books.forEach(book => {
            const col = document.createElement("div");
            col.className = "col-md-3";
    
            col.innerHTML = `
                <div class="card h-100 shadow-sm">
                    <img src="${book.cover}" class="card-img-top img-fluid book-cover" alt="Capa de ${book.title}">
                    <div class="card-body">
                        <h5 class="card-title">${book.title}</h5>
                        <p class="card-text">${book.authorName}</p>
                    </div>
                </div>
            `;
    
            row.appendChild(col);
        });
    
        bookList.appendChild(row);
    }

    searchBtn.addEventListener("click", () => {
        const searchTerm = searchInput.value.trim();
        fetchBooks(searchTerm);
    });

    fetchBooks();
});
