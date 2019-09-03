package stefanini.service.impl;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import stefanini.Application;
import stefanini.dto.request.RequestRegistroPautaDTO;
import stefanini.dto.request.RequestRegistroVotoDTO;
import stefanini.dto.response.ResponseBuscarPautaDTO;
import stefanini.entidade.Pauta;
import stefanini.entidade.Voto;
import stefanini.service.IPautaService;
import stefanini.service.IVotoService;
import stefanini.util.DataUtil;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServicesImplTest {

    @Autowired
    private IPautaService IPautaService;

    @Autowired
    private IVotoService IVotoService;

    @Test
    public void testAregistrarPautaSucesso() {
        this.IVotoService.excluirVotos();
        this.IPautaService.excluirPautas();
        //##### CADASTRAR PAUTA ########
        RequestRegistroPautaDTO requestRegistroPautaDTO = new RequestRegistroPautaDTO();
        requestRegistroPautaDTO.setNomePauta("Pauta 1");
        requestRegistroPautaDTO.setDescricaoPauta("Descrição pauta 1");
        requestRegistroPautaDTO.setExpirarPauta(1);
        Pauta pauta = this.IPautaService.registrarPauta(requestRegistroPautaDTO);
        Assert.isTrue(pauta.getId() != 0, null);

        //##### CADASTRAR PAUTA 2 ########
        RequestRegistroPautaDTO requestRegistroPautaDTO2 = new RequestRegistroPautaDTO();
        requestRegistroPautaDTO2.setNomePauta("Pauta 2");
        requestRegistroPautaDTO2.setDescricaoPauta("Descrição pauta 2");
        requestRegistroPautaDTO2.setExpirarPauta(5);
        Pauta pauta2 = this.IPautaService.registrarPauta(requestRegistroPautaDTO2);
        Assert.isTrue(pauta2.getId() != 0, null);

    }

    @Test
    public void testBregistrarPautaErro() {

        //##### CADASTRAR COM MESMO NOME FORCAR ERRO ########
        RequestRegistroPautaDTO requestRegistroPautaDTO = new RequestRegistroPautaDTO();
        requestRegistroPautaDTO.setNomePauta("Pauta 1");
        requestRegistroPautaDTO.setDescricaoPauta("Descrição pauta 1");
        requestRegistroPautaDTO.setExpirarPauta(1);

        Pauta pauta = this.IPautaService.registrarPauta(requestRegistroPautaDTO);
        Assert.isTrue(pauta.getId() == 0, null);

        //##### CADASTRAR COM NOME null ########
        RequestRegistroPautaDTO requestRegistroPautaDTO2 = new RequestRegistroPautaDTO();
        requestRegistroPautaDTO2.setNomePauta(null);
        requestRegistroPautaDTO2.setDescricaoPauta("Descrição pauta 1");
        requestRegistroPautaDTO2.setExpirarPauta(1);

        Pauta pauta2 = this.IPautaService.registrarPauta(requestRegistroPautaDTO2);
        Assert.isTrue(pauta2.getId() == 0, null);
    }

    @Test

    public void testCbuscarTodasAsPautas() {
        //##### BUSCAR PAUTAS ########
        List<ResponseBuscarPautaDTO> list = this.IPautaService.buscarPautas(null);
        Assert.isTrue(list.size() > 0, null);
    }

    @Test
    public void testDbuscarUnicaPauta() {
        //##### BUSCAR PAUTA ########
        List<ResponseBuscarPautaDTO> list = this.IPautaService.buscarPautas("Pauta 1");
        Assert.isTrue(list.size() == 1, null);
    }

    @Test
    public void testEregistrarVotoSucesso() {
        //##### CADASTRAR VOTO ########
        RequestRegistroVotoDTO requestRegistroVotoDTO = new RequestRegistroVotoDTO();
        requestRegistroVotoDTO.setCpf("48623272226");
        requestRegistroVotoDTO.setNomePauta("Pauta 1");
        requestRegistroVotoDTO.setVoto(RequestRegistroVotoDTO.OpcoesVoto.NAO);
        Voto voto = this.IVotoService.registrarVoto(requestRegistroVotoDTO);
        Assert.isTrue(voto.getId() != 0, null);

        //##### CADASTRAR VOTO ########
        RequestRegistroVotoDTO requestRegistroVotoDTO2 = new RequestRegistroVotoDTO();
        requestRegistroVotoDTO2.setCpf("42134032626");
        requestRegistroVotoDTO2.setNomePauta("Pauta 2");
        requestRegistroVotoDTO2.setVoto(RequestRegistroVotoDTO.OpcoesVoto.SIM);
        Voto voto2 = this.IVotoService.registrarVoto(requestRegistroVotoDTO2);
        Assert.isTrue(voto2.getId() != 0, null);
    }

    @Test
    public void testFregistrarVotoErro() {
        //##### REGISTRA VOTO COM PAUTA INEXISTENTE ########
        RequestRegistroVotoDTO requestRegistroVotoDTO = new RequestRegistroVotoDTO();
        requestRegistroVotoDTO.setCpf("48623272226");
        requestRegistroVotoDTO.setNomePauta("Pauta 0");
        requestRegistroVotoDTO.setVoto(RequestRegistroVotoDTO.OpcoesVoto.NAO);
        Voto voto = this.IVotoService.registrarVoto(requestRegistroVotoDTO);
        Assert.isTrue(voto.getId() == 0, null);

        //##### REGISTRA VOTO COM CPF INVALIDO ########
        RequestRegistroVotoDTO requestRegistroVotoDTO2 = new RequestRegistroVotoDTO();
        requestRegistroVotoDTO2.setCpf("421340326260");
        requestRegistroVotoDTO2.setNomePauta("Pauta 2");
        requestRegistroVotoDTO2.setVoto(RequestRegistroVotoDTO.OpcoesVoto.SIM);
        Voto voto2 = this.IVotoService.registrarVoto(requestRegistroVotoDTO2);
        Assert.isTrue(voto2.getId() == 0, null);
    }

    @Test
    public void testGregistrarPautaComPerformace() {
        //##### CADASTRAR PAUTA ########
        System.out.println("Inicio de registros performace Pautas.");
        System.out.println(DataUtil.LocalDateTimeToString(DataUtil.dataAtual()));
        for (int i = 3; i < 2500; i++) {
            RequestRegistroPautaDTO requestRegistroPautaDTO = new RequestRegistroPautaDTO();
            requestRegistroPautaDTO.setNomePauta("Pauta " + i);
            requestRegistroPautaDTO.setDescricaoPauta("Descrição pauta " + i);
            requestRegistroPautaDTO.setExpirarPauta(i);
            this.IPautaService.registrarPauta(requestRegistroPautaDTO);
        }
        System.out.println("Fim de registros performace Pautas.");
        System.out.println(DataUtil.LocalDateTimeToString(DataUtil.dataAtual()));
        Assert.isTrue(true, null);
    }

    @Test
    public void testHregistrarVotoComPerformace() {
        //##### CADASTRAR PAUTAS ########
        System.out.println("Inicio de registros performace Votos.");
        System.out.println(DataUtil.LocalDateTimeToString(DataUtil.dataAtual()));
        List<ResponseBuscarPautaDTO> list = this.IPautaService.buscarPautas(null);
        for (ResponseBuscarPautaDTO dto : list) {
            RequestRegistroVotoDTO requestRegistroVotoDTO = new RequestRegistroVotoDTO();
            requestRegistroVotoDTO.setCpf("48623272226");
            requestRegistroVotoDTO.setNomePauta(dto.getNomePauta());
            requestRegistroVotoDTO.setVoto(RequestRegistroVotoDTO.OpcoesVoto.NAO);
            this.IVotoService.registrarVoto(requestRegistroVotoDTO);
        }
        System.out.println("Fim de registros performace Votos.");
        System.out.println(DataUtil.LocalDateTimeToString(DataUtil.dataAtual()));
        Assert.isTrue(true, null);
    }

    @Test
    public void testIlimparTest() {
        this.IVotoService.excluirVotos();
        this.IPautaService.excluirPautas();
        Assert.isTrue(true, null);
    }

}
