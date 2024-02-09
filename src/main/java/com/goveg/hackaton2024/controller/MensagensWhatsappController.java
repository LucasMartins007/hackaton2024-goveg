package com.goveg.hackaton2024.controller;

import com.goveg.hackaton2024.integracao.GerenciadorWhatsapp;
import com.goveg.hackaton2024.model.entity.Mensagem;
import com.goveg.hackaton2024.model.entity.Pedido;
import com.goveg.hackaton2024.model.enums.EnumStatusPedido;
import com.goveg.hackaton2024.repository.EmpresaRepository;
import com.goveg.hackaton2024.repository.MensagemRepository;
import com.goveg.hackaton2024.repository.PedidoRepository;
import com.goveg.hackaton2024.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/whatsapp")
public class MensagensWhatsappController {

    private final GerenciadorWhatsapp gerenciadorWhatsapp;

    private final ProdutoRepository produtoRepository;

    private final PedidoRepository pedidoRepository;

    private final EmpresaRepository empresaRepository;

    private final MensagemRepository mensagemRepository;

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
            return respostaPositiva();
        }
        if (response == 2) {
            return respostaNegativa();
        }
        return gerenciadorWhatsapp.receberMensagem(body);
    }

    private String respostaNegativa() {
        final List<Pedido> pedidos = pedidoRepository.findAll();
        if (pedidos.isEmpty()) {
            return "Ocorreu um erro nos nossos servidores, por favor, aguarde um momento";
        }
        final Optional<Pedido> pedido = pedidos.stream()
                .filter(pedido1 -> pedido1.getStatusPedido().equals(EnumStatusPedido.ANDAMENTO))
                .sorted()
                .findFirst();

        if (pedido.isEmpty()) {
            return """ 
                    O prazo para resposta desse pedido encerrou, você tem até 12 horas para responder aceitar um pedido.
                    """;
        }

        pedido.get().setStatusPedido(EnumStatusPedido.CANCELADO);
        pedidoRepository.save(pedido.get());
        String mensagemEnviada = """ 
                Muito obrigado pela resposta, seu pedido foi cancelado com sucesso.
                """;

        final Mensagem mensagem = new Mensagem();
        mensagem.setMensagem(mensagemEnviada);
        mensagem.setPedido(pedido.get());
        mensagemRepository.save(mensagem);

        return mensagemEnviada;
    }

    private String respostaPositiva() {
        final List<Pedido> pedidos = pedidoRepository.findAll();
        if (pedidos.isEmpty()) {
            return "Ocorreu um erro nos nossos servidores, por favor, aguarde um momento";
        }
        final Optional<Pedido> pedido = pedidos.stream()
                .filter(pedido1 -> pedido1.getStatusPedido().equals(EnumStatusPedido.ANDAMENTO))
                .sorted()
                .findFirst();

        if (pedido.isEmpty()) {
            return """ 
                    O prazo para resposta desse pedido encerrou, você tem até 12 horas para responder aceitar um pedido.
                    """;
        }
        pedido.get().setDataAceite(new Date());
        pedidoRepository.save(pedido.get());
        String mensagemEnviada = """ 
                Muito bem! o seu pedido foi confirmado e o seu cliente já foi notificado
                para realizar o pagamento.
                Te avisaremos quando o cliente realizar o pagamento.
                """;

        final Mensagem mensagem = new Mensagem();
        mensagem.setMensagem(mensagemEnviada);
        mensagem.setPedido(pedido.get());
        mensagemRepository.save(mensagem);

        return mensagemEnviada;
    }
}
