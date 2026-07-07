import { api } from "./api";

export async function registrarAcesso(idDataset) {
    return api("/acessos",{
        method: "POST",
        body: JSON.stringify({id_dataset: idDataset}),
    });
}