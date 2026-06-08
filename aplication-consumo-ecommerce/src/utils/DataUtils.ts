export class DataUtils {
    static formatarParaLocalDateTime(data: Date, fimDoDia = false): string {
        const ano = data.getFullYear();
        const mes = String(data.getMonth() + 1).padStart(2, "0");
        const dia = String(data.getDate()).padStart(2, "0");

        const hora = fimDoDia ? "23" : "00";
        const minuto = fimDoDia ? "59" : "00";
        const segundo = fimDoDia ? "59" : "00";

        return `${ano}-${mes}-${dia}T${hora}:${minuto}:${segundo}`;
    }

    static obterInicioMesAtual(): Date {
        const hoje = new Date();
        return new Date(hoje.getFullYear(), hoje.getMonth(), 1);
    }

    static obterHoje(): Date {
        return new Date();
    }
}