import { BrowserRouter, createBrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import App from "../App";
import { Home } from "../layouts/Home";


export const Router = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home />} />
                {/* <Route path="/vendas" element={<Vendas />} />
                <Route path="/produtos" element={<Produtos />} /> */}
            </Routes>
        </BrowserRouter>
    );
}