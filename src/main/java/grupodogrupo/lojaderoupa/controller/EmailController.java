package grupodogrupo.lojaderoupa.controller;

import grupodogrupo.lojaderoupa.model.Email;
import grupodogrupo.lojaderoupa.utils.CodeGenerator;
import grupodogrupo.lojaderoupa.utils.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/email")
public class EmailController {

    @Autowired
    private EmailSender emailSender;

    @PostMapping
    public ResponseEntity<String> enviarEmail(@RequestBody Email email) {
        boolean foiEnviado = emailSender.sendEmail(email, CodeGenerator.gerarCodigo());

        if (!foiEnviado) {
            return ResponseEntity.badRequest().body("Email inválido");
        }
        return ResponseEntity.ok().body("Email enviado");
    }
}
