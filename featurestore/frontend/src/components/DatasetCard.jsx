import React from "react";
import "./style/DatasetCard.css";

export default function DatasetCard({ dataset, onClick, selected }) {

    return(
        <button
            key={dataset.id}
            className={`dataset-card ${selected ? "selected" : ""}`}
            onClick={onClick}
            >
            <h4 className="nome-dataset">{dataset.nome}</h4>

            <small className="autor">
                <strong>Autor: </strong>
                <span className="nome-autor">{dataset.autor}</span>
            </small>
        </button>
    )
}