import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
// import App from './App.jsx'
import MinhaArea from './screens/MinhaArea'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <MinhaArea />
  </StrictMode>,
)
