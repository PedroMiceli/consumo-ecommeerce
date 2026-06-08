import { Route, Routes } from "react-router-dom";
import { Home } from "../layouts/Home";
import { Vendas } from "../layouts/vendas/Vendas";


export const Router = () => {
    return (
        <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/vendas" element={<Vendas />} />
            {/* <Route path="/anuncios" element={<Anuncios />} /> */}
        </Routes>
    );
}