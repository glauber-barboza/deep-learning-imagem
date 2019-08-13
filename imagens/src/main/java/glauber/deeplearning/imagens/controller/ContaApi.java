package glauber.deeplearning.imagens.controller;

import glauber.deeplearning.imagens.utils.HttpUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/conta")
public class ContaApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContaApi.class);

    @GetMapping
    @ApiOperation(value = "Cria uma conta nova.")
    @ApiResponses(@ApiResponse(code = HttpUtil.OK, message = HttpUtil.CREATED_MESSAGE))
    public ResponseEntity getTest() {
        LOGGER.info("Iniciado criação de conta.");
        LOGGER.info("Finalizado criação de conta.");
        return ResponseEntity.ok(Void.class);
    }
}
