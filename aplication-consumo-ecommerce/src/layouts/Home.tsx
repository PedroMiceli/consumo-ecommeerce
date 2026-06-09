import { useEffect, useState } from "react";
import { vendaServices } from "../services/venda/VendaServices";
import { useToast } from "../context/ToastContext";
import { DataUtils } from "../utils/DataUtils";
import type { Nullable } from "primereact/ts-helpers";
import { Calendar } from "primereact/calendar";
import { Button } from "primereact/button";
import type ResumoVendasResponse from "../services/venda/dto/ResumoVendasResponse";
import { Card } from "primereact/card";

export const Home = () => {
    const toast = useToast();
    const [loading, setLoading] = useState(true);

    const hoje = DataUtils.obterHoje();
    const inicioMesAtual = DataUtils.obterInicioMesAtual();
    const [periodo, setPeriodo] = useState<Date[] | null>([inicioMesAtual,hoje]);

    const[vendas, setVendas] = useState<ResumoVendasResponse | null>(null);

    const obterVendas = (inicio: Nullable<Date>, fim: Nullable<Date>) => {
        if (!inicio || !fim) {
            toast.error({ detail: "Informe a data de início e a data final." });
            return;
        }

        setLoading(true);

        const dataInicioFormatada = DataUtils.formatarParaLocalDateTime(inicio);
        const dataFimFormatada = DataUtils.formatarParaLocalDateTime(fim, true);

        vendaServices.obterResumoVendasPorData(dataInicioFormatada, dataFimFormatada)
            .then((response) => {
                setVendas(response);
            })
            .catch(e => toast.error({ detail: e.message }))
            .finally(() => setLoading(false));
    };

    useEffect(() => {obterVendas(inicioMesAtual, hoje);}, []);

    const formatarMoeda = (valor?: number) => {
        return new Intl.NumberFormat("pt-BR", {
            style: "currency",
            currency: "BRL",
        }).format(valor ?? 0);
    };

    const formatarPorcentagem = (valor?: number) => {
        if (valor === undefined || valor === null || Number.isNaN(valor)) {
            return "0,0";
        }

        return valor.toLocaleString("pt-BR", {
            minimumFractionDigits: 1,
            maximumFractionDigits: 1,
        }
    );
};


    return (
        <>
        <Card className="mb-4">
            <div className="grid justify-content-center align-items-end">
                <div className="col-12 md:col-4">
                    <Calendar
                        value={periodo}
                        onChange={(e) => setPeriodo(e.value as Date[])}
                        selectionMode="range"
                        readOnlyInput
                        hideOnRangeSelection
                        dateFormat="dd/mm/yy"
                        showIcon
                        className="w-full"
                        placeholder="Selecione a data início e fim"
                    />
                </div>

                <div className="col-12 md:col-2">
                    <Button
                        label="Buscar"
                        icon="pi pi-search"
                        onClick={() => {
                            const dataInicio = periodo?.[0];
                            const dataFim = periodo?.[1];

                            obterVendas(dataInicio, dataFim);
                        }}
                        loading={loading}
                        className="w-full"
                        disabled={!periodo?.[0] || !periodo?.[1]}
                    />
                </div>
            </div>
        </Card>

        <Card className="mb-4">
            <div className="grid">
                <div className="col-12 md:col-6 lg:col-2">
                    <div className="surface-card border-1 surface-border border-round p-3 h-full">
                        <span className="text-color-secondary text-sm">
                            Vendas encontradas
                        </span>

                        <h3 className="m-0 mt-1 text-green-500">
                            {vendas?.totalDeVendasRealizadas ?? 0}
                        </h3>
                    </div>
                </div>
                <div className="col-12 md:col-6 lg:col-2">
                    <div className="surface-card border-1 surface-border border-round p-3 h-full">
                        <span className="text-color-secondary text-sm">
                            Vendas bem-sucedidas
                        </span>

                        <h3 className="m-0 mt-2 text-green-500">
                            {vendas?.totalDeVendasBemSucedidas ?? 0}
                        </h3>
                    </div>
                </div>
                <div className="col-12 md:col-6 lg:col-2">
                    <div className="surface-card border-1 surface-border border-round p-3 h-full">
                        <span className="text-700 text-sm">
                            Vendas declaradas
                        </span>

                        <h3 className="m-0 mt-2 text-blue-500">
                            {formatarMoeda(vendas?.valorDeclarado)}
                        </h3>
                    </div>
                </div>
                <div className="col-12 md:col-6 lg:col-2">
                    <div className="surface-card border-1 surface-border border-round p-3 h-full">
                        <span className="text-700 text-sm">
                            Recebido em conta
                        </span>

                        <h3 className="m-0 mt-2 text-green-500">
                            {formatarMoeda(vendas?.valorLucrado)}
                        </h3>
                    </div>
                </div>
                <div className="col-12 md:col-6 lg:col-2">
                    <div className="surface-card border-1 surface-border border-round p-3 h-full">
                        <span className="text-700 text-sm">
                            Total faturado
                        </span>

                        <h3 className="m-0 mt-2 text-orange-500">
                            {formatarMoeda(vendas?.valorFaturado)}
                        </h3>
                    </div>
                </div>
                <div className="col-12 md:col-6 lg:col-2">
                    <div className="surface-card border-1 surface-border border-round p-3 h-full">
                        <span className="text-700 text-sm">
                            Reservado
                        </span>

                        <h3 className="m-0 mt-2 text-orange-500">
                            Reservado
                        </h3>
                    </div>
                </div>
            </div>
            <div className="grid">
                <div className="col-12 md:col-6 lg:col-2">
                    <div className="surface-card  border-1 surface-border border-round p-3 h-full">
                        <span className="text-700 text-sm">
                            Valor reembolsado
                        </span>

                        <h3 className="m-0 mt-2 text-red-500">
                            {formatarMoeda(vendas?.valorDevolvidoComReembolsoAoComprador)}
                        </h3>
                    </div>
                </div>

                <div className="col-12 md:col-6 lg:col-2">
                    <div className="surface-card border-1 surface-border border-round p-3 h-full">
                        <span className="text-700 text-sm">
                            Vendas com reembolso
                        </span>

                        <h3 className="m-0 mt-2 text-red-500">
                            {formatarPorcentagem(vendas?.porcentagemDeVendasComReembolso)}%
                        </h3>
                    </div>
                </div>
            </div>
        </Card>
        </>
    );
};