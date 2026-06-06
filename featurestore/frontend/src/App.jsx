import { Routes, Route } from "react-router-dom";
import Registro from "./screens/Registro";
import Login from "./screens/Login";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Registro />} />
      <Route path="/registro" element={<Registro />} />
      <Route path="/login" element={<Login />} />
    </Routes>
  );
}

export default App;