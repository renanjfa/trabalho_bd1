import React from "react";
import { useState } from "react";
import "./style/MinhaArea.css";

import Header from "../components/Header";
import SideBar from "../components/SideBar";
import DetalhesDataset from "../components/DetalhesDataset";
import ListaDatasets from "../components/ListaDatasets";
import AdicionarDatasetPopUp from "../components/AdicionarDatasetPopUp";
import CriarVersaoPopUp from "../components/CriarVersaoPopUp";

export default function MinhaArea() {

  const [popupDatasetAberto, setPopupDatasetAberto] = useState(false);
  const [popupVersaoAberto, setPopupVersaoAberto] = useState(false);

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

        <SideBar activeSection={"minha-area"} />

        <ListaDatasets  datasets={datasets} buttonAdd={true} section={"Minha Área"} onAddClick={() => setPopupDatasetAberto(true)}/>

        <AdicionarDatasetPopUp
            aberto={popupDatasetAberto}
            onClose={() => setPopupDatasetAberto(false)}
            onAbrirVersao={() => {
                setPopupDatasetAberto(false);
                setPopupVersaoAberto(true);
            }}
        />

        <CriarVersaoPopUp
            aberto={popupVersaoAberto}
            onClose={() => {
                setPopupVersaoAberto(false);
                setPopupDatasetAberto(false);
            }}
            onBack={() => {
                setPopupVersaoAberto(false);
                setPopupDatasetAberto(true);
            }}
            onSubmit={(novaVersao) => {
                console.log(novaVersao);
                setPopupVersaoAberto(false);
                setPopupDatasetAberto(true);
            }}
        />

        <DetalhesDataset/>

      </div>

    </div>
  );
}

