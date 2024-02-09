package com.goveg.hackaton2024.controller;

import com.goveg.hackaton2024.model.dto.EmpresaDTO;
import com.goveg.hackaton2024.model.dto.ProdutoPedidoDTO;
import com.goveg.hackaton2024.model.dto.UsuarioClienteDTO;
import com.goveg.hackaton2024.model.dto.UsuarioFornecedorDTO;
import com.goveg.hackaton2024.model.entity.Empresa;
import com.goveg.hackaton2024.model.entity.Produto;
import com.goveg.hackaton2024.model.entity.Propriedade;
import com.goveg.hackaton2024.model.enums.EnumCultura;
import com.goveg.hackaton2024.model.enums.EnumStatusPedido;
import com.goveg.hackaton2024.model.enums.EnumTipoProduto;
import com.goveg.hackaton2024.repository.EmpresaRepository;
import com.goveg.hackaton2024.repository.ProdutoRepository;
import com.goveg.hackaton2024.repository.PropriedadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/propriedades")
public class PropriedadesController {

    private final PropriedadeRepository propriedadeRepository;

    private final ProdutoRepository produtoRepository;

    private final EmpresaRepository empresaRepository;

    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarPropriedade() {
        Propriedade propriedade = new Propriedade();
        propriedade.setNome("Fazenda seu João");

        List<Empresa> empresa = empresaRepository.findAll();
        propriedade.setEmpresa(empresa.get(0));

        propriedadeRepository.save(propriedade);
    }

