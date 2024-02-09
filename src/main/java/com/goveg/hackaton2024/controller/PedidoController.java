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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    @PostMapping("/cancelar")
    @ResponseStatus(HttpStatus.OK)
    public void cancelarEntrega(@RequestParam("produtoId") Integer produtoId) {
        final Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        final Pedido pedido = produto.getPedido();

        pedido.setStatusPedido(EnumStatusPedido.CANCELADO);
        pedidoRepository.save(pedido);

        produto.setStatusPedido(EnumStatusPedido.CANCELADO);
        produtoRepository.save(produto);
    }

    @PostMapping("/entregar")
    @ResponseStatus(HttpStatus.OK)
    public void confirmarEntrega(@RequestParam("produtoId") Integer produtoId) {
        final Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        final Pedido pedido = produto.getPedido();

        pedido.setStatusPedido(EnumStatusPedido.CONCLUIDO);
        pedidoRepository.save(pedido);

        produto.setStatusPedido(EnumStatusPedido.CONCLUIDO);
        produto.setDataConclusao(new Date());
        produtoRepository.save(produto);

        String mensagem = "O produto foi entregue com sucesso.";
        gerenciadorWhatsapp.enviarMensagem(mensagem);

        final Mensagem msg = new Mensagem();
        msg.setMensagem(mensagem);
        mensagemRepository.save(msg);
    }

    @PostMapping("/confirmar")
    @ResponseStatus(HttpStatus.OK)
    public void confirmarPedido(@RequestBody ConfirmacaoPedidoDTO confirmacaoPedidoDTO, @RequestParam("produtoId") Integer produtoId) {
        final Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        final Empresa empresa = empresaRepository.findById(1).get();

        Pedido pedido = new Pedido();
        pedido.setStatusPedido(EnumStatusPedido.ANDAMENTO);
        pedido.setProduto(List.of(produto));
        pedido.setPrecototal(confirmacaoPedidoDTO.getQuantidade().multiply(produto.getValorUnitario()));
        pedido.setQuantidadeTotal(confirmacaoPedidoDTO.getQuantidade());
        pedido.setDataRecebimento(confirmacaoPedidoDTO.getDataRecebimento());
        pedido.setEmpresa(empresa);
        pedido = pedidoRepository.save(pedido);

        produto.setDataInclusao(new Date());
        produto.setPedido(pedido);
        produto.setStatusPedido(EnumStatusPedido.ANDAMENTO);
        produto.setDataEntrega(confirmacaoPedidoDTO.getDataRecebimento());
        produtoRepository.save(produto);

        String mensagemEnviada = formatarMensagem(empresa, pedido, confirmacaoPedidoDTO, produto);
        gerenciadorWhatsapp.enviarMensagem(mensagemEnviada);

        final Mensagem mensagem = new Mensagem();
        mensagem.setMensagem(mensagemEnviada);
        mensagem.setPedido(pedido);
        mensagemRepository.save(mensagem);
    }

    @PostMapping("/pagar")
    @ResponseStatus(HttpStatus.OK)
    public void pagarPedido(@RequestParam("produtoId") Integer produtoId) {
        final Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produto.setStatusPedido(EnumStatusPedido.ENTREGUE);

        produtoRepository.save(produto);

        String mensagem =
                "Gostaríamos de informar que recebemos a confirmação de pagamento referente ao seu pedido. Com isso pode iniciar o processo de entrega.";
        gerenciadorWhatsapp.enviarMensagem(mensagem);

        final Mensagem msg = new Mensagem();
        msg.setMensagem(mensagem);
        mensagemRepository.save(msg);
    }

    private String formatarMensagem(Empresa empresa, Pedido pedido, ConfirmacaoPedidoDTO confirmacaoPedidoDTO, Produto produto) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = formato.format(confirmacaoPedidoDTO.getDataRecebimento());

        if(pedido.getProduto().isEmpty()) {

        }

        return """
                   Olá, espero que esteja bem!
                   A Ibis Cascavel solicitou %s Kg de %s, somando um  total de %s, para ser entregue no dia %s, deseja aceitar?
                   
                   Digite:
                   1 - Confirmar
                   2 - Cancelar
                """.formatted(
                pedido.getQuantidadeTotal(),
                produto.getTipoProduto().getDescricao(),
                pedido.getPrecototal(),
                dataFormatada
        );
    }
}
