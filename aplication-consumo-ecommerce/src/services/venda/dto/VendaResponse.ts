export default interface VendaResponse {
    id: string;
    numeroVenda: string;
    dataVenda: number;
    deposito: string;
    estado: string;
    descricaoStatus: string;
    pacoteDiversosProdutos: boolean;
    pertenceKit: boolean;
    unidades: number;
    receitaProdutos: number;
    receitaAcrescimoPreco: number;
    taxaParcelamentoAcrescimo: number;
    tarifaVendaImpostos: number;
    receitaEnvio: number;
    tarifasEnvio: number;
    custoEnvioMedidasPeso: number;
    custoDiferencasMedidasPeso: number;
    descontosBonus: number;
    cancelamentosReembolsos: number;
    total: number;
    mesFaturamentoTarifas: string;
    pedidoCompra: string;
    vendaPorPublicidade: boolean;
}