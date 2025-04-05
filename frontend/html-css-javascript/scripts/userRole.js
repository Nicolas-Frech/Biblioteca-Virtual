export const getUserRole = async () => {
    const token = localStorage.getItem("token");
  
    try {
      const response = await fetch("http://localhost:8080/user/role", {
        method: "GET",
        headers: {
          "Authorization": `Bearer ${token}`,
          "Content-Type": "application/json"
        }
      });
  
      if (!response.ok) {
        throw new Error("Erro ao buscar role");
      }
  
      const role = await response.text();
      return role;
  
    } catch (error) {
      console.error("Erro em getUserRole:", error);
      throw error;
    }
  };