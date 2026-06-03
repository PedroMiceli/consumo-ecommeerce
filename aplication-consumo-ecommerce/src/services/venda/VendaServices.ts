import { BaseApiServices } from "../api/BaseApiServices";
import type VendaResponse from "./dto/VendaResponse";



class VendaServices extends BaseApiServices {
    constructor() {
        super('/venda');
    }


    async obterTodasPorData(dataInicio: string, dataFim: string): Promise<VendaResponse[]> {
        const params = new URLSearchParams();

        params.append("dataInicio", dataInicio);
        params.append("dataFim", dataFim);

        return await this.get<VendaResponse[]>(`/buscar-vendas?${params.toString()}`);
    }

    // async salvar(request: NotebookRequest): Promise<NotebookResponse> {
    //         return await this.post<NotebookResponse>("", request);
    //     }

    // async excluirNotebook(id: string): Promise<void> {
    //     await this.delete('', id);
    // }

    
    
}


export const vendaServices = new VendaServices();