package glauber.deeplearning.imagens.controller;

import glauber.deeplearning.imagens.service.BaseWeka;
import glauber.deeplearning.imagens.utils.HttpUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/v1/img")
public class ImagenApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImagenApi.class);
    private BaseWeka baseWeka;

    public ImagenApi(BaseWeka baseWeka) {
        this.baseWeka = baseWeka;
    }

    @PostMapping("/naveBayes")
    @ApiOperation(value = "Cria uma conta nova.")
    @ApiResponses(@ApiResponse(code = HttpUtil.OK, message = HttpUtil.CREATED_MESSAGE))
    public ResponseEntity<String> classificaNaveBayes(@RequestParam MultipartFile file) throws IOException {
        LOGGER.info("Iniciado Extração de caracteristicas");
        File fileTemp = multipartToFile(file, file.getOriginalFilename());
        String caracteristicas = baseWeka.classificaNaveBayes(fileTemp);
        LOGGER.info("Finalizado extração de caracteristicas.");
        return ResponseEntity.ok(caracteristicas);
    }

    @PostMapping("/j48")
    @ApiOperation(value = "Classifica por arvore J48.")
    @ApiResponses(@ApiResponse(code = HttpUtil.OK, message = HttpUtil.CREATED_MESSAGE))
    public ResponseEntity<String> classificaArvoreJ48(@RequestParam MultipartFile file) throws IOException {
        LOGGER.info("Iniciado Extração de caracteristicas");
        File fileTemp = multipartToFile(file, file.getOriginalFilename());
        String caracteristicas = baseWeka.classificaJ48(fileTemp);
        LOGGER.info("Finalizado extração de caracteristicas.");
        return ResponseEntity.ok(caracteristicas);
    }

    //CASO NÂO TENHA PASTA CRIADA NO DIRETORIO C, CRIAR
    public static File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
        File convFile = new File("C:\\imagens\\" + fileName);
        convFile.setExecutable(true);
        convFile.setWritable(true);
        convFile.setReadable(true);
        multipart.transferTo(convFile);
        return convFile;
    }
}
