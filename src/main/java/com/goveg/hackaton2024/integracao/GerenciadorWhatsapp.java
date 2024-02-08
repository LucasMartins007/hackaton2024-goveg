package com.goveg.hackaton2024.integracao;

import com.goveg.hackaton2024.config.RestTemplateBean;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GerenciadorWhatsapp {

    public String ACCOUNT_SID = System.getenv("ACCOUNT_SID");
    public String AUTH_TOKEN = System.getenv("AUTH_TOKEN");

    public static final String URL = "https://hackaton-c2ddd13cc9f5.herokuapp.com/hackaton/v1/whatsapp/receber";

    @Autowired
    private RestTemplateBean restClient;


    public void enviarMensagem(String mensagem) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:+554598281323"),
                new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                mensagem).create();

        System.out.println(message.getSid());
    }

    public String receberMensagem(String mensagem) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        return mensagem;
    }
}