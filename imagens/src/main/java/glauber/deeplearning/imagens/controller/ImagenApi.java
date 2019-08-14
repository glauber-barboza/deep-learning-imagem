package glauber.deeplearning.imagens.controller;

import glauber.deeplearning.imagens.utils.HttpUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/v1/conta")
public class ImagenApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImagenApi.class);

    @PostMapping
    @ApiOperation(value = "Cria uma conta nova.")
    @ApiResponses(@ApiResponse(code = HttpUtil.OK, message = HttpUtil.CREATED_MESSAGE))
    public ResponseEntity getTest(@RequestBody File file) {
        LOGGER.info("Iniciado criação de conta.");
        File fi= file;
        LOGGER.info("Finalizado criação de conta.");
        return ResponseEntity.ok(Void.class);
    }
}
