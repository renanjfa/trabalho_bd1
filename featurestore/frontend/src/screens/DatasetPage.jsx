import React from "react";
import "./DatasetPage.css";

import Header from "../components/Header";
import DatasetPageDetails from '../components/DatasetPageDetails';

import DetalhesDataset from "../components/DetalhesDataset";
import ListaDatasets from "../components/ListaDatasets";

export default function DatasetPage() {

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
                    
                <DatasetPageDetails/>
            
            </div>
        
        </div>
    )
}

