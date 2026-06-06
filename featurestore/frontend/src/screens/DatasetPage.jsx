import React from "react";
import "./DatasetPage.css";

import Header from "../components/Header";
import DatasetPageDetails from '../components/DatasetPageDetails';
import ListaVersoes from "../components/ListaVersoes";


export default function DatasetPage() {

    const versoes = [
        {
        id: 1,
        nome: "versao1",
        descricao:
            "Dataset com samples de textos escritos por humano ou gerados por IAs generativas.",
        autor: "renatjfa",
        },
        {
        id: 2,
        nome: "versao2",
        descricao:
            "Dados e estatísticas Brasileirão 2025, gols, jogos, confrontos, cartões, dados ...",
        autor: "jtorres",
        selecionado: true,
        },
        {
        id: 3,
        nome: "versao3",
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

                <ListaVersoes versoes={versoes}/>
            
            </div>
        
        </div>
    )
}

