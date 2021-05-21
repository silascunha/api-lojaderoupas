package grupodogrupo.lojaderoupa.utils;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;
import grupodogrupo.lojaderoupa.model.Email;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    @Value("${mailapi.apikey}")
    private String MJ_APIKEY_PUBLIC;

    @Value("${mailapi.secretkey}")
    private String MJ_API_PRIVATE;

    @Value("${mailapi.template}")
    private Integer MJ_TEMPLATE;

    public boolean sendEmail(Email emailInfo, String codigo)  {
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;
        client = new MailjetClient(MJ_APIKEY_PUBLIC, MJ_API_PRIVATE, new ClientOptions("v3.1"));
        request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "grupodogrupo.sistema@outlook.com")
                                        .put("Name", "E-commerce"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", emailInfo.getEmail())
                                                .put("Name", emailInfo.getNome())))
                                .put(Emailv31.Message.TEMPLATEID, MJ_TEMPLATE)
                                .put(Emailv31.Message.TEMPLATELANGUAGE, true)
                                .put(Emailv31.Message.SUBJECT, "Confirme seu E-mail - Loja de roupa sem nome")
                                .put(Emailv31.Message.VARIABLES, new JSONObject()
                                        .put("nome", emailInfo.getNome())
                                        .put("codigo", codigo))));
        try {
            response = client.post(request);

            System.out.println(response.getStatus());
            System.out.println(response.getData());

            if (response.getStatus() == 200) {
                return true;
            }
            return false;

        } catch (MailjetException | MailjetSocketTimeoutException e) {
            e.printStackTrace();
            return false;
        }

    }
}
