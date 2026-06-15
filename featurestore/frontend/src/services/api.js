const API_URL = "http://localhost:8080/api";
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
    const text = await response.text();
    const data = text ? JSON.parse(text): {};

    if(!response.ok){
        throw new Error(data.error || "Erro na requisicao");
    }

    return data;
}