    @PostMapping("/cadastrar/produto")
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarProdutos(@RequestParam("propriedadeId") Integer propriedadeId) {
        final Produto produtoMorango = new Produto();

        final Propriedade propriedade = propriedadeRepository.findById(propriedadeId).get();
        produtoMorango.setPropriedade(propriedade);

        produtoMorango.setTipoProduto(EnumTipoProduto.MORANGO);
        produtoMorango.setOrganico(true);
        produtoMorango.setCultura(EnumCultura.FRUTA);
        produtoMorango.setStatusPedido(EnumStatusPedido.ABERTO);
        produtoMorango.setValorUnitario(BigDecimal.valueOf(10.60));
        produtoMorango.setDescricao("Desfrute da doçura natural das nossos morangos cultivadas organicamente. Colhidas no auge da maturidade, esses morangos premium são uma explosão de sabor e frescor");
        produtoRepository.save(produtoMorango);


        final Produto produtoUva = new Produto();
        final Propriedade propriedadeUva = propriedadeRepository.findById(propriedadeId).get();
        produtoUva.setPropriedade(propriedadeUva);
        produtoUva.setTipoProduto(EnumTipoProduto.UVA);
        produtoUva.setOrganico(true);
        produtoUva.setCultura(EnumCultura.FRUTA);
        produtoUva.setStatusPedido(EnumStatusPedido.ABERTO);
        produtoUva.setValorUnitario(BigDecimal.valueOf(8.60));
        produtoUva.setDescricao("Deleite-se com a experiência única das nossas uvas viníferas cultivadas em um terroir exclusivo. Cada cacho é uma obra-prima da natureza, produzido com dedicação e expertise.");
        produtoRepository.save(produtoUva);


        final Produto produtoBatata = new Produto();
        final Propriedade propriedadeBatata = propriedadeRepository.findById(propriedadeId).get();
        produtoBatata.setPropriedade(propriedadeBatata);

        produtoBatata.setTipoProduto(EnumTipoProduto.BATATA);
        produtoBatata.setOrganico(true);
        produtoBatata.setCultura(EnumCultura.LEGUME);
        produtoBatata.setStatusPedido(EnumStatusPedido.ABERTO);
        produtoBatata.setValorUnitario(BigDecimal.valueOf(15.90));
        produtoBatata.setDescricao("Nossas batatas são cultivadas de forma sustentável, respeitando o solo e o meio ambiente. Com uma pele fina e um interior macio, essas batatas orgânicas são ideais para uma variedade de pratos saudáveis e deliciosos.");
        produtoRepository.save(produtoBatata);

        final Produto produtoTomate = new Produto();
        final Propriedade propriedadeTomate = propriedadeRepository.findById(propriedadeId).get();
        produtoTomate.setPropriedade(propriedadeTomate);

        produtoTomate.setTipoProduto(EnumTipoProduto.ALHO);
        produtoTomate.setOrganico(true);
        produtoTomate.setCultura(EnumCultura.LEGUME);
        produtoTomate.setStatusPedido(EnumStatusPedido.ABERTO);
        produtoTomate.setValorUnitario(BigDecimal.valueOf(19.70));
        produtoTomate.setDescricao("Nossos tomates heirloom são um deleite para os conhecedores. Com uma variedade de formas e cores vibrantes, esses tomates são cultivados com paixão e cuidado. Sinta o sabor autêntico e a textura suculenta que só os tomates sazonais podem oferecer");
        produtoRepository.save(produtoTomate);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProdutoPedidoDTO> listarPropriedades(@RequestParam("statusPedido") EnumStatusPedido statusPedido) {
        List<Propriedade> propriedades = propriedadeRepository.findAll();

        List<ProdutoPedidoDTO> produtoPedidoDTOS = new ArrayList<>();
        propriedades.forEach(propriedade ->
                propriedade.getProdutos()
                        .stream()
                        .filter(produto -> {
                            if (statusPedido.equals(EnumStatusPedido.CONCLUIDO) || statusPedido.equals(EnumStatusPedido.ENTREGUE)) {
                                System.out.println(produto.getStatusPedido().equals(EnumStatusPedido.CONCLUIDO)
                                        || produto.getStatusPedido().equals(EnumStatusPedido.ENTREGUE));
                                return produto.getStatusPedido().equals(EnumStatusPedido.CONCLUIDO)
                                        || produto.getStatusPedido().equals(EnumStatusPedido.ENTREGUE);
                            }
                            return produto.getStatusPedido().equals(statusPedido);
                        })
                        .sorted(Comparator.comparing(Produto::getId))
                        .forEach(produto -> popularListaProdutoPedidoDTO(propriedade, produto, produtoPedidoDTOS))
        );
        return produtoPedidoDTOS;
    }

    private void popularListaProdutoPedidoDTO(Propriedade propriedade, Produto produto, List<ProdutoPedidoDTO> produtoPedidoDTOS) {
        final ProdutoPedidoDTO dto = new ProdutoPedidoDTO();
        dto.setId(produto.getId());
        dto.setDescricao(propriedade.getNome());

        criarUsuarioClienteDTO(propriedade, dto);

        dto.setStatus(produto.getStatusPedido());
        dto.setOrganico(produto.isOrganico());
        dto.setPedidoTag(produto.getCultura());
        dto.setDataAceite(produto.getPedido() == null ? produto.getDataAceite() : produto.getPedido().getDataAceite());
        dto.setQuantidade(produto.getPedido() == null ? null : produto.getPedido().getQuantidadeTotal());
        dto.setDataEntrega(produto.getDataEntrega());
        dto.setDataConclusao(produto.getDataConclusao());
        dto.setDataInclusao(produto.getDataInclusao());
        dto.setDescricaoProduto(produto.getDescricao());
        dto.setValor(produto.getValorUnitario());
        dto.setProdutoTipo(produto.getTipoProduto());

        produtoPedidoDTOS.add(dto);
    }

    private void criarUsuarioClienteDTO(Propriedade propriedade, ProdutoPedidoDTO dto) {
        final UsuarioFornecedorDTO usuarioFornecedorDTO = new UsuarioFornecedorDTO();
        usuarioFornecedorDTO.setId(propriedade.getId());
        usuarioFornecedorDTO.setNome(propriedade.getNome());
        dto.setUsuarioFornecedorDTO(usuarioFornecedorDTO);
    }
}
