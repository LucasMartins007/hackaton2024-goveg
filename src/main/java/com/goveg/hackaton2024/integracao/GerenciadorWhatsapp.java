package com.goveg.hackaton2024.integracao;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GerenciadorWhatsapp {

    public String ACCOUNT_SID = System.getenv("ACCOUNT_SID");
    public String AUTH_TOKEN = System.getenv("AUTH_TOKEN");


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
        System.out.println("-----------------------------------------" + mensagem + "------------------------------------");
        final Map<String, String> params = extractParams(mensagem);

        final String bodyValue = params.get("Body");

        System.out.println("------------------------------------ Valor do Body: " + bodyValue + "------------------------------------");
        return bodyValue;
    }

    private static Map<String, String> extractParams(String url) {
        Map<String, String> params = new HashMap<>();
        String[] urlParts = url.split("&");
        for (String part : urlParts) {
            String[] keyValue = part.split("=");
            if (keyValue.length == 2) {
                String decodedValue = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                params.put(keyValue[0], decodedValue);
            }
        }
        return params;
    }

}