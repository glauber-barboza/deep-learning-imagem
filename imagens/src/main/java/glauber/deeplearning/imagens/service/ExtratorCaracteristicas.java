package glauber.deeplearning.imagens.service;

import glauber.deeplearning.imagens.dto.Caracteristicas;
import org.bytedeco.opencv.opencv_core.CvScalar;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import static org.bytedeco.opencv.global.opencv_core.cvGet2D;
import static org.bytedeco.opencv.helper.opencv_imgcodecs.cvLoadImage;

@Service
public class ExtratorCaracteristicas {

    public Caracteristicas extrairCaracteristicas(File file, MultipartFile multipartFile) {
        float laranjaCamisaBart = 0.0F;
        float azulCalcaoBart = 0.0F;
        float azulSapatoBart = 0.0F;
        float azulCalcaHomer = 0.0F;
        float marromBocaHomer = 0.0F;
        float cinzaSapatoHomer = 0.0F;
        double red, green, blue;
        float[] caracteristicas = new float[6];

        //OBS, não funciona em modo debbug
        IplImage iplImage = cvLoadImage(file.getAbsolutePath());

        for (int altura = 0; altura < iplImage.height(); ++altura) {
            for (int largura = 0; largura < iplImage.width(); ++largura) {
                CvScalar scalarExtraiRgb = cvGet2D(iplImage, altura, largura);
                blue = scalarExtraiRgb.val(0);
                green = scalarExtraiRgb.val(1);
                red = scalarExtraiRgb.val(2);

                if (blue >= 11.0D && blue <= 22.0D && green >= 85.0D && green <= 105.0D && red >= 240.0D && red <= 255.0D) {
                    ++laranjaCamisaBart;
                }

                if (altura > iplImage.height() / 2 && blue >= 125.0D && blue <= 170.0D && green >= 0.0D && green <= 12.0D && red >= 0.0D && red <= 20.0D) {
                    ++azulCalcaoBart;
                }

                if (altura > iplImage.height() / 2 + iplImage.height() / 3 && blue >= 125.0D && blue <= 140.0D && green >= 3.0D && green <= 12.0D && red >= 0.0D && red <= 20.0D) {
                    ++azulSapatoBart;
                }

                if (blue >= 150.0D && blue <= 180.0D && green >= 98.0D && green <= 120.0D && red >= 0.0D && red <= 90.0D) {
                    ++azulCalcaHomer;
                }

                if (altura < iplImage.height() / 2 + iplImage.height() / 3 && blue >= 95.0D && blue <= 140.0D && green >= 160.0D && green <= 185.0D && red >= 175.0D && red <= 200.0D) {
                    ++marromBocaHomer;
                }

                if (altura > iplImage.height() / 2 + iplImage.height() / 3 && blue >= 25.0D && blue <= 45.0D && green >= 25.0D && green <= 45.0D && red >= 25.0D && red <= 45.0D) {
                    ++cinzaSapatoHomer;
                }
            }
        }

        laranjaCamisaBart = laranjaCamisaBart / (float) (iplImage.height() * iplImage.width()) * 100.0F;
        azulCalcaoBart = azulCalcaoBart / (float) (iplImage.height() * iplImage.width()) * 100.0F;
        azulSapatoBart = azulSapatoBart / (float) (iplImage.height() * iplImage.width()) * 100.0F;
        azulCalcaHomer = azulCalcaHomer / (float) (iplImage.height() * iplImage.width()) * 100.0F;
        marromBocaHomer = marromBocaHomer / (float) (iplImage.height() * iplImage.width()) * 100.0F;
        cinzaSapatoHomer = cinzaSapatoHomer / (float) (iplImage.height() * iplImage.width()) * 100.0F;

        caracteristicas[0] = laranjaCamisaBart;
        caracteristicas[1] = azulCalcaoBart;
        caracteristicas[2] = azulSapatoBart;
        caracteristicas[3] = azulCalcaHomer;
        caracteristicas[4] = marromBocaHomer;
        caracteristicas[5] = cinzaSapatoHomer;

        Caracteristicas carac = new Caracteristicas();
        carac.setLaranjaCamisaBart(laranjaCamisaBart);
        carac.setAzulCalcaoBart(azulCalcaoBart);
        carac.setAzulSapatoBart(azulSapatoBart);
        carac.setAzulCalcaHomer(azulCalcaHomer);
        carac.setMarromBocaHomer(marromBocaHomer);
        carac.setCinzaSapatoHomer(cinzaSapatoHomer);
        return carac;
    }

}
