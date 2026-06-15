import { useState } from "react";
import {UploadCloud, ArrowLeft, X} from "lucide-react";
import { criarVersao } from "../services/versaoService";

export default function CriarVersaoPopUp({aberto, onClose, onSubmit, onBack, idDataset}){
    const [titulo, setTitulo]= useState("");
    const [descricao, setDescricao]= useState("");
    const [idVersaoBase, setIdVersaoBase]= useState(null);
    const [features, setFeatures]= useState([
        {nome: "",tipo: "", descricao: "" },
    ]);
    const [arquivoCsv, setArquivoCsv]= useState(null);

    if(!aberto){
        return null;
    }

    function adicionarFeature(){
        setFeatures([...features, {nome:"",tipo: "",descricao:""}]);
    }

    function atualizarFeature(index, campo, valor){
        const novasFeatures=[...features];
        novasFeatures[index][campo]=valor;
        setFeatures(novasFeatures);
    }
    function removerFeature(index){
        const novasFeatures = features.filter((_,i)=>i !==index);
        setFeatures(novasFeatures);
    }

    function handleDrop(event){
        event.preventDefault();
        const arquivo= event.dataTransfer.files[0];
        if(arquivo){
            setArquivoCsv(arquivo);
        }
    }

    async function handleSubmit(event){
        event.preventDefault();
        try {
            const featuresParaEnviar = features.map(f => ({
                nome_feature: f.nome,
                tipo: f.tipo || "",
                descricao: f.descricao,
            }));
            await criarVersao(idDataset, titulo, descricao, arquivoCsv?.name || null, idVersaoBase, featuresParaEnviar);
            setTitulo("");
            setFeatures([{nome: "",tipo: "", descricao: ""}]);
            setArquivoCsv(null);
            onSubmit();
        } catch (e) {
            console.error(e.message);
        }
    }

    return(
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/60 px-4">
            <div className="w-full max-w-4xl rounded bg-white px-12 py-8 shadow-lg">
                <div className="mb-5 flex items-center justify-between">
                    <div>
                        <h2 className="text-sm font bold text-black">
                            Adicione uma versao nova
                        </h2>
                        <div className="mt-1 h-[2px] w-24 bg-[#ef4b2d]"/>
                    </div>
                    <button type="button" onClick={onBack} className="text-xs font-bold text-black underline hover:text-[#ef4b2d]">
                        <ArrowLeft/>
                    </button>
                </div>
                <form onSubmit={handleSubmit} className="border border-gray-300 px-4 py-5">
                    <div className="mb-4">
                        <label className="mb-2 block text-xs font-bold text-black">
                            Titulo
                        </label>
                        <input type="text" value={titulo} onChange={(event)=> setTitulo(event.target.value)}
                            className="h-8 w-full max-w-md rounded border border-gray-300 px-2 text-sm outline-none focus:border-[#ff5a4f]"/>
                    </div>

                    <div className="mb-4">
                        <label className="mb-2 block text-xs font-bold text-black">
                            Descricao
                        </label>
                        <textarea value={descricao} onChange={(event) => setDescricao(event.target.value)}
                            className="h-20 w-full max-w-md resize-none rounded border border-gray-300 px-2 py-2 text-sm outline-none focus:border-[#ef4b2d]"/>
                    </div>

                    <div className="mb-4">
                        <label className="mb-2 block text-xs font-bold text-black">
                            Features
                        </label>
                        <div className="space-y-2">
                            {features.map((feature,index)=>(
                                <div key={index} className="flex items-center gap-2">
                                    <input type="text" placeholder="nome" value={feature.nome} onChange={(event)=> atualizarFeature(index, "nome", event.target.value)} className="h-8 w-24 rounded border border-gray-300 px-2 text-xs outline-none focus:border-[#ef4b2d]"/>
                                    <input type="text" placeholder="tipo" value={feature.tipo} onChange={(event)=> atualizarFeature(index, "tipo", event.target.value)} className="h-8 w-24 rounded border border-gray-300 px-2 text-xs outline-none focus:border-[#ef4b2d]"/>
                                    <input type="text" placeholder="descricao feature" value={feature.descricao} onChange={(event)=> atualizarFeature(index, "descricao", event.target.value)} className="h-8 w-full max-w-sm rounded border border-gray-300 px-2 text-xs outline-none focus:border-[#ef4b2d]"/>
                                    {features.length > 1 && (
                                        <button type="button" onClick={()=> removerFeature(index)} className="text-red-500 hover:text-red-700">
                                            <X size={16}/>
                                        </button>
                                    )}
                                </div>
                            ))}
                        </div>
                        <button type="button" onClick={adicionarFeature} className="mt-2 rounded border border-gray-400 px-2 py-1 text-xs text-black hover:bg-gray-100">
                            Adiconar feature +
                        </button>
                    </div>
                    <div className="mb-6">
                        <label className="mb-2 block text-xs font-bold text-black">
                            Adicionar CSV
                        </label>
                        <label onDragOver={(event)=> event.preventDefault()} onDrop={handleDrop} className="flex h-28 w-36 cursor-pointer flex-col items-center justify-center rounded border border-gray-300 bg-white text-center hover:bg-gray-50">
                            <UploadCloud size={28} className="mb-2 text-gray-400"/>
                            <span className="text-[10px] text-gray-400">
                                Clicke ou arraste aqui
                            </span>
                            <span className="my-1 text-[10px] text-gray-400">
                                ou
                            </span>
                            <span className="rounded border border-gray-300 px-3 py-1 text-[10px] text-gray-500">
                                selecione
                            </span>
                            <input type="file" accept=".csv" className="hidden" onChange={(event)=> setArquivoCsv(event.target.files[0])}/>
                        </label>
                        {arquivoCsv && (
                            <p className="mt-2 text-xs text-gray-500}">
                                Arquivo selecionado: {arquivoCsv.name}
                            </p>
                        )}
                    </div>
                </form>
                <button type="button" onClick={handleSubmit} className="rounded bg-[#ef4b2d] px-7 py-2 text-xs font-bold text-white hover:bg-[#d93f24]">
                        Pronto
                </button>
            </div>
        </div>
    );
}