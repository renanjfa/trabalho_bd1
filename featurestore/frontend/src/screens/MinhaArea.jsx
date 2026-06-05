import React from "react";
import "./MinhaArea.css";

import Header from "../components/Header";

export default function MinhaArea() {
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

  return (
    <div className="container">

      <Header/>

      <div className="content">

        {/* Sidebar */}
        <aside className="sidebar">

          <div className="profile">
            <img
              src="https://via.placeholder.com/80"
              alt="Usuário"
            />

            <h3>Sundar Gurung</h3>
            <p>sundargurung36@gmail.com</p>
          </div>

          <nav>
            <button>DASHBOARD</button>
            <button>DATASETS</button>
            <button className="active">MINHA ÁREA</button>
            <button>PERFIL</button>
          </nav>

          <button className="logout">
            Logout
          </button>

        </aside>

        {/* Lista de datasets */}
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

        {/* Detalhes */}
        <section className="details">

          <h1>brasileirao-2025</h1>

          <p>
            <strong>Autor:</strong> jtorres
          </p>

          <p className="created">
            Created on: 20/06/2023
          </p>

          <h3>Descrição:</h3>

          <p>
            Dados e estatísticas do Campeonato Brasileiro
            2025 contendo gols, jogos, confrontos,
            cartões, classificação e demais informações.
          </p>

          <h3>Fontes:</h3>

          <p>
            Kaggle, Globoplay, UOL, SBT, Premiere
          </p>

          <button className="versions-btn">
            Inspecionar Versões
          </button>

        </section>

      </div>

    </div>
  );
}

