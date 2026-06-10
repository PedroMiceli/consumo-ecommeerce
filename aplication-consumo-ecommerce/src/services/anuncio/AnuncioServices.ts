import { BaseApiServices } from "../api/BaseApiServices";
import type AnuncioResponse from "./dto/AnuncioResponse";

class AnuncioServices extends BaseApiServices {
    constructor() {
        super('/anuncio');
    }


    async obterTodos(): Promise<AnuncioResponse[]> {
        return await this.get<AnuncioResponse[]>(`/buscar-anuncios`);
    }
    
    // async salvar(request: NotebookRequest): Promise<NotebookResponse> {
    //         return await this.post<NotebookResponse>("", request);
    //     }

    // async excluirNotebook(id: string): Promise<void> {
    //     await this.delete('', id);
    // }

}


export const anuncioServices = new AnuncioServices();