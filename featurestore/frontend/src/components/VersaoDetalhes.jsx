import React from "react";
import "./style/VersaoDetalhes.css";
import { downloadVersao } from "../services/versaoService";

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
                    <strong className="created">Autor:</strong> {versao.nome_usuario}
                </p>

                <p className="created">
                    <strong>Criado em: </strong>{new Date(versao.data).toLocaleDateString("pt-BR")}
                    <span>, {versao.hora?.slice(0, 5)}</span>
                </p>

                {versao.id_versao_base != null && versao.id_versao_base !== 0 && (
                    <p className="created">
                        <strong>Baseado na versão: </strong>{versao.nome_versao_base}
                    </p>
                )}
            </div>

            <div className="gap-descricao-fontes">
                <h3>Descrição</h3>
                <p className="descricao-text" >
                    {versao.descricao}
                </p>

                <h3>Features</h3>

                {versao.features && versao.features.length > 0 ? (
                    <div className="features-table-container">
                        <table className="features-table">
                            <thead>
                                <tr>
                                    <th>Nome</th>
                                    <th>Tipo</th>
                                    <th>Descrição</th>
                                </tr>
                            </thead>

                            <tbody>
                                {versao.features.map((f) => (
                                    <tr key={f.id_feature}>
                                        <td>{f.nome_feature}</td>
                                        <td>{f.tipo}</td>
                                        <td>{f.descricao}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                ) : (
                    <p>Nenhuma feature</p>
                )}

                
            </div>

            {/* OBS:  */}
            <div>

                <button className="versions-btn" onClick={async ()=> {
                    try{
                        await downloadVersao(versao.id_versao);
                    }catch (e){
                        console.error(e.message);
                    }
                }}>
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