import React, {useState, useEffect} from "react";
import { useNavigate } from "react-router-dom";
import "./style/SideBar.css";
import { logout, buscarPerfil } from "../services/authService";

import { CircleUser, LogOut, LayoutDashboard, Database, BookMarked, UserPen } from 'lucide-react';


export default function SideBar({ activeSection }) {

    const navigate = useNavigate();

    const [usuario, setUsuario]= useState(null);

    useEffect(()=>{
      async function carregar(){
        try{
          const data = await buscarPerfil();
          setUsuario(data);
        }catch (e){
          console.error(e.message);
        }
      }
      carregar();
    },[])

    return (
        <aside className="sidebar">

          <div className="profile">
            <CircleUser className="iconUser"/>

            <h3>{usuario ? usuario.nome_usuario : "..."}</h3>
            <p>{usuario ? usuario.email : "..."}</p>
          </div>

         {/* OBS: IMPLEMENTAR SELECAO DE SIDEBAR e NAVEGACAO */}
          <nav className="sideBarOptions">
            <div className={`row ${activeSection === "dashboard" ? "active" : ""}`}>
              <LayoutDashboard />
              <button>DASHBOARD</button>
            </div>

            <div className={`row ${activeSection === "datasets" ? "active" : ""}`}>
              <Database />
              <button onClick={() => navigate("/datasets")}>DATASETS</button>
            </div>

            <div className={`row ${activeSection === "minha-area" ? "active" : ""}`}>
              <BookMarked />
              <button onClick={() => navigate("/minha-area")}>MINHA ÁREA</button>
            </div>

          </nav>


          <button className="logout" onClick={()=>{logout(); navigate("/login");}}>
              <LogOut/>
          </button>
          

        </aside>
    )
}