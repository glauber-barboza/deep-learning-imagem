package glauber.deeplearning.imagens.controller;

import glauber.deeplearning.imagens.dto.Caracteristicas;
import glauber.deeplearning.imagens.service.ExtratorCaracteristicas;
import glauber.deeplearning.imagens.utils.HttpUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequestMapping("/v1/img")
public class ImagenApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImagenApi.class);
    private ExtratorCaracteristicas extratorCaracteristicas;

    public ImagenApi(ExtratorCaracteristicas extratorCaracteristicas) {
        this.extratorCaracteristicas = extratorCaracteristicas;
    }

    @PostMapping
    @ApiOperation(value = "Cria uma conta nova.")
    @ApiResponses(@ApiResponse(code = HttpUtil.OK, message = HttpUtil.CREATED_MESSAGE))
    public ResponseEntity<Caracteristicas> extrairCaracteristicas(@RequestParam MultipartFile file) throws IOException {
        LOGGER.info("Iniciado Extração de caracteristicas");
        File fileTemp = multipartToFile(file, file.getOriginalFilename());
        Caracteristicas caracteristicas = extratorCaracteristicas.extrairCaracteristicas(fileTemp,file);
        LOGGER.info("Finalizado extração de caracteristicas.");
        return ResponseEntity.ok(caracteristicas);
    }

    //CASO NÂO TENHA PASTA CRIADA NO DIRETORIO C, CRIAR
    public static File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
        File convFile = new File(  "C:\\imagens\\" + fileName);
        convFile.setExecutable(true);
        convFile.setWritable(true);
        convFile.setReadable(true);
        multipart.transferTo(convFile);
        return convFile;
    }
}
