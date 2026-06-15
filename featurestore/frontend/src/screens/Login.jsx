import { useState, useEffect } from "react";
import { login } from "../services/authService";
import { User,Lock } from "lucide-react";
import { useNavigate } from "react-router-dom";
import InputCampo from "../components/InputCampo";
import loginIlustration from "../assets/login-ilustration.png";

export default function Login(){

    const[email, setEmail]= useState("");
    const[senha, setSenha]= useState("");
    const[erro, setErro]= useState("");

    const navigate = useNavigate();

    async function handleSubmit(event) {
        event.preventDefault();
        setErro("");
        try {
            await login(email, senha);
            navigate("/minha-area");
        } catch (e) {
            setErro(e.message);
        }
    }

    return (
        <main className="min-h-screen flex items-center justify-center px-6 py-10 registro-bg">
            <section className="w-full max-w-5xl min-h-[540px] bg-[#f8f8f8] rounded-md shadow-lg flex overflow-hidden">

                <div className="w-full md:w-1/2 flex flex-col justify-center px-8 md:px-14">
                    <h1 className="text-3xl font-bold !text-black mb-6 ">
                        Login
                    </h1>
                    <form className="w-full max-w-[430px]" onSubmit={handleSubmit}>
                        <div className="space-y-5">
                            <InputCampo icon={User} type="text" placeholder="Insira o email" value={email} onChange={setEmail}/>
                            <InputCampo icon={Lock} type="password" placeholder="Insira a senha" value={senha} onChange={setSenha}/>
                        </div>
                        {erro && (
                            <p className="mt-3 text-sm text-red-500">{erro}</p>
                        )}
                        <div className="mt-12">
                            <button type="submit"className="bg-[#ff7f86] text-white text-sm px-8 py-3 rounded hover:bg-[#ff6f77] transition">
                                Login
                            </button>
                        </div>
                    </form>
                </div>
                
                <div className="hidden md:flex w-1/2 items-center justify-center px-10">
                <img
                    src={loginIlustration}
                    alt="Ilustracao de login"
                    className="w-full max-w-[360px] object-contain"
                />
                </div>
                
            </section>
        </main>
    );
}