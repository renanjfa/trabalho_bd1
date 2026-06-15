import React from "react";
import { useNavigate } from "react-router-dom";
import "./style/DetalhesDataset.css";

export default function DetalhesDataset({dataset}) {

    const navigate = useNavigate();

    if(!dataset){
            return(
                <section className="details">
                    <p>Selecione um dataset para ver os detalhes!</p>
                </section>
            );
        }
    return(
        
        <section className="details">

            <div>
                <strong className="titulo-dataset">{dataset.nome_dataset}</strong>
            </div>

            <div className="gap-author-data">
                <p className="autor-nome">
                    <strong className="created">Autor: </strong>{dataset.nome_usuario}
                </p>

                <p className="created">
                    <strong>Criado em: </strong>{new Date(dataset.data).toLocaleDateString("pt-BR")}
                    <span>, {dataset.hora?.slice(0, 5)}</span>
                </p>
            </div>

            <div className="gap-descricao-fontes">
                <h3>Descrição:</h3>

                <p className="descricao-text" >
                    {dataset.descricao}
                </p>

                <h3>Fontes:</h3>

                <p className="descricao-text">
                    {dataset.fontes ? dataset.fontes.join(", ") : "Nenhuma fonte"}
                </p>
            </div>

            <button className="versions-btn" onClick={() => navigate("/dataset-page/" + dataset.id)}>
                Inspecionar Versões
            </button>

        </section>
    )
}