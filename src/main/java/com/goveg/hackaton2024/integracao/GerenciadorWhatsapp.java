package com.goveg.hackaton2024.integracao;

import com.goveg.hackaton2024.model.entity.Empresa;
import com.goveg.hackaton2024.model.entity.Pedido;
import com.goveg.hackaton2024.model.entity.Produto;
import com.goveg.hackaton2024.model.enums.EnumStatusPedido;
import com.goveg.hackaton2024.repository.EmpresaRepository;
import com.goveg.hackaton2024.repository.ProdutoRepository;
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
        if (ACCOUNT_SID == null || AUTH_TOKEN == null) {
            ACCOUNT_SID = "AC185e9cdea484cf43b46c47b74e53c9c4";
            AUTH_TOKEN = "47d01b7ccd953917636381fbc5b1e20e";
        }
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("whatsapp:+554598281323"),
                        new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                        mensagem)
                .create();

        System.out.println(message.getSid());
    }

    public String receberMensagem(String mensagem) {
        if (ACCOUNT_SID == null || AUTH_TOKEN == null) {
            ACCOUNT_SID = "AC185e9cdea484cf43b46c47b74e53c9c4";
            AUTH_TOKEN = "47d01b7ccd953917636381fbc5b1e20e";
        }
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        final Map<String, String> params = extractParams(mensagem);

        return params.get("Body");
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