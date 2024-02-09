package com.goveg.hackaton2024.controller;

import com.goveg.hackaton2024.model.dto.EmpresaDTO;
import com.goveg.hackaton2024.model.entity.Empresa;
import com.goveg.hackaton2024.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/empresa")
public class EmpresaController {

    private final EmpresaRepository empresaRepository;

    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Empresa cadastrarEmpresa(@RequestBody EmpresaDTO empresaDTO) {
        return empresaRepository.save(modelMapper.map(empresaDTO, Empresa.class));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public EmpresaDTO encontrarEmpresa() {
        return modelMapper.map(empresaRepository.findById(1).get(), EmpresaDTO.class);
    }
}
