import React from "react";
import "./SideBar.css";

import { CircleUser, LogOut, LayoutDashboard, Database, BookMarked, UserPen } from 'lucide-react';


export default function SideBar() {

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
            <div className="row">
                <LayoutDashboard/>
                <button>DASHBOARD</button>
            </div>

            <div className="row">
                <Database/>
                <button>DATASETS</button>
            </div>

            <div className="active">
                <BookMarked/>
                <button >MINHA ÁREA</button>
            </div>

            <div className="row">
                <UserPen/>
                <button>DATASETS</button>
            </div>
          </nav>


          {/* OBS: FUNCIONALIDADE LOGOUT */}
          <button className="logout">
            <LogOut/>
          </button>

        </aside>
    )
}