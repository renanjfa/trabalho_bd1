import { useState } from "react";
import { User, Mail,Lock } from "lucide-react";
import { useNavigate } from "react-router-dom";
import InputCampo from "../components/InputCampo";
import registerIllustration from "../assets/register-illustration.png";
import { registrar } from "../services/authService";

export default function Registro(){

    const [nome, setNome] = useState("");
    const [email, setEmail] = useState("");
    const [senha, setSenha] = useState("");
    const [erro, setErro] = useState("");

    const navigate = useNavigate();

    async function handleSubmit(event) {
        event.preventDefault();
        setErro("");
        try {
            await registrar(email, nome, senha);
            navigate("/login");
        } catch (e) {
            setErro(e.message);
        }
    }

    return (
        <main className="min-h-screen flex items-center justify-center px-6 py-10 registro-bg">
            <section className="w-full max-w-5xl min-h-[540px] bg-[#f8f8f8] rounded-md shadow-lg flex overflow-hidden">
                <div className="hidden md:flex w-1/2 items-center justify-center px-10">
                <img
                    src={registerIllustration}
                    alt="Ilustracao de registro"
                    className="w-full max-w-[360px] object-contain"
                />
                </div>
                <div className="w-full md:w-1/2 flex flex-col items-center px-8 md:px-14 py-9">
                    <h1 className="text-3xl font-bold !text-black mb-7 ">
                        Registro
                    </h1>
                    <form className="w-full max-w-[430px]" onSubmit={handleSubmit}>
                        <div className="space-y-5">
                            <InputCampo icon={User} type="text" placeholder="Insira o nome de usuario" value={nome} onChange={setNome}/>
                            <InputCampo icon={Mail} type="email" placeholder="Insira o email" value={email} onChange={setEmail}/>
                            <InputCampo icon={Lock} type="password" placeholder="Insira a senha" value={senha} onChange={setSenha}/>
                        </div>
                        {erro && (
                            <p className="mt-3 text-sm text-red-500">{erro}</p>
                        )}
                        <div className="flex flex-col items-center gap-3 mt-36">
                            <button
                                type="submit"
                                className="w-64 bg-[#ff7f86] text-white text-sm py-3 rounded hover:bg-[#ff6f77] transition"
                            >
                                Registrar
                            </button>

                            <button
                                type="button"
                                onClick={() => navigate("/login")}
                                className="w-64 text-sm py-3 rounded border border-[#ff7f86] text-[#ff7f86] hover:bg-[#fff0f1] transition"
                            >
                                Já possui conta? Faça login
                            </button>
                        </div>
                    </form>
                </div>
            </section>
        </main>
    );
}