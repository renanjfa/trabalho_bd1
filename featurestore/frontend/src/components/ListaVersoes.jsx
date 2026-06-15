import React, {useState} from "react";
import "./style/ListaVersoes.css";

import VersaoCard from "./VersaoCard";

export default function ListaVersoes({ versoes, selectedVersaoId, onSelectVersao  }) {

    const [search, setSearch] = useState("");
    
    const filteredVersoes = versoes.filter((versao) => {
      const term = search.trim().toLowerCase();

      return (
        versao.nome_versao?.toLowerCase().includes(term) ||
        versao.email_usuario?.toLowerCase().includes(term)
      );
    });

    return(
        <section className="datasets">

          <div className="section-header">
            <strong>Versões</strong>

          </div>

          <input
            type="text"
            placeholder="Buscar"
            className="search"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />

          <div className="dataset-list">

            {filteredVersoes.length > 0 ? (
              filteredVersoes.map((versao) => (
                <VersaoCard key={versao.id_versao} versao={versao} selected={versao.id_versao === selectedVersaoId} onClick={() => onSelectVersao(versao.id_versao)}/>
              ))
            ) : (
              <p className="no-results">
                Nenhuma versão encontrada!
              </p>
            )}

          </div>

        </section>
    )
}