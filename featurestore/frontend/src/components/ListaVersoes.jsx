import React from "react";
import "./style/ListaVersoes.css";

import VersaoCard from "./VersaoCard";

export default function ListaVersoes({ versoes, selectedVersaoId, onSelectVersao  }) {

    return(
        <section className="datasets">

          <div className="section-header">
            <h2>Versoes</h2>

          </div>

          <input
            type="text"
            placeholder="Buscar"
            className="search"
          />

          <div className="dataset-list">

            {versoes.map((versao) => (
              <VersaoCard key={versao.id_versao} versao={versao} selected={versao.id_versao === selectedVersaoId} onClick={() => onSelectVersao(versao.id_versao)}/>
            ))}

          </div>

        </section>
    )
}