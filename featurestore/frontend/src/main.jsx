import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
// import App from './App.jsx'
import MinhaArea from './screens/MinhaArea'
import Datasets from './screens/Datasets'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <MinhaArea />
  </StrictMode>,
)
