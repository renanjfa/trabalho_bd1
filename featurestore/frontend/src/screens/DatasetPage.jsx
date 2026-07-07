import React,{useState, useEffect} from "react";
import { useParams } from "react-router-dom";
import { buscarDatasets } from "../services/datasetservice";
import { listarVersoesPorDataSet } from "../services/versaoService";
import { registrarAcesso } from "../services/acessosService";
import "./style/DatasetPage.css";

import Header from "../components/Header";
import DatasetPageDetails from '../components/DatasetPageDetails';
import ListaVersoes from "../components/ListaVersoes";
import VersaoDetalhes from "../components/VersaoDetalhes";
import CriarVersaoPopUp from "../components/CriarVersaoPopUp";
import EstatisticasDatasetPopUp from "../components/EstatisticasDatasetPopUp";


export default function DatasetPage() {
    const {id}= useParams();
    const [dataset, setDatasets]= useState(null);
    const [versoes, setVersoes]= useState([]);
    const [popupVersaoAberto, setPopupVersaoAberto] = useState(false);
    const [selectedVersaoId, setSelectedVersaoId] = useState(null);
    const [versaoBaseId, setVersaoBaseId]= useState(null);
    const [estatisticasAberto, setEstatisticasAberto] = useState(false);

    useEffect(() => {
        carregarDados();
    }, [id]);

    async function carregarDados() {
        try {
            const ds = await buscarDatasets(id);
            setDatasets(ds);
            const vs = await listarVersoesPorDataSet(id);
            setVersoes(vs);
        } catch (e) {
            console.error(e.message);
        }
    }
    const versaoSelecionada = versoes.find(v => v.id_versao === selectedVersaoId);

    return (
        <div className="container">
        
            <Header/>
        
            <div className="content">
                    
                <DatasetPageDetails dataset={dataset} onVerEstatisticas={()=> setEstatisticasAberto(true)}/>

                <ListaVersoes versoes={versoes} selectedVersaoId={selectedVersaoId} onSelectVersao={setSelectedVersaoId}/>

                <VersaoDetalhes versao={versaoSelecionada} onCriarVersao={(idVersao) => {setVersaoBaseId(idVersao); setPopupVersaoAberto(true);}}/>

                <CriarVersaoPopUp
                    aberto={popupVersaoAberto}
                    idDataset= {parseInt(id)}
                    idVersaoBase={versaoBaseId}
                    featuresBase={versoes.find(v=>v.id_versao===versaoBaseId)?.features || []}
                    onClose={() => setPopupVersaoAberto(false)}
                    onBack={() => setPopupVersaoAberto(false)}
                    onSubmit={(novaVersao) => {
                        setPopupVersaoAberto(false);
                        carregarDados();
                    }}
                />
                <EstatisticasDatasetPopUp
                    aberto={estatisticasAberto}
                    onClose={()=> setEstatisticasAberto(false)}
                    idDataset={parseInt(id)}
                />
            </div>
        
        </div>
    )
}

