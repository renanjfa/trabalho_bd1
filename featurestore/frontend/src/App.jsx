import { Routes, Route } from "react-router-dom";
import Registro from "./screens/Registro";
import Login from "./screens/Login";
import MinhaArea from "./screens/MinhaArea";
import Datasets from "./screens/Datasets";
import DatasetPage from "./screens/DatasetPage";
import Dashboard from "./screens/Dashboard";
import RotaProtegida from "./components/RotaProtegida";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="/registro" element={<Registro />} />
      <Route path="/login" element={<Login />} />
      <Route path="/minha-area" element={<RotaProtegida><MinhaArea /></RotaProtegida>} />
      <Route path="/datasets" element={<RotaProtegida><Datasets /></RotaProtegida>} />
      <Route path="/dataset-page/:id" element={<RotaProtegida><DatasetPage /></RotaProtegida>} />
      <Route path="/dashboard" element={<RotaProtegida><Dashboard /></RotaProtegida>} />
    </Routes>
  );
}

export default App;