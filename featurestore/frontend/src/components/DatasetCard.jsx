import React from "react";
import "./DatasetCard.css";

export default function DatasetCard({ dataset }) {

    return(
        <div
            key={dataset.id}
            className={`dataset-card ${
                dataset.selecionado ? "selected" : ""
            }`}
            >
            <h4 className="nome-dataset">{dataset.nome}</h4>

            <small className="autor">
                <strong>Autor: </strong>
                <span className="nome-autor">{dataset.autor}</span>
            </small>
        </div>
    )
}