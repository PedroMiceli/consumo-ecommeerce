import { useEffect, useState } from "react";
import { useToast } from "../../context/ToastContext";
import { DataUtils } from "../../utils/DataUtils";
import type AnuncioResponse from "../../services/anuncio/dto/AnuncioResponse";
import { anuncioServices } from "../../services/anuncio/AnuncioServices";
import { DataTable } from "primereact/datatable";
import { Column } from "primereact/column";

export const Anuncios = () => {
    const toast = useToast();
        const [loading, setLoading] = useState(true);
    
    
        const[anuncios, setAnuncios] = useState<AnuncioResponse[]>([]);
    
        const obterAnuncios = () => {
        
            setLoading(true);

            anuncioServices.obterTodos()
                .then((response) => {
                    setAnuncios(response);
                })
                .catch(e => toast.error({ detail: e.message }))
                .finally(() => setLoading(false));
        };
    
        useEffect(() => {obterAnuncios();}, []);


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
        <div className="card mt-4">
            <DataTable
                value={anuncios}
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

                <Column field="numeroAnuncio" header="Numero do anuncio" sortable frozen />
                <Column field="sku" header="SKU do Produto" sortable />
                <Column field="canalVenda" header="Canal de Venda" sortable />
                <Column field="tituloAnuncio" header="Titulo do Anuncio" />
                <Column field="variacao" header="Variação" sortable />
                <Column field="precoUnitarioVenda" header="Valor do Anuncio" body={(row: AnuncioResponse) => formatarMoeda(row.precoUnitarioVenda)} sortable />
                <Column field="tipoAnuncio" header="Tipo do Anuncio" sortable />
            </DataTable>
            <div className="flex align-items-center justify-content-between mb-3">
                <span className="text-color-secondary text-sm">Total: {anuncios.length}</span>
            </div>
        </div>
        
        </>
    );
}