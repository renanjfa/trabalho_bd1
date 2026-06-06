import React from "react";
import "./style/VersaoDetalhes.css";

export default function VersaoDetalhes() {
    
    return (
        <section className="details">

            {/* OBS: RECEBER TITULO DO BANCO */}
            <div>
                <strong className="titulo-dataset" >versao3</strong>
            </div>

            {/* OBS: RECEBER AUTOR e CREATED ON DO BANCO */}
            <div className="gap-author-data">
                <p className="autor-nome">
                    <strong className="created">Autor:</strong> jjtorres
                </p>

                <p className="created">
                    <strong>Criado em: </strong>20/06/2023
                </p>

                <p className="created">
                    <strong>Baseado na versao: </strong>versao1
                </p>
            </div>

            {/* OBS: RECEBER DESCRICAO e FONTES DO BANCO */}
            <div className="gap-descricao-fontes">
                <h3>Tabela Features</h3>

                
            </div>

            {/* OBS:  */}
            <div>

                <button className="versions-btn">
                    Baixar Versao
                </button>
                <span>
                    <button className="editar-versao">
                        Editar/Criar Versao
                    </button>
                </span>
            </div>

        </section>
    )
}