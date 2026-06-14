import {api} from "./api";

export async function registrar(email, nome_usuario, senha){
    return api("/registro",{
        method: "POST",
        body: JSON.stringify({email,nome_usuario,senha}),
    });
}

export async function login(email,senha){
    const data = await api("/login", {
        method: "POST",
        body: JSON.stringify({email,senha}),
    });
    localStorage.setItem("token", data.token);
    return data;
}

export function logout(){
    localStorage.removeItem("token");
}

export function getToken(){
    return localStorage.getItem("token");
}