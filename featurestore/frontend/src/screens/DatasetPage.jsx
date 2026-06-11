import React from "react";
import { useState } from "react";
import "./style/DatasetPage.css";

import Header from "../components/Header";
import DatasetPageDetails from '../components/DatasetPageDetails';
import ListaVersoes from "../components/ListaVersoes";
import VersaoDetalhes from "../components/VersaoDetalhes";
import CriarVersaoPopUp from "../components/CriarVersaoPopUp";


export default function DatasetPage() {

    const [popupVersaoAberto, setPopupVersaoAberto] = useState(false);
    const [selectedVersaoId, setSelectedVersaoId] = useState(null);

    const versoes = [
        {
        id: 1,
        nome: "versao1",
        descricao:
            "Dataset com samples de textos escritos por humano ou gerados por IAs generativas.",
        autor: "renatjfa",
        created: "21/05/2026"
        },
        {
        id: 2,
        nome: "versao2",
        descricao:
            "Dados e estatísticas Brasileirão 2025, gols, jogos, confrontos, cartões, dados ...",
        autor: "jtorres",
        created: "21/05/2026"
        },
        {
        id: 3,
        nome: "versao3",
        descricao:
            "Dataset com samples de textos escritos por humano ou gerados por IAs generativas.",
        autor: "renatjfa",
        created: "21/05/2026"
        },
    ];

    return (
        <div className="container">
        
            <Header/>
        
            <div className="content">
                    
                <DatasetPageDetails/>

                <ListaVersoes versoes={versoes} selectedVersaoId={selectedVersaoId} onSelectVersao={setSelectedVersaoId}/>

                <VersaoDetalhes onCriarVersao={() => setPopupVersaoAberto(true)}/>

                <CriarVersaoPopUp
                    aberto={popupVersaoAberto}
                    onClose={() => setPopupVersaoAberto(false)}
                    onBack={() => setPopupVersaoAberto(false)}
                    onSubmit={(novaVersao) => {
                        console.log(novaVersao);

                        // chamada para API aqui

                        setPopupVersaoAberto(false);
                    }}
                />
            
            </div>
        
        </div>
    )
}

