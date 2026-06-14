import { api } from "./api";

export async function listarDatasets(){
    return api("/datasets");
}

export async function buscarDatasets(id){
    return api("/datasets/" + id);
}

export async function criarDatasets(nome_dataset, descricao, fontes){
    return api("/datasets",{
        method: "POST",
        body: JSON.stringify({nome_dataset, descricao, fontes}),
    });
}

export async function deletarDataset(id){
    return api("/datasets/" + id, {
        method: "DELETE",
    });
}

export async function listarMeusDatasets(){
    return api("/meus-datasets");
}