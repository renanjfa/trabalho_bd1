import React from "react";
import { useNavigate } from "react-router-dom";
import "./style/DatasetPageDetails.css";

import { Undo2 } from 'lucide-react';

export default function DatasetPageDetails({dataset}) {

    const navigate = useNavigate();

    if (!dataset) {
        return (
            <aside className="dataset-details">
                <p>Carregando...</p>
            </aside>
        );
    }

    return (
        <aside className="dataset-details">

            <h1 className="titulo-dataset">
                {dataset.nome_dataset}
            </h1>

            <div className="divAutor">
                <p className="metadata">Autor: {dataset.email_usuario}</p>
                <p className="metadata">Criado em: {dataset.data}</p>
                <p className="metadata">Hora: {dataset.hora}</p>
            </div>

            <section className="info-section">
                <h3>Descrição:</h3>

                <p className="descricao-text">
                    {dataset.descricao}
                </p>
            </section>

            <section className="info-section">
                <h3>Fontes:</h3>

                <p className="descricao-text">
                    {dataset.fontes ? dataset.fontes.join(", ") : "Nenhuma fonte"}
                </p>
            </section>

            <button className="goBack" onClick={() => navigate(-1)}>
                <Undo2/>
            </button>

        </aside>
    )
}