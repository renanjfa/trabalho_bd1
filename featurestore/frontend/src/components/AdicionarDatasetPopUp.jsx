import { useState } from "react";
import { ArrowLeft } from "lucide-react";

export default function AdicionarDataset({aberto, onClose, onAbrirVersao}){
    const [titulo, setTitulo]= useState("");
    const [fontes, setFontes]= useState("");
    const [descricao, setDescricao]= useState("");

    if(!aberto){
        return null;
    }
    function handleSubmit(event){
        event.preventDefault();

        const novoDataset = {
            titulo,
            fontes,
            descricao,
        };

        setTitulo("");
        setFontes("");
        setDescricao("");

        onAbrirVersao();
    }

    return(
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/60 px-4">
            <div className="w-full max-w-3xl rounded bg-white px-12 py-8 shadow-lg">
                <div className="mb-5 flex items-center justify-between">
                    <div>
                        <h2 className="text-sm font bold text-black">
                            Adicione um novo dataset
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
                            Titulo
                        </label>
                        <input type="text" value={titulo} onChange={(event)=> setTitulo(event.target.value)}
                            className="h-8 w-full max-w-md rounded border border-gray-300 px-2 text-sm outline-none focus:border-[#ff5a4f]"/>
                    </div>
                    <div className="mb-4">
                        <label className="mb-2 block text-xs font-bold text-black">
                            Fontes
                        </label>
                        <input type="text" value={fontes} onChange={(event)=> setFontes(event.target.value)}
                            className="h-8 w-20 rounded border border-gray-300 px-2 text-sm outline-none focus:border-[#ff5a4f]"/>
                    </div>
                    <div className="mb-4">
                        <label className="mb-2 block text-xs font-bold text-black">
                            Descricao
                        </label>
                        <textarea value={descricao} onChange={(event)=> setDescricao(event.target.value)}
                            className="h-32 w-full max-w-md resize-none rounded border border-gray-300 px-2 py-2 text-sm outline-none focus:border-[#ff5a4f]"/>
                    </div>

                    <button type="submit" className="rounded bg-[#ef4b2d] px-7 py-2 text-xs font-bold text-white hover:bg-[#d93f24]">
                        Criar versao original
                    </button>
                </form>
            </div>
        </div>
    );
}