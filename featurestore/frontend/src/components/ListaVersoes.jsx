import React from "react";
import "./ListaVersoes.css";
import DatasetCard from "./DatasetCard";

export default function ListaVersoes({ versoes }) {

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
              <DatasetCard dataset={versao}/>
            ))}

          </div>

        </section>
    )
}