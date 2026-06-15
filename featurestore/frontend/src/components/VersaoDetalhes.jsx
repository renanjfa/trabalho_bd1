import React from "react";
import "./style/VersaoDetalhes.css";

export default function VersaoDetalhes({ versao, onCriarVersao }) {
    if (!versao) {
        return (
            <section className="details">
                <p>Selecione uma versão para ver os detalhes.</p>
            </section>
        );
    }
    return (
        <section className="details">

            <div>
                <strong className="titulo-dataset" >{versao.nome_versao}</strong>
            </div>

            {/* OBS: RECEBER AUTOR e CREATED ON DO BANCO */}
            <div className="gap-author-data">
                <p className="autor-nome">
                    <strong className="created">Autor:</strong> {versao.email_usuario}
                </p>

                <p className="created">
                    <strong>Criado em: </strong>{versao.data}
                </p>

                {versao.id_versao_base && (
                    <p className="created">
                        <strong>Baseado na versão: </strong>{versao.id_versao_base}
                    </p>
                )}
            </div>

            <div className="gap-descricao-fontes">
                <h3>Tabela Features</h3>
                {versao.features && versao.features.length > 0 ? (
                    <ul>
                        {versao.features.map((f) => (
                            <li key={f.id_feature}>{f.nome_feature} - {f.tipo}</li>
                        ))}
                    </ul>
                ) : (
                    <p>Nenhuma feature</p>
                )}

                
            </div>

            {/* OBS:  */}
            <div>

                <button className="versions-btn">
                    Baixar Versao
                </button>
                <span>
                    <button className="editar-versao" onClick={()=> onCriarVersao(versao.id_versao)}>
                        Editar/Criar Versao
                    </button>
                </span>
            </div>

        </section>
    )
}