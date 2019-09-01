package glauber.deeplearning.imagens.service;

import glauber.deeplearning.imagens.dto.Caracteristicas;
import org.springframework.stereotype.Service;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.File;
import java.text.DecimalFormat;

@Service
public class BaseWeka {
    private Instances instances;
    private ExtratorCaracteristicas extratorCaracteristicas;

    public BaseWeka(ExtratorCaracteristicas extratorCaracteristicas) {
        this.extratorCaracteristicas = extratorCaracteristicas;
        try {
            ConverterUtils.DataSource dataSource = new ConverterUtils.DataSource("caracteristicas.arff");
            //seta a classe, onde estao as opções possiveis
            instances = dataSource.getDataSet();
            instances.setClassIndex(6);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String classificaNaveBayes(File file) {
        //   carregarWeka();
        //criação da tabela de probabilidade
        NaiveBayes naiveBayes = new NaiveBayes();
        try {
            naiveBayes.buildClassifier(instances);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String result = "";
        //Criação de novo Registro
        Instance novoRegistro = criarNovoRegistro(file);

        try {
            double[] resultado = naiveBayes.distributionForInstance(novoRegistro);
            DecimalFormat df = new DecimalFormat("#,###.0000");
            result = "Bart: " + df.format(resultado[0] * 100) + "% " + "Homer: " + df.format(resultado[1] * 100) + "%";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String classificaJ48(File file) {
        J48 arvore = new J48();
        String result = "";
        try {
            //cria uma arvore de decisão
            arvore.buildClassifier(instances);
            Instance novoRegistro = criarNovoRegistro(file);
            double[] resultado = arvore.distributionForInstance(novoRegistro);
            DecimalFormat df = new DecimalFormat("#,###.0000");
            result = "Bart: " + df.format(resultado[0] * 100) + "% " + "Homer: " + df.format(resultado[1] * 100) + "%";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Instance criarNovoRegistro(File file) {
        Caracteristicas caracteristicas = extratorCaracteristicas.extrairCaracteristicas(file);
        Instance novoRegistro = new DenseInstance(instances.numAttributes());
        novoRegistro.setDataset(instances);
        novoRegistro.setValue(0, caracteristicas.getLaranjaCamisaBart());
        novoRegistro.setValue(1, caracteristicas.getAzulCalcaoBart());
        novoRegistro.setValue(2, caracteristicas.getAzulSapatoBart());
        novoRegistro.setValue(3, caracteristicas.getMarromBocaHomer());
        novoRegistro.setValue(4, caracteristicas.getAzulCalcaHomer());
        novoRegistro.setValue(5, caracteristicas.getCinzaSapatoHomer());
        return novoRegistro;
    }

}
