import { Routes, Route } from "react-router-dom";
import Registro from "./screens/Registro";
import Login from "./screens/Login";
import MinhaArea from "./screens/MinhaArea";
import Datasets from "./screens/Datasets";
import DatasetPage from "./screens/DatasetPage";
import Dashboard from "./screens/Dashboard";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="/registro" element={<Registro />} />
      <Route path="/login" element={<Login />} />
      <Route path="/minha-area" element={<MinhaArea />} />
      <Route path="/datasets" element={<Datasets />} />
      <Route path="/dataset-page/:id" element={<DatasetPage />} />
      <Route path="/dashboard" element={<Dashboard />} />
    </Routes>
  );
}

export default App;