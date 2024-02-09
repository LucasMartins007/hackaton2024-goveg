package com.goveg.hackaton2024.controller;

import com.goveg.hackaton2024.integracao.GerenciadorWhatsapp;
import com.goveg.hackaton2024.model.entity.Pedido;
import com.goveg.hackaton2024.model.enums.EnumStatusPedido;
import com.goveg.hackaton2024.repository.EmpresaRepository;
import com.goveg.hackaton2024.repository.PedidoRepository;
import com.goveg.hackaton2024.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/whatsapp")
public class MensagensWhatsappController {

    private final GerenciadorWhatsapp gerenciadorWhatsapp;

    private final ProdutoRepository produtoRepository;

    private final PedidoRepository pedidoRepository;

    private final EmpresaRepository empresaRepository;

    @PostMapping("/enviar")
    @ResponseStatus(HttpStatus.OK)
    public void enviarMensagem() {
        gerenciadorWhatsapp.enviarMensagem("mensagem enviada pelo controller");
    }

    @PostMapping("/receber")
    @ResponseStatus(HttpStatus.OK)
    public String receberMensagem(@RequestBody String body) {
        String resposta = gerenciadorWhatsapp.receberMensagem(body);

        int response;
        try {
            response = Integer.parseInt(resposta);
        } catch (NumberFormatException e) {
            return "Digite 1 para confirmar o pedido ou 2 para cancelar.";
        }
        if (response > 2) {
            return "Digite 1 para confirmar o pedido ou 2 para cancelar.";
        }
        if (response == 1) {
            final List<Pedido> pedidos = pedidoRepository.findAllByStatusPedido(EnumStatusPedido.ANDAMENTO);
            pedidos.forEach(pedido -> pedido.setDataAceite(new Date()));
            pedidoRepository.saveAll(pedidos);
            return """ 
                    Muito bem! o seu pedido foi confirmado e o seu cliente já foi notificado.
                    Faça alguma coisa depois daqui!
                    """;
        }
        return gerenciadorWhatsapp.receberMensagem(body);
    }
}
