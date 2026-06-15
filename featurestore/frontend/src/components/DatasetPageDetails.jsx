import React from "react";
import { useNavigate } from "react-router-dom";
import "./style/DatasetPageDetails.css";

import { Undo2 } from 'lucide-react';

export default function DatasetPageDetails({ dataset }) {
    const navigate = useNavigate();

    if (!dataset) {
        return (
            <aside className="dataset-details dataset-details-loading">
                <p>Carregando dados...</p>
            </aside>
        );
    }

    return (
        <aside className="dataset-details">

            <h1 className="titulo-dataset">
                {dataset.nome_dataset}
            </h1>

            <div className="divAutor">
                <p className="metadata"><span><strong>Autor:</strong></span> {dataset.nome_usuario}</p>
                <p className="metadata"><span><strong>Criado em:</strong></span> {new Date(dataset.data).toLocaleDateString("pt-BR")}</p>
                <p className="metadata"><span><strong>Hora:</strong></span> {dataset.hora?.slice(0, 5)}</p>
            </div>

            <section className="info-section">
                <h3>Descrição</h3>
                <p className="descricao-text">
                    {dataset.descricao}
                </p>
            </section>

            <section className="info-section">
                <h3>Fontes</h3>
                <p className="descricao-text">
                    {dataset.fontes && dataset.fontes.length > 0 ? dataset.fontes.join(", ") : "Nenhuma fonte registrada"}
                </p>
            </section>

            <button className="goBack" onClick={() => navigate(-1)} title="Voltar">
                <Undo2 size={20} />
            </button>

        </aside>
    );
}