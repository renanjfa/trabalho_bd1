import { api } from "./api";

export async function listarVersoesPorDataSet(idDataset){
    return api("/versoes/dataset/"+ idDataset);
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

export async function downloadVersao(idVersao){
    const token = localStorage.getItem("token");
    const response = await fetch("http://localhost:8080/api/versoes/" + idVersao + "/download", {
        headers: { "Authorization": "Bearer " + token },
    });
    if(!response.ok){
        throw new Error("Erro ao baixar versao");
    }
    const blob = await response.blob();
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = response.headers.get("Content-Disposition")?.split("filename=")[1]?.replace(/"/g, "") || "versao.csv";
    a.click();
    window.URL.revokeObjectURL(url);
}
