import { BrowserRouter } from 'react-router-dom'
import './App.css'
import { Router } from './router/Router'
import { Navbar } from './layouts/shared/navbar/Navbar'

function App() {
  

  return(
    <BrowserRouter>
            <div className="app">
                <Navbar />
                <main className="app-content">
                    <Router />
                </main>
            </div>
        </BrowserRouter>
  ) 
}


export default App
