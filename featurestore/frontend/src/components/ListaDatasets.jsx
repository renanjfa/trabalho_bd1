import React from "react";
import "./ListaDatasets.css";
import DatasetCard from "./DatasetCard";

export default function ListaDatasets({ datasets, buttonAdd, section }) {

    return(
        <section className="datasets">

          <div className="section-header">
            <h2>{section}</h2>

            {buttonAdd && (
              <button className="add-btn">
                Adicionar Dataset
              </button>
            )}
          </div>

          <input
            type="text"
            placeholder="Buscar"
            className="search"
          />

          <div className="dataset-list">

            {datasets.map((dataset) => (
              <DatasetCard dataset={dataset}/>
            ))}

          </div>

        </section>
    )
}