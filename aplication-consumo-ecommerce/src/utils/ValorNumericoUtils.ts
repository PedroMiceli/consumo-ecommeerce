export default class ValorNumericoUtils {

    static formatarMoedaBRL(valor: number | null): string {
        if (!valor) return 'R$ 0,00';

        return new Intl.NumberFormat('pt-BR', {
            style: 'currency',
            currency: 'BRL',
        }).format(valor)
    }

    static formatarPorcentagemBRL(valor: number, casasDecimais?: number) {
        if (!valor) return "0%";

        return valor.toFixed(casasDecimais ?? 0).toLocaleString().replace('.', ',') + '%';
    }

}