import React from "react";
import "./DetalhesDataset.css";

export default function DetalhesDataset() {

    return(
        <section className="details">

            {/* OBS: RECEBER TITULO DO BANCO */}
            <div>
                <strong className="titulo-dataset" >brasileirao-2025</strong>
            </div>

            {/* OBS: RECEBER AUTOR e CREATED ON DO BANCO */}
            <div className="gap-author-data">
                <p className="autor-nome">
                    <strong className="created">Autor:</strong> jjtorres
                </p>

                <p className="created">
                    <strong>Created on: </strong>20/06/2023
                </p>
            </div>

            {/* OBS: RECEBER DESCRICAO e FONTES DO BANCO */}
            <div className="gap-descricao-fontes">
                <h3>Descrição:</h3>

                <p className="descricao-text" >
                    Dados e estatísticas do Campeonato Brasileiro
                    2025 contendo gols, jogos, confrontos,
                    cartões, classificação e demais informações.
                </p>

                <h3>Fontes:</h3>

                <p>
                    Kaggle, Globoplay, UOL, SBT, Premiere
                </p>
            </div>

            {/* OBS:  */}
            <button className="versions-btn">
                Inspecionar Versões
            </button>

        </section>
    )
}