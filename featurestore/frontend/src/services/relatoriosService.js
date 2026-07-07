import { api } from "./api";

export async function getInfoSistema() {
    return api("/relatorios/sistema");
}

export async function getVersoesDatasets() {
    return api("/relatorios/versoes-datasets");
}

export async function getAcessosDownloadsDataset(idDataset) {
    return api("/relatorios/acessos-downloads/" + idDataset);
}

export async function getDatasetsMais(orderBy = "acessos"){
    return api("/relatorios/datasets-mais?orderBy=" + orderBy);
}

export async function getContribuicoes(){
    return api("/relatorios/contribuicoes");
}

export async function getHistoricoDataset(idDataset){
    return api("/relatorios/historicos/" + idDataset);
}