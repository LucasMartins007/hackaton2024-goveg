package com.goveg.hackaton2024.integracao;

import com.goveg.hackaton2024.config.RestTemplateBean;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GerenciadorWhatsapp {

    public static final String ACCOUNT_SID = "AC185e9cdea484cf43b46c47b74e53c9c4";
    public static final String AUTH_TOKEN = "3bf28eaefa2dbfe862027a60122b2695";

    public static final String URL = "https://  -mastiff-9776.twil.io/demo-reply";


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

    public String receberMensagem() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        final ResponseEntity<String> result = restClient.restTemplate()
                .getForEntity(URL, String.class);

        return result.getBody();
    }
}