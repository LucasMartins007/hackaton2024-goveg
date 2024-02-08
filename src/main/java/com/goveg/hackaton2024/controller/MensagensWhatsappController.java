package com.goveg.hackaton2024.controller;

import com.goveg.hackaton2024.integracao.GerenciadorWhatsapp;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/whatsapp")
public class MensagensWhatsappController {

    private final GerenciadorWhatsapp gerenciadorWhatsapp;

    @PostMapping("/enviar")
    @ResponseStatus(HttpStatus.OK)
    public void enviarMensagem() {
        gerenciadorWhatsapp.enviarMensagem("mensagem enviada pelo controller");
    }

    @PostMapping("/receber")
    @ResponseStatus(HttpStatus.OK)
    public String receberMensagem(@RequestBody String body) {
        return gerenciadorWhatsapp.receberMensagem(body);
    }
}
