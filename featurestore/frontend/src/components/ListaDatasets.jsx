import React from "react";
import "./ListaDatasets.css";
import DatasetCard from "./DatasetCard";

export default function ListaDatasets() {

  const datasets = [
    {
      id: 1,
      nome: "human-vs-ai text",
      descricao:
        "Dataset com samples de textos escritos por humano ou gerados por IAs generativas.aksEUSOUORENANAjdfhaskhgvakjsdvhckajsdhbcajdhbckajdbvhkajsvbdhkasjdhbkasjdchbkajdschbkasjdcbhkasjd ASDKJAGSDKJAGHSDKHAVSDKHJAVSDGHASVDJGHASVDJGHASVDJAGHSDVJAGSHDVJAGSHDVJAGSHDVJAGSHDVAJGSHvcbhkasjdbhcksjdbch",
      autor: "renanjfa",
    },
    {
      id: 2,
      nome: "brasileirao-2025",
      descricao:
        "Dados e estatísticas Brasileirão 2025, gols, jogos, confrontos, cartões, dados ...",
      autor: "jjtorres",
      selecionado: true,
    },
    {
      id: 3,
      nome: "human-vs-ai text",
      descricao:
        "Dataset com samples de textos escritos por humano ou gerados por IAs generativas.",
      autor: "renanjfa",
    },
    {
      id: 3,
      nome: "human-vs-ai text",
      descricao:
        "Dataset com samples de textos escritos por humano ou gerados por IAs generativas.",
      autor: "renanjfa",
    },
    {
      id: 3,
      nome: "human-vs-ai text",
      descricao:
        "Dataset com samples de textos escritos por humano ou gerados por IAs generativas.",
      autor: "renanjfa",
    },
    {
      id: 3,
      nome: "human-vs-ai text",
      descricao:
        "Dataset com samples de textos escritos por humano ou gerados por IAs generativas.",
      autor: "renanjfa",
    },
    {
      id: 3,
      nome: "human-vs-ai text",
      descricao:
        "Dataset com samples de textos escritos por humano ou gerados por IAs generativas.",
      autor: "renanjfa",
    },
    {
      id: 3,
      nome: "human-vs-ai text",
      descricao:
        "Dataset com samples de textos escritos por humano ou gerados por IAs generativas.",
      autor: "renanjfa",
    },
    {
      id: 3,
      nome: "human-vs-ai text",
      descricao:
        "Dataset com samples de textos escritos por humano ou gerados por IAs generativas.",
      autor: "renanjfa",
    },
    {
      id: 3,
      nome: "human-vs-ai text",
      descricao:
        "Dataset com samples de textos escritos por humano ou gerados por IAs generativas.",
      autor: "renanjfa",
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
              <DatasetCard dataset={dataset}/>
            ))}

          </div>

        </section>
    )
}