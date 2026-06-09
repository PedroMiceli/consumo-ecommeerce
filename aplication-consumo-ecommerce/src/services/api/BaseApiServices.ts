import {ApiError} from "./models/ApiError.ts";

export const API_URL = import.meta.env.VITE_API_URL;

type RequestOptions = {
    autenticado?: boolean;
    headers?: HeadersInit;
};

export abstract class BaseApiServices {
    private readonly urlApi: string = API_URL;
    private readonly controller: string;

    constructor(controller: string) {
        this.controller = controller;
    }

    private obterUrl(endpoint: string): string {
        return `${this.urlApi}${this.controller}${endpoint}`;
    }

    private montarQueryParams(params?: Record<string, any>): string {
        if (!params) return "";

        const queryParams = new URLSearchParams();

        Object.entries(params).forEach(([key, value]) => {
            if (Array.isArray(value)) {
                value.forEach(item => queryParams.append(key, String(item)));
            } else if (value !== undefined && value !== null) {
                queryParams.append(key, String(value));
            }
        });

        const queryString = queryParams.toString();

        return queryString ? `?${queryString}` : "";
    }

    private async request<T>(endpoint: string,init: RequestInit,options: RequestOptions = { autenticado: false }
    ): Promise<T> {
        const response = await fetch(this.obterUrl(endpoint), {
            ...init,
            credentials: options.autenticado ? "include" : "omit",
            headers: {
                ...options.headers,
                ...init.headers
            }
        });

        return await this.handleResponse<T>(response);
    }

    protected async get<T>(endpoint: string, params?: Record<string, any>, options?: RequestOptions): Promise<T> {
        const queryString = this.montarQueryParams(params);

        return await this.request<T>(
            `${endpoint}${queryString}`,
            {
                method: "GET",
                // headers: {"Content-Type": "application/json"}
            },
            options
        );
    }

    protected async post<T>(endpoint: string, body: unknown, options?: RequestOptions): Promise<T> {
        return await this.request<T>(
            endpoint,
            {
                method: "POST",
                // headers: {"Content-Type": "application/json"},
                body: JSON.stringify(body)
            },
            options
        );
    }

    protected async postFormData<T>(endpoint: string, formData: FormData, options?: RequestOptions): Promise<T> {
        return await this.request<T>(
            endpoint,
            {
                method: "POST",
                body: formData
            },
            options
        );
    }

    protected async put<T>(endpoint: string, body: unknown, options?: RequestOptions): Promise<T> {
        return await this.request<T>(
            endpoint,
            {
                method: "PUT",
                // headers: {"Content-Type": "application/json"},
                body: JSON.stringify(body)
            },
            options
        );
    }


    protected async putFormData<T>(endpoint: string, formData: FormData, options?: RequestOptions): Promise<T> {
        return await this.request<T>(
            endpoint,
            {
                method: "PUT",
                body: formData
            },
            options
        );
    }

    protected async delete(endpoint: string, id: string, options?: RequestOptions): Promise<void> {
        await this.request<void>(
            `${endpoint}/${id}`,
            {
                method: "DELETE",
                // headers: {"Content-Type": "application/json"}
            },
            options
        );
    }

    // Lê a resposta: se ok retorna (json/text/void), se houver erro lança ApiError
    private async handleResponse<T>(response: Response): Promise<T> {
        const contentType = response.headers.get('content-type') ?? '';
        const hasBody = response.headers.get('content-length') !== '0' && response.status !== 204;

        // ✅ SUCESSO
        if (response.ok) {
            if (hasBody && contentType.includes('application/json')) {
                // MUDANÇA AQUI:
                const text = await response.text(); // Lê como texto

                // Analisa o JSON, aplicando a conversão de datas automaticamente
                try {
                    return JSON.parse(text, dateReviver) as T;
                    // eslint-disable-next-line @typescript-eslint/no-unused-vars
                } catch (e) {
                    // Retorna o erro se o JSON for inválido (mesmo com 2xx)
                    throw new ApiError('Erro', response.status, text);
                }
            }
            if (hasBody) {
                // corpo não-JSON (raro)
                return (await response.text()) as unknown as T;
            }
            // sem corpo (201/204)
            return undefined as unknown as T;
        }

        // ❌ ERRO → tenta extrair sua estrutura { erro, detalhe, status }
        let message = response.statusText || 'Erro na requisição';
        let details: unknown;

        try {
            if (hasBody && contentType.includes('application/json')) {
                const data = await response.json();
                details = data;
                message =
                    data?.detalhe ||
                    data?.erro ||
                    data?.message ||
                    data?.error ||
                    JSON.stringify(data);
            } else if (hasBody) {
                const text = await response.text();
                details = text;
                if (text) message = text;
            }
        } catch {
            // se parsing falhar, mantém statusText
        }

        throw new ApiError(message, response.status, details);
    }
}


function dateReviver(_: unknown, value: unknown): unknown {
    // Regex simples para detectar strings no formato YYYY-MM-DD (sem tempo)
    const shortDateRegex = /^(\d{4})-(\d{2})-(\d{2})$/;
    // Regex para strings ISO 8601 completas (com tempo e Z)
    const isoDateRegex = /(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2}).(\d{3})Z/;

    if (typeof value === 'string') {

        // **CASO 1: Data Simples (YYYY-MM-DD) SEM tempo (Seu caso)**
        if (shortDateRegex.test(value)) {
            // Adiciona o tempo T00:00:00, mas SEM o 'Z' (UTC).
            // Isso força o JavaScript a interpretar a data no fuso horário local (GMT-0300).
            const date = new Date(`${value}T00:00:00`);

            if (!isNaN(date.getTime())) {
                return date;
            }
        }

        // **CASO 2: Datas ISO 8601 completas (com tempo e Z)**
        // Mantemos a conversão padrão para datas que vêm corretamente no formato completo do back-end,
        // pois estas normalmente representam um momento específico no tempo.
        if (isoDateRegex.test(value)) {
            const date = new Date(value);
            if (!isNaN(date.getTime())) {
                return date;
            }
        }
    }
    return value;
}