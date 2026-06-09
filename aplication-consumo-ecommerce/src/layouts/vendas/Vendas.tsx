import { useEffect, useState } from "react";
import { useToast } from "../../context/ToastContext";
import { DataUtils } from "../../utils/DataUtils";
import type { Nullable } from "primereact/ts-helpers";
import type VendaResponse from "../../services/venda/dto/VendaResponse";
import { vendaServices } from "../../services/venda/VendaServices";
import { Calendar } from "primereact/calendar";
import { Button } from "primereact/button";
import { Card } from "primereact/card";
import { DataTable } from "primereact/datatable";
import { Column } from "primereact/column";

export const Vendas = () => {
    const toast = useToast();
        const [loading, setLoading] = useState(true);
    
        const hoje = DataUtils.obterHoje();
        const inicioMesAtual = DataUtils.obterInicioMesAtual();
        const [periodo, setPeriodo] = useState<Date[] | null>([inicioMesAtual,hoje]);
    
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

        const formatarBoolean = (valor?: boolean) => {
            return valor ? "Sim" : "Não";
        };

        const formatarData = (valor?: number) => {
            if (!valor) {
                return "-";
            }

            return new Intl.DateTimeFormat("pt-BR", {
                day: "2-digit",
                month: "2-digit",
                year: "numeric",
                hour: "2-digit",
                minute: "2-digit",
            }).format(new Date(valor));
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
        
        <div className="card mt-4">
            <DataTable
                value={vendas}
                paginator
                rows={50}
                rowsPerPageOptions={[10, 25, 50, 100]}
                scrollable
                scrollHeight="500px"
                //stripedRows
                showGridlines
                size="small"
                rowClassName={() => 'text-sm'}
                emptyMessage="Nenhuma venda encontrada."
                className="p-datatable-sm"
            >
                <Column field="numeroVenda" header="Nº Venda" sortable frozen />
                <Column
                    field="dataVenda"
                    header="Data da venda"
                    sortable
                    body={(row: VendaResponse) => formatarData(row.dataVenda)}
                />
                <Column field="deposito" header="Depósito" sortable />
                <Column field="estado" header="Estado" sortable />
                <Column field="descricaoStatus" header="Status" />

                <Column
                    field="pacoteDiversosProdutos"
                    header="Pacote diverso"
                    body={(row: VendaResponse) => formatarBoolean(row.pacoteDiversosProdutos)}
                />

                <Column
                    field="pertenceKit"
                    header="Pertence kit"
                    body={(row: VendaResponse) => formatarBoolean(row.pertenceKit)}
                />

                <Column field="unidades" header="Unidades" sortable />

                <Column
                    field="receitaProdutos"
                    header="Receita produtos"
                    sortable
                    body={(row: VendaResponse) => formatarMoeda(row.receitaProdutos)}
                />

                <Column
                    field="receitaAcrescimoPreco"
                    header="Acréscimo preço"
                    sortable
                    body={(row: VendaResponse) => formatarMoeda(row.receitaAcrescimoPreco)}
                />

                <Column
                    field="taxaParcelamentoAcrescimo"
                    header="Taxa parcelamento"
                    sortable
                    body={(row: VendaResponse) => formatarMoeda(row.taxaParcelamentoAcrescimo)}
                />

                <Column
                    field="tarifaVendaImpostos"
                    header="Tarifa/impostos"
                    sortable
                    body={(row: VendaResponse) => formatarMoeda(row.tarifaVendaImpostos)}
                />

                <Column
                    field="receitaEnvio"
                    header="Receita envio"
                    sortable
                    body={(row: VendaResponse) => formatarMoeda(row.receitaEnvio)}
                />

                <Column
                    field="tarifasEnvio"
                    header="Tarifas envio"
                    sortable
                    body={(row: VendaResponse) => formatarMoeda(row.tarifasEnvio)}
                />

                <Column
                    field="custoEnvioMedidasPeso"
                    header="Custo medidas/peso"
                    sortable
                    body={(row: VendaResponse) => formatarMoeda(row.custoEnvioMedidasPeso)}
                />

                <Column
                    field="custoDiferencasMedidasPeso"
                    header="Diferença medidas/peso"
                    sortable
                    body={(row: VendaResponse) => formatarMoeda(row.custoDiferencasMedidasPeso)}
                />

                <Column
                    field="descontosBonus"
                    header="Descontos/bônus"
                    sortable
                    body={(row: VendaResponse) => formatarMoeda(row.descontosBonus)}
                />

                <Column
                    field="cancelamentosReembolsos"
                    header="Cancelamentos/reembolsos"
                    sortable
                    body={(row: VendaResponse) => formatarMoeda(row.cancelamentosReembolsos)}
                />

                <Column
                    field="total"
                    header="Total"
                    sortable
                    body={(row: VendaResponse) => (
                        <span className={row.total >= 0 ? "text-green-500 font-bold" : "text-red-500 font-bold"}>
                            {formatarMoeda(row.total)}
                        </span>
                    )}
                />

                <Column field="mesFaturamentoTarifas" header="Mês faturamento" sortable />
                <Column field="pedidoCompra" header="Pedido compra" />

                <Column
                    field="vendaPorPublicidade"
                    header="Publicidade"
                    body={(row: VendaResponse) => formatarBoolean(row.vendaPorPublicidade)}
                />
            </DataTable>
            <div className="flex align-items-center justify-content-between mb-3">
                <span className="text-color-secondary text-sm">
                    Total: {vendas.length}
                </span>
            </div>
        </div>
        
        </>
    );
}