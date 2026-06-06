import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
// import App from './App.jsx'
import MinhaArea from './screens/MinhaArea'
import Datasets from './screens/Datasets'
import DatasetPage from './screens/DatasetPage'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <DatasetPage />
  </StrictMode>,
)
