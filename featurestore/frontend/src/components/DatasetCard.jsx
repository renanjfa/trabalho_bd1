import React from "react";
import "./style/DatasetCard.css";

export default function DatasetCard({ dataset, onClick, selected }) {

    return(
        <button
            key={dataset.id}
            className={`dataset-card ${selected ? "selected" : ""}`}
            onClick={onClick}
            >
            <h4 className="nome-dataset">{dataset.nome_dataset}</h4>

            <small className="autor">
                <strong>Autor: </strong>

                {dataset.nome_usuario != null ? (

                    <span className="nome-autor">{dataset.nome_usuario}</span>
                ) : (
                    <span className="nome-autor">nome de usuario nulo</span>
                )}
            </small>
        </button>
    )
}