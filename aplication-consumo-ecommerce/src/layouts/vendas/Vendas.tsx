import { useEffect, useState } from "react";
import { useToast } from "../../context/ToastContext";
import { DataUtils } from "../../utils/DataUtils";
import type { Nullable } from "primereact/ts-helpers";
import type VendaResponse from "../../services/venda/dto/VendaResponse";
import { vendaServices } from "../../services/venda/VendaServices";
import { Calendar } from "primereact/calendar";
import { Button } from "primereact/button";

export const Vendas = () => {
    const toast = useToast();
    const [loading, setLoading] = useState(true);

    const hoje = DataUtils.obterHoje();
    const inicioMesAtual = DataUtils.obterInicioMesAtual();

    const [dataInicio, setDataInicio] = useState<Nullable<Date>>(inicioMesAtual);
    const [dataFim, setDataFim] = useState<Nullable<Date>>(hoje);

    const[vendas, setVendas] = useState<VendaResponse[]>([]);

    const obterVendas = (inicio: Nullable<Date>, fim: Nullable<Date>) => {
        if (!inicio || !fim) {
            toast.error({ detail: "Informe a data de início e a data final." });
            return;
        }

        setLoading(true);

        const dataInicioFormatada = DataUtils.formatarParaLocalDateTime(inicio);
        const dataFimFormatada = DataUtils.formatarParaLocalDateTime(fim, true);

        vendaServices.obterTodasPorData(dataInicioFormatada, dataFimFormatada)
            .then((response) => {
                console.log(response);
                setVendas(response);
            })
            .catch(e => toast.error({ detail: e.message }))
            .finally(() => setLoading(false));
    };

    useEffect(() => {
        console.log(vendas);
    }, [vendas]);

    useEffect(() => {obterVendas(inicioMesAtual, hoje);}, []);


    return (
        <div className="card">
           

            <div className="grid align-items-end">
                <div className="col-12 md:col-4">
                    <label className="block mb-2">Data início</label>
                    <Calendar
                        value={dataInicio}
                        onChange={(e) => setDataInicio(e.value)}
                        dateFormat="dd/mm/yy"
                        showIcon
                        className="w-full"
                    />
                </div>

                <div className="col-12 md:col-4">
                    <label className="block mb-2">Data fim</label>
                    <Calendar
                        value={dataFim}
                        onChange={(e) => setDataFim(e.value)}
                        dateFormat="dd/mm/yy"
                        showIcon
                        className="w-full"
                    />
                </div>

                <div className="col-12 md:col-4">
                    <Button
                        label="Buscar"
                        icon="pi pi-search"
                        onClick={() => obterVendas(dataInicio, dataFim)}
                        loading={loading}
                    />
                </div>
            </div>

            <div className="mt-4">
                <p>Total de vendas encontradas: {vendas.length}</p>
            </div>
        </div>

    );
}