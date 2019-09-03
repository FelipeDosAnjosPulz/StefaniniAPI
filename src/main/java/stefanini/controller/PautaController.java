package stefanini.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import stefanini.dto.request.RequestRegistroPautaDTO;
import stefanini.dto.response.ResponseBuscarPautaDTO;
import stefanini.dto.response.ResponseRegistroPautaDTO;
import stefanini.service.IPautaService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PautaController {

    @Autowired
    private IPautaService IPautaService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @ResponseBody
    @PostMapping("/registrarPauta")
    public ResponseRegistroPautaDTO registrarPauta(@RequestBody RequestRegistroPautaDTO requestRegistroPautaDTO) {
        jmsTemplate.convertAndSend("registrarPauta", requestRegistroPautaDTO);
        return new ResponseRegistroPautaDTO("Pauta enviada para registro.");
    }

    @ResponseBody
    @GetMapping("/buscarPautas")
    public List<ResponseBuscarPautaDTO> buscarPautas() {
        return this.IPautaService.buscarPautas(null);
    }

    @ResponseBody
    @GetMapping("/buscarPautaPorNome")
    public List<ResponseBuscarPautaDTO> buscarPautaPorNome(@RequestParam(value = "nomePauta") String nomePauta) {
        return this.IPautaService.buscarPautas(nomePauta);
    }

}
