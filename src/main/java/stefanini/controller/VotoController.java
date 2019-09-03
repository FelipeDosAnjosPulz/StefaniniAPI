package stefanini.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import stefanini.dto.request.RequestRegistroVotoDTO;
import stefanini.dto.response.ResponseRegistroVotoDTO;

@RestController
@RequestMapping("/api")
public class VotoController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @ResponseBody
    @PostMapping("/registrarVoto")
    public ResponseRegistroVotoDTO registrarVoto(@RequestBody RequestRegistroVotoDTO requestRegistroVotoDTO) {
        jmsTemplate.convertAndSend("registrarVoto", requestRegistroVotoDTO);
        return new ResponseRegistroVotoDTO("Voto enviado para registro.");
    }

}
