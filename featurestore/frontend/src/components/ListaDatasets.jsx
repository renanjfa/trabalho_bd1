import React from "react";
import "./style/ListaDatasets.css";
import DatasetCard from "./DatasetCard";

export default function ListaDatasets({ datasets, buttonAdd, section, onAddClick, selectedDatasetId, onSelectDataset }) {

    return(
        <section className="datasets">

          <div className="section-header">
            <h2>{section}</h2>

            {buttonAdd && (
              <button className="add-btn" onClick={onAddClick}>
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
              <DatasetCard key={dataset.id} dataset={dataset} selected={dataset.id === selectedDatasetId} onClick={() => onSelectDataset(dataset.id)}/>
            ))}

          </div>

        </section>
    )
}