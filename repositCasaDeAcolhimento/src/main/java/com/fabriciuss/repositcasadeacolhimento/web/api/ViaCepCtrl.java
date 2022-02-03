package com.fabriciuss.repositcasadeacolhimento.web.api;

import com.fabriciuss.repositcasadeacolhimento.domain.Funcionario;
import com.fabriciuss.repositcasadeacolhimento.service.DTO.EnderecoTO;
import com.fabriciuss.repositcasadeacolhimento.service.ViaCepCtrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @RestControler faz a combinação de @Controller e @ResponseBody, simplificando a criação de serviços web RESTful.
 */
@RestController
public class ViaCepCtrl implements Serializable {
    private final Logger log = LoggerFactory.getLogger(ViaCepCtrl.class);

    private static final long serialVersionUID = 6046704732666502085L;

    private final ViaCepCtrlService viaCepCtrlService;

    public ViaCepCtrl(ViaCepCtrlService viaCepCtrlService) {
        this.viaCepCtrlService = viaCepCtrlService;
    }

    /**
     * @PathVariable é utilizado quando o valor da variável é passada diretamente na URL, sem a utilização de interrogação (?).
     * O valor poderá ser acessado atraves do parametro cep que é do tipo String.
     *
     * @GetMapping permite mapear solicitações HTTP GET, toda vez que uma solicitação GET para o endereço /getCep/{cep} foi disparada o método será executado.
     *
     * @param cep
     * @return ResponseEntity<EnderecoTO>
     */
    @GetMapping(value="/getCep/{cep}")
    public ResponseEntity<EnderecoTO> doObterCep(@PathVariable(name = "cep") String cep) {
        log.debug("REST request to get EnderecoTO : {}", cep);
        EnderecoTO enderecoTO = viaCepCtrlService.findOne(cep);
        /**
         * ResponseEntity permite retornar para tela os dados encontratos, o primeiro parametro recebe os dados, o segundo o status do response.
         */
        return new ResponseEntity<EnderecoTO>(enderecoTO, HttpStatus.OK);
    }

}
