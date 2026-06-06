import React from "react";
import "./style/SideBar.css";

import { CircleUser, LogOut, LayoutDashboard, Database, BookMarked, UserPen } from 'lucide-react';


export default function SideBar({ activeSection }) {

    return (
        <aside className="sidebar">

          <div className="profile">
            <CircleUser className="iconUser"/>

            {/* OBS: RECEBER DO BANCO A INFO DO USUARIO */}
            <h3>Nome Usuario</h3>
            <p>emailuser@gmail.com</p>
          </div>

         {/* OBS: IMPLEMENTAR SELECAO DE SIDEBAR e NAVEGACAO */}
          <nav className="sideBarOptions">
            <div className={`row ${activeSection === "dashboard" ? "active" : ""}`}>
              <LayoutDashboard />
              <button>DASHBOARD</button>
            </div>

            <div className={`row ${activeSection === "datasets" ? "active" : ""}`}>
              <Database />
              <button>DATASETS</button>
            </div>

            <div className={`row ${activeSection === "minha-area" ? "active" : ""}`}>
              <BookMarked />
              <button>MINHA ÁREA</button>
            </div>

          </nav>


          {/* OBS: FUNCIONALIDADE LOGOUT */}
          <button className="logout">
            <LogOut/>
          </button>

        </aside>
    )
}