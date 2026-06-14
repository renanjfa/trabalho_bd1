import { api } from "./api";

export async function listarVersoesPorDataSet(idDataset){
    return api("/versoes/datasets/"+ idDataset);
}

export async function criarVersao(id_dataset, nome_versao, descricao, csv, id_versao_base, features){
    return api("/versoes",{
        method: "POST",
        body: JSON.stringify({
            id_dataset,
            nome_versao,
            descricao,
            csv,
            id_versao_base,
            features,
        }),
    });
}
