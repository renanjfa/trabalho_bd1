import { useState } from "react";
import { ArrowLeft, X } from "lucide-react";
import { criarDatasets } from "../services/datasetservice";

export default function AdicionarDataset({aberto, onClose, onDatasetCriado}){
    const [titulo, setTitulo]= useState("");
    const [fontes, setFontes]= useState([""]);
    const [descricao, setDescricao]= useState("");
    const [erro, setErro]= useState("");

    if(!aberto){
        return null;
    }
    function adicionarFonte(){
        setFontes([...fontes, ""]);
    }
    function atualizarFontes(index, valor){
        const novasFontes=[...fontes];
        novasFontes[index]= valor;
        setFontes(novasFontes);
    }
    function removerFonte(index){
        const novasFontes= fontes.filter((_,i)=>i!==index);
        setFontes(novasFontes);
    }
    async function handleSubmit(event){
        event.preventDefault();

        try{
            const resultado = await criarDatasets(titulo, descricao, fontes);
            setTitulo("");
            setFontes([""]);
            setDescricao("");
            onDatasetCriado(resultado);
        }catch (e){
            setErro(e.message);
        }
    }

    return(
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/60 px-4">
            <div className="w-full max-w-3xl rounded bg-white px-12 py-8 shadow-lg">
                <div className="mb-5 flex items-center justify-between">
                    <div>
                        <h2 className="text-sm font bold text-black">
                            Adicione um Novo Dataset
                        </h2>
                        <div className="mt-1 h-[2px] w-20 bg-[#ff5a4f]"/>
                    </div>
                    <button type="button" onClick={onClose} className="text-xs font-bold text-black underline hover:text-[#ff5a4f]">
                        <ArrowLeft/>
                    </button>
                </div>
                <form onSubmit={handleSubmit} className="border border-gray-300 px-4 py-4">
                    <div className="mb-4">
                        <label className="mb-2 block text-xs font-bold text-black">
                            Título
                        </label>
                        <input type="text" value={titulo} onChange={(event)=> setTitulo(event.target.value)}
                            className="h-8 w-full max-w-md rounded border border-gray-300 px-2 text-sm outline-none focus:border-[#ff5a4f]"/>
                    </div>
                    <div className="mb-4">
                        <label className="mb-2 block text-xs font-bold text-black">
                            Fontes
                        </label>
                        <div className="space-y-2">
                            {fontes.map((fonte, index)=>(
                                <div key={index} className="flex items-center gap-2">
                                    <input type="text" placeholder="Nome da fonte" value={fonte} onChange={(event)=> atualizarFontes(index, event.target.value)}
                                        className="h-8 w-full max-w-md rounded border border-gray-300 px-2 text-sm outline-none focus:border-[#ff5a4f]"/>
                                    {fontes.length > 1 && (
                                        <button type="button" onClick={()=> removerFonte(index)} className="text-red-500 hover:text-red-700">
                                            <X size={16}/>
                                        </button>
                                    )}
                                </div>
                            ))}
                        </div>
                        <button type="button" onClick={adicionarFonte} className="mt-2 rounded border-gray-400 px-2 py-1 text-xs text-black hover:bg-gray-100">
                            Adicionar Fonte +
                        </button>
                    </div>
                    <div className="mb-4">
                        <label className="mb-2 block text-xs font-bold text-black">
                            Descrição
                        </label>
                        <textarea value={descricao} onChange={(event)=> setDescricao(event.target.value)}
                            className="h-32 w-full max-w-md resize-none rounded border border-gray-300 px-2 py-2 text-sm outline-none focus:border-[#ff5a4f]"/>
                    </div>

                    {erro && <p className="mt-2 text-sm text-red-500">{erro}</p>}
                    <button type="submit"  className="rounded bg-[#ef4b2d] px-7 py-2 text-xs font-bold text-white hover:bg-[#d93f24]">
                        Criar Versão Original
                    </button>
                </form>
            </div>
        </div>
    );
}