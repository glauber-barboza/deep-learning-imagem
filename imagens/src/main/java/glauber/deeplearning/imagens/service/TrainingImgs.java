package glauber.deeplearning.imagens.service;

import org.bytedeco.opencv.opencv_core.CvMat;
import org.bytedeco.opencv.opencv_core.CvScalar;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.bytedeco.opencv.global.opencv_core.*;
import static org.bytedeco.opencv.global.opencv_core.cvSet2D;
import static org.bytedeco.opencv.helper.opencv_imgcodecs.cvLoadImage;

@Service
public class TrainingImgs {
    public void execute(){
        String exportacao = "@relation caracteristicas\n\n";
        exportacao = exportacao + "@attribute laranja_camisa_bart real\n";
        exportacao = exportacao + "@attribute azul_calcao_bart real\n";
        exportacao = exportacao + "@attribute azul_sapato_bart real\n";
        exportacao = exportacao + "@attribute marrom_boca_homer real\n";
        exportacao = exportacao + "@attribute azul_calca_homer real\n";
        exportacao = exportacao + "@attribute cinza_sapato_homer real\n";
        exportacao = exportacao + "@attribute classe {Bart, Homer}\n\n";
        exportacao = exportacao + "@data\n";
        File diretorio = new File("..\\imagens\\src\\main\\java\\glauber\\deeplearning\\imagens\\imagens");
        File[] arquivos = diretorio.listFiles();
        float[][] caracteristicas = new float[400][7];

        for (int i = 0; i < arquivos.length; ++i) {
            float laranjaCamisaBart = 0.0F;
            float azulCalcaoBart = 0.0F;
            float azulSapatoBart = 0.0F;
            float azulCalcaHomer = 0.0F;
            float marromBocaHomer = 0.0F;
            float cinzaSapatoHomer = 0.0F;
            IplImage imagemOriginal = cvLoadImage(diretorio.getAbsolutePath() + "\\" + arquivos[i].getName());
            // CvSize tamanhoImagemOriginal = cvGetSize(imagemOriginal);
            //IplImage imagemProcessada = cvCreateImage(tamanhoImagemOriginal, 8, 3);

            IplImage imagemProcessada = cvCloneImage(imagemOriginal);
            byte classePersonagem;
            String classePersonagemString;
            if (arquivos[i].getName().charAt(0) == 'b') {
                classePersonagem = 0;
                classePersonagemString = "Bart";
            } else {
                classePersonagem = 1;
                classePersonagemString = "Homer";
            }

            CvMat mtx = CvMat.createHeader(imagemProcessada.height(), imagemProcessada.width());
            CvScalar scalarImagemProcessada = new CvScalar();
            cvGetMat(imagemProcessada, mtx, (int[]) null, 0);

            for (int altura = 0; altura < imagemProcessada.height(); ++altura) {
                for (int largura = 0; largura < imagemProcessada.width(); ++largura) {
                    CvScalar scalarExtraiRgb = cvGet2D(imagemProcessada, altura, largura);
                    double blue = scalarExtraiRgb.val(0);
                    double green = scalarExtraiRgb.val(1);
                    double red = scalarExtraiRgb.val(2);
                    if (blue >= 11.0D && blue <= 22.0D && green >= 85.0D && green <= 105.0D && red >= 240.0D && red <= 255.0D) {
                        scalarImagemProcessada.setVal(0, 0.0D);
                        scalarImagemProcessada.setVal(1, 255.0D);
                        scalarImagemProcessada.setVal(2, 128.0D);
                        cvSet2D(mtx, altura, largura, scalarImagemProcessada);
                        ++laranjaCamisaBart;
                    }

                    if (altura > imagemProcessada.height() / 2 && blue >= 125.0D && blue <= 170.0D && green >= 0.0D && green <= 12.0D && red >= 0.0D && red <= 20.0D) {
                        scalarImagemProcessada.setVal(0, 0.0D);
                        scalarImagemProcessada.setVal(1, 255.0D);
                        scalarImagemProcessada.setVal(2, 128.0D);
                        cvSet2D(mtx, altura, largura, scalarImagemProcessada);
                        ++azulCalcaoBart;
                    }

                    if (altura > imagemProcessada.height() / 2 + imagemProcessada.height() / 3 && blue >= 125.0D && blue <= 140.0D && green >= 3.0D && green <= 12.0D && red >= 0.0D && red <= 20.0D) {
                        scalarImagemProcessada.setVal(0, 0.0D);
                        scalarImagemProcessada.setVal(1, 255.0D);
                        scalarImagemProcessada.setVal(2, 128.0D);
                        cvSet2D(mtx, altura, largura, scalarImagemProcessada);
                        ++azulSapatoBart;
                    }

                    if (blue >= 150.0D && blue <= 180.0D && green >= 98.0D && green <= 120.0D && red >= 0.0D && red <= 90.0D) {
                        scalarImagemProcessada.setVal(0, 0.0D);
                        scalarImagemProcessada.setVal(1, 255.0D);
                        scalarImagemProcessada.setVal(2, 255.0D);
                        cvSet2D(mtx, altura, largura, scalarImagemProcessada);
                        ++azulCalcaHomer;
                    }

                    if (altura < imagemProcessada.height() / 2 + imagemProcessada.height() / 3 && blue >= 95.0D && blue <= 140.0D && green >= 160.0D && green <= 185.0D && red >= 175.0D && red <= 200.0D) {
                        scalarImagemProcessada.setVal(0, 0.0D);
                        scalarImagemProcessada.setVal(1, 255.0D);
                        scalarImagemProcessada.setVal(2, 255.0D);
                        cvSet2D(mtx, altura, largura, scalarImagemProcessada);
                        ++marromBocaHomer;
                    }

                    if (altura > imagemProcessada.height() / 2 + imagemProcessada.height() / 3 && blue >= 25.0D && blue <= 45.0D && green >= 25.0D && green <= 45.0D && red >= 25.0D && red <= 45.0D) {
                        scalarImagemProcessada.setVal(0, 0.0D);
                        scalarImagemProcessada.setVal(1, 255.0D);
                        scalarImagemProcessada.setVal(2, 255.0D);
                        cvSet2D(mtx, altura, largura, scalarImagemProcessada);
                        ++cinzaSapatoHomer;
                    }
                }
            }

            new IplImage(mtx);
            laranjaCamisaBart = laranjaCamisaBart / (float) (imagemOriginal.height() * imagemOriginal.width()) * 100.0F;
            azulCalcaoBart = azulCalcaoBart / (float) (imagemOriginal.height() * imagemOriginal.width()) * 100.0F;
            azulSapatoBart = azulSapatoBart / (float) (imagemOriginal.height() * imagemOriginal.width()) * 100.0F;
            azulCalcaHomer = azulCalcaHomer / (float) (imagemOriginal.height() * imagemOriginal.width()) * 100.0F;
            marromBocaHomer = marromBocaHomer / (float) (imagemOriginal.height() * imagemOriginal.width()) * 100.0F;
            cinzaSapatoHomer = cinzaSapatoHomer / (float) (imagemOriginal.height() * imagemOriginal.width()) * 100.0F;
            caracteristicas[i][0] = laranjaCamisaBart;
            caracteristicas[i][1] = azulCalcaoBart;
            caracteristicas[i][2] = azulSapatoBart;
            caracteristicas[i][3] = azulCalcaHomer;
            caracteristicas[i][4] = marromBocaHomer;
            caracteristicas[i][5] = cinzaSapatoHomer;
            caracteristicas[i][6] = (float) classePersonagem;
            System.out.println("Laranja camisa Bart: " + caracteristicas[i][0] + " - Azul calção Bart: " + caracteristicas[i][1] + " - Azul sapato Bart: " + caracteristicas[i][2] + " - Azul calça Homer: " + caracteristicas[i][3] + " - Marrom boca Homer: " + caracteristicas[i][4] + " - Preto sapato Homer: " + caracteristicas[i][5] + " - Classe: " + caracteristicas[i][6]);
            exportacao = exportacao + caracteristicas[i][0] + "," + caracteristicas[i][1] + "," + caracteristicas[i][2] + "," + caracteristicas[i][3] + "," + caracteristicas[i][4] + "," + caracteristicas[i][5] + "," + classePersonagemString + "\n";
        }

        try {
            File arquivo = new File("caracteristicas.arff");
            FileOutputStream f = new FileOutputStream(arquivo);

            f.write(exportacao.getBytes());

            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
