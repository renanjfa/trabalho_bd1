import React, {useState} from "react";
import "./style/ListaDatasets.css";
import DatasetCard from "./DatasetCard";

export default function ListaDatasets({ datasets, buttonAdd, section, onAddClick, selectedDatasetId, onSelectDataset }) {

    const [search, setSearch] = useState("");

    const filteredDatasets = datasets.filter((dataset) => {
      const term = search.trim().toLowerCase();

      return (
        dataset.nome_dataset?.toLowerCase().includes(term) ||
        dataset.email_usuario?.toLowerCase().includes(term)
      );
    });

    return(
        <section className="datasets">

          <div className="section-header">
            <strong>{section}</strong>

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
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />

          <div className="dataset-list">

            {filteredDatasets.length > 0 ? (
              filteredDatasets.map((dataset) => (
                <DatasetCard key={dataset.id} dataset={dataset} selected={dataset.id === selectedDatasetId} onClick={() => onSelectDataset(dataset.id)} />
              ))
            ) : (
              <p className="no-results">
                Nenhum dataset encontrado!
              </p>
            )}

          </div>

        </section>
    )
}