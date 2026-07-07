import React, {useState, useEffect} from "react";
import { deletarDataset, listarMeusDatasets } from "../services/datasetservice";
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

  const [selectedDatasetId, setSelectedDatasetId] = useState(null);

  const [datasets, setDatasets] = useState([]);
  const [datasetParaVersao, setdatasetParaVersao] = useState(null);

  useEffect(()=>{
    carregarDatasets();
  },[]);

  async function carregarDatasets() {
      try {
          const data = await listarMeusDatasets();
          setDatasets(data);
      } catch (e) {
          console.error(e.message);
      }
  }
  const datasetSelecionado = datasets.find(d => d.id === selectedDatasetId);

  return (
    <div className="container">

      <Header/>

      <div className="content">

        <SideBar activeSection={"minha-area"} />

        <ListaDatasets  datasets={datasets} buttonAdd={true} section={"Minha Área"} onAddClick={() => setPopupDatasetAberto(true)}
                        selectedDatasetId={selectedDatasetId} onSelectDataset={setSelectedDatasetId}/>

        <AdicionarDatasetPopUp
            aberto={popupDatasetAberto}
            onClose={() => setPopupDatasetAberto(false)}
            onDatasetCriado={(novoDataset) => {
                setdatasetParaVersao(novoDataset)
                setPopupDatasetAberto(false);
                setPopupVersaoAberto(true);
            }}
        />

        <CriarVersaoPopUp
            aberto={popupVersaoAberto}
            idDataset={datasetParaVersao?.id}
            onClose={async () => {
                if(datasetParaVersao?.id){
                  try {
                    await deletarDataset(datasetParaVersao.id);
                  }catch (e) {}
                }
                setPopupVersaoAberto(false);
                setPopupDatasetAberto(false);
                setdatasetParaVersao(null);
            }}
            onBack={async () => {
              if(datasetParaVersao?.id){
                try { 
                  await deletarDataset(datasetParaVersao.id);
                }catch (e) {}
              }
                setPopupVersaoAberto(false);
                setPopupDatasetAberto(true);
                setdatasetParaVersao(null);
            }}
            onSubmit={() => {
                setPopupVersaoAberto(false);
                setdatasetParaVersao(null);
                carregarDatasets();
              }}
        />

        <DetalhesDataset dataset={datasetSelecionado}/>

      </div>

    </div>
  );
}

