document.addEventListener("DOMContentLoaded", () => {
    const bookList = document.getElementById("book-list");
    const searchInput = document.getElementById("search");
    let books = [];

    async function fetchBooks() {
        try {
            const response = await fetch("http://localhost:8080/book");
            const data = await response.json();
            books = data.content;
            displayBooks(books);
        } catch (error) {
            console.error("Erro ao buscar livros:", error);
        }
    }

    function displayBooks(filteredBooks) {
        bookList.innerHTML = "";
        
        const row = document.createElement("div");
        row.className = "row g-3";
    
        filteredBooks.forEach(book => {
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

    searchInput.addEventListener("input", () => {
        const searchTerm = searchInput.value.toLowerCase();
        const filteredBooks = books.filter(book => 
            book.title.toLowerCase().includes(searchTerm) ||
            book.authorName.toLowerCase().includes(searchTerm)
        );
        displayBooks(filteredBooks);
    });

    fetchBooks();
});