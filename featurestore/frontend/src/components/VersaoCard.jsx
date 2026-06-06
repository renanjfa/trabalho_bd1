import React from "react";
import "./style/VersaoCard.css";

export default function VersaoCard({ versao }) {

    return(
        <div
            key={versao.id}
            className={`versao-card ${
                versao.selecionado ? "selected" : ""
            }`}
            >
            <h4 className="nome-versao">{versao.nome}</h4>

            <div className="footer-card">
                <small className="autor">
                <strong>Autor: </strong>
                <span className="nome-autor">{versao.autor}</span>
                </small>

                <small className="data-created">
                {versao.created}
                </small>
            </div>
        </div>
    )
}