package com.mecaniq.app.utils

import com.mecaniq.app.data.models.OrdemServicoDTO

object WhatsAppUtil {

    fun gerarMensagemOS(os: OrdemServicoDTO, modeloVeiculo: String, placa: String): String {
        val itensFormatados = os.itens.joinToString("\n") { item ->
            "• ${item.descricao} (${item.quantidade}x R$ ${item.precoUnitario})"
        }

        return """
            🚗 *MecaniQ - Ordem de Serviço Emitida*
            
            Olá! Segue o resumo do atendimento para o veículo *$modeloVeiculo* (Placa: *$placa*):
            
            *KM de Entrada:* ${os.kmEntrada} km
            
            *Serviços / Peças:*
            $itensFormatados
            
            *Valor Total:* R$ ${os.valorTotal}
            
            Obrigado pela preferência! Caso tenha dúvidas, responda a esta mensagem.
        """.trimIndent()
    }

    /**
     * Codifica o texto para o formato de URL do WhatsApp de forma compatível
     */
    fun encodeParam(texto: String): String {
        return texto.replace(" ", "%20")
            .replace("\n", "%0A")
            .replace("*", "%2A")
    }
}