import { User, Mail,Lock } from "lucide-react";
import InputCampo from "../components/InputCampo";
import registerIllustration from "../assets/register-illustration.png";

export default function Registro(){
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
                    <form className="w-full max-w-[430px]">
                        <div className="space-y-5">
                            <InputCampo icon={User}type="text"placeholder="Insira o nome de usuario"/>
                            <InputCampo icon={Mail}type="email"placeholder="Insira o email"/>
                            <InputCampo icon={Lock}type="password"placeholder="Insira a senha"/>
                        </div>
                        <div className="flex justify-center mt-36">
                            <button type="submit"className="bg-[#ff7f86] text-white text-sm px-8 py-3 rounded hover:bg-[#ff6f77] transition">
                                Registro
                            </button>
                        </div>
                    </form>
                </div>
            </section>
        </main>
    );
}