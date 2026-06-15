import React from "react";
import "./style/VersaoCard.css";

export default function VersaoCard({ versao, onClick, selected }) {

    return(
        <button
            key={versao.id_versao}
            className={`versao-card ${selected ? "selected" : ""}`}
            onClick={onClick}
            >
            <h4 className="nome-versao">{versao.nome_versao}</h4>

            <div className="footer-card">
                <small className="autor">
                <strong>Autor: </strong>
                <span className="nome-autor">{versao.nome_usuario}</span>
                </small>

                <small className="data-created">
                {new Date(versao.data).toLocaleDateString("pt-BR")}
                </small>
            </div>
        </button>
    )
}