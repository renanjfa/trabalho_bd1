const API_URL = "http://localhost:8080";
export async function api(endpoint, options={}){
    const token= localStorage.getItem("token");

    const headers= {
        "Content-Type": "application/json", ...options.headers,
    };

    if(token){
        headers["Authorization"] = "Bearer " + token;
    }
    const response = await fetch(API_URL + endpoint, {
        ...options,
        headers,
    });

    const data = await response.json();

    if(!response.ok){
        throw new Error(data.error || "Erro na requisicao");
    }

    return data;
}
