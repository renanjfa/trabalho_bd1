import React from "react";
import "./ListaDatasets.css";

export default function ListaDatasets() {

  const datasets = [
    {
      id: 1,
      nome: "human-vs-ai text",
      descricao:
        "Dataset com samples de textos escritos por humano ou gerados por IAs generativas.",
      autor: "renatjfa",
    },
    {
      id: 2,
      nome: "brasileirao-2025",
      descricao:
        "Dados e estatísticas Brasileirão 2025, gols, jogos, confrontos, cartões, dados ...",
      autor: "jtorres",
      selecionado: true,
    },
    {
      id: 3,
      nome: "human-vs-ai text",
      descricao:
        "Dataset com samples de textos escritos por humano ou gerados por IAs generativas.",
      autor: "renatjfa",
    },
  ];

    return(
        <section className="datasets">

          <div className="section-header">
            <h2>Minha Área</h2>

            <button className="add-btn">
              Adicionar Dataset
            </button>
          </div>

          <input
            type="text"
            placeholder="Buscar"
            className="search"
          />

          <div className="dataset-list">

            {datasets.map((dataset) => (
              <div
                key={dataset.id}
                className={`dataset-card ${
                  dataset.selecionado ? "selected" : ""
                }`}
              >
                <h4>{dataset.nome}</h4>

                <p>{dataset.descricao}</p>

                <small>
                  Autor: {dataset.autor}
                </small>
              </div>
            ))}

          </div>

        </section>
    )
}