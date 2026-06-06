import React from "react";
import "./style/Header.css";

export default function Header() {

    const today = new Date();
    const weekDay = today.toLocaleDateString('default', { weekday: 'long' });
    const currentDate = today.toLocaleDateString();

    return(

        <header className="header" >
            <h1>
                <span className="feature"> Feature</span> <span className="store">Store</span>
            </h1>

            <div className="dateBlock">
                <strong className="weekDay"> {weekDay} </strong>
                <span className="todayDate"> {currentDate} </span>
            </div>
        </header>
    )

}