import React, { useState, useEffect} from "react";
import "./style/Datasets.css";

import Header from "../components/Header";
import SideBar from "../components/SideBar";
import DetalhesDataset from "../components/DetalhesDataset";
import ListaDatasets from "../components/ListaDatasets";
import { listarDatasets } from "../services/datasetservice";

export default function Datasets() {

  const [selectedDatasetId, setSelectedDatasetId] = useState(null);

  const [datasets, setDatasets]= useState([]);

  useEffect(()=>{
    async function carregar(){
      try {
        const data = await listarDatasets();
        setDatasets(data);
      }catch(e){
        console.error(e.message);
      }
    }
    carregar();
  },[]);
  const datasetSelecionado = datasets.find(d=> d.id === selectedDatasetId);

  return (
    <div className="container">

      <Header/>

      <div className="content">

        <SideBar activeSection={"datasets"}/>

        <ListaDatasets datasets={datasets} buttonAdd={false} section={"Datasets"} 
                      selectedDatasetId={selectedDatasetId} onSelectDataset={setSelectedDatasetId}/>

        <DetalhesDataset dataset={datasetSelecionado}/>

      </div>

    </div>
  );
}

