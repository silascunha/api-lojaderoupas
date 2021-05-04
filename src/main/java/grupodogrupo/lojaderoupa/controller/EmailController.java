package grupodogrupo.lojaderoupa.controller;

import grupodogrupo.lojaderoupa.model.Email;
import grupodogrupo.lojaderoupa.utils.EmailSender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/email")
public class EmailController {

    @PostMapping
    public ResponseEntity<String> enviarEmail(@RequestBody Email email) {
        String mensagem = "Olá " + email.getNome() + "," +
                "\n\nO código de verificação do seu email é: Mentira eu não gerei código nenhum";
        try {
            EmailSender.sendEmail(email.getEmail(), "Confirme sua conta", mensagem);
            return ResponseEntity.ok().body("O email foi enviado");
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.accepted().body("O email não foi enviado");
        }

    }
}
