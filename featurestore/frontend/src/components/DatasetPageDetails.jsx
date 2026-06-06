import React from "react";
import "./DatasetPageDetails.css";

export default function DatasetPageDetails() {

    return (
        <aside className="dataset-details">

            <h1 className="titulo-dataset">
                brasileirao-2025
            </h1>

            <div className="divAutor">
                <p className="metadata">Autor: renanjfa</p>
                <p className="metadata">Created on: 20/06/2023</p>
                <p className="metadata">Hora: 12:00</p>
            </div>

            <section className="info-section">
                <h3>Descrição:</h3>

                <p className="descricao-text">
                    Dados e estatísticas do Campeonato Brasileiro
                    2025 contendo gols, jogos, confrontos,
                    cartões, classificação e demais informações.
                </p>
            </section>

            <section className="info-section">
                <h3>Fontes:</h3>

                <p className="descricao-text">
                    Kaggle, Globoplay, UOL, SBT, Premiere
                </p>
            </section>

        </aside>
    )
}