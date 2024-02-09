package com.goveg.hackaton2024.controller;

import com.goveg.hackaton2024.model.enums.EnumStatusPedido;
import com.goveg.hackaton2024.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/total")
public class TotalizadorController {

    private final ProdutoRepository produtoRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public long cadastrarPropriedade(@RequestParam("emAndamento") Boolean emAndamento) {
        if (emAndamento) {
            return produtoRepository.findAll()
                    .stream()
                    .filter(produto -> produto.getStatusPedido().equals(EnumStatusPedido.ANDAMENTO))
                    .count();
        }
        return produtoRepository.count();
    }
}
