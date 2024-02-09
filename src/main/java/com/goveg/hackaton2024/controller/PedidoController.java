package com.goveg.hackaton2024.controller;

import com.goveg.hackaton2024.integracao.GerenciadorWhatsapp;
import com.goveg.hackaton2024.model.dto.ConfirmacaoPedidoDTO;
import com.goveg.hackaton2024.model.entity.Empresa;
import com.goveg.hackaton2024.model.entity.Mensagem;
import com.goveg.hackaton2024.model.entity.Pedido;
import com.goveg.hackaton2024.model.entity.Produto;
import com.goveg.hackaton2024.model.enums.EnumStatusPedido;
import com.goveg.hackaton2024.repository.*;
import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.IMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pedido")
public class PedidoController {

    private final PropriedadeRepository propriedadeRepository;

    private final ProdutoRepository produtoRepository;

    private final EmpresaRepository empresaRepository;

    private final PedidoRepository pedidoRepository;

    private final GerenciadorWhatsapp gerenciadorWhatsapp;

    private final MensagemRepository mensagemRepository;

    @PostMapping("/confirmar")
    @ResponseStatus(HttpStatus.OK)
    public void confirmarPedido(@RequestBody ConfirmacaoPedidoDTO confirmacaoPedidoDTO, @RequestParam("produtoId") Integer produtoId) {
        final Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        final Empresa empresa = empresaRepository.findById(1).get();

        Pedido pedido = new Pedido();
        pedido.setStatusPedido(EnumStatusPedido.ANDAMENTO);
        pedido.setProduto(List.of(produto));
        pedido.setPrecototal(confirmacaoPedidoDTO.getValorTotal());
        pedido.setQuantidadeTotal(confirmacaoPedidoDTO.getQuantidade());
        pedido.setDataRecebimento(confirmacaoPedidoDTO.getDataRecebimento());
        pedido.setEmpresa(empresa);
        pedidoRepository.save(pedido);

        produto.setPedido(pedido);
        produto.setStatusPedido(EnumStatusPedido.ANDAMENTO);
        produtoRepository.save(produto);

        String mensagemEnviada = formatarMensagem(empresa, pedido);
        gerenciadorWhatsapp.enviarMensagem(mensagemEnviada);

        final Mensagem mensagem = new Mensagem();
        mensagem.setMensagem(mensagemEnviada);
        mensagem.setPedido(pedido);
        mensagemRepository.save(mensagem);
    }

    private String formatarMensagem(Empresa empresa, Pedido pedido) {
        return """
                   A %s deseja fazer um pedido de %s Kg de %s no valor de R$ %s, deseja aceitar?
                    \n
                    1 - Sim
                    2 - Não
                """.formatted(
                empresa.getNome(),
                pedido.getQuantidadeTotal(),
                pedido.getProduto().get(0).getDescricao(),
                pedido.getPrecototal()
        );
    }
}
