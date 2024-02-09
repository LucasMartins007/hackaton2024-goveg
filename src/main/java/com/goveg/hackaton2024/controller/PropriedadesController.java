package com.goveg.hackaton2024.controller;

import com.goveg.hackaton2024.model.dto.EmpresaDTO;
import com.goveg.hackaton2024.model.dto.ProdutoPedidoDTO;
import com.goveg.hackaton2024.model.dto.UsuarioClienteDTO;
import com.goveg.hackaton2024.model.dto.UsuarioFornecedorDTO;
import com.goveg.hackaton2024.model.entity.Empresa;
import com.goveg.hackaton2024.model.entity.Produto;
import com.goveg.hackaton2024.model.entity.Propriedade;
import com.goveg.hackaton2024.model.enums.EnumStatusPedido;
import com.goveg.hackaton2024.repository.PropriedadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/propriedades")
public class PropriedadesController {

    private final PropriedadeRepository propriedadeRepository;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProdutoPedidoDTO> listarPropriedades(@RequestParam("statusPedido") EnumStatusPedido statusPedido) {
        List<Propriedade> propriedades = propriedadeRepository.findAllProdutosByStatus(statusPedido);

        List<ProdutoPedidoDTO> produtoPedidoDTOS = new ArrayList<>();
        propriedades.forEach(propriedade ->
                propriedade.getProdutos()
                        .forEach(produto -> popularListaProdutoPedidoDTO(propriedade, produto, produtoPedidoDTOS))
        );
        return produtoPedidoDTOS;
    }

    private void popularListaProdutoPedidoDTO(Propriedade propriedade, Produto produto, List<ProdutoPedidoDTO> produtoPedidoDTOS) {
        final ProdutoPedidoDTO dto = new ProdutoPedidoDTO();
        dto.setId(propriedade.getId());
        dto.setDescricao(propriedade.getNome());

        criarUsuarioClienteDTO(propriedade, dto);

        dto.setStatus(produto.getStatusPedido());
        dto.setOrganico(produto.isOrganico());
        dto.setPedidoTag(produto.getCultura());
        dto.setDescricaoProduto(produto.getDescricao());
        dto.setDataAceite(new Date());
        dto.setValor(produto.getValorUnitario());
        dto.setProdutoTipo(produto.getTipoProduto());

        produtoPedidoDTOS.add(dto);
    }

    private void criarUsuarioClienteDTO(Propriedade propriedade, ProdutoPedidoDTO dto) {
        final UsuarioFornecedorDTO usuarioFornecedorDTO = new UsuarioFornecedorDTO();
        usuarioFornecedorDTO.setId(propriedade.getEmpresa().getId());
        usuarioFornecedorDTO.setNome(propriedade.getEmpresa().getNome());
        dto.setUsuarioFornecedorDTO(usuarioFornecedorDTO);
    }
}
