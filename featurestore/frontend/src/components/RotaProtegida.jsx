import { Navigate } from "react-router-dom";
import { getToken } from "../services/authService";

export default function RotaProtegida({children}){
    const token = getToken();

    if(!token){
        return <Navigate to="/login"/>
    }

    return children;
}