package Beneficiarios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Pessoa {
    private String nomeCompleto;
    private LocalDate dataNascimento;
    private Categorias categoria;
    private String estado;
    private ArrayList<String> regrasAtendidas;
    private int tempoBeneficio;
    private double valorBeneficio;

    public Pessoa(){
        regrasAtendidas = new ArrayList<String>();
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Categorias getCategoria() {
        return categoria;
    }

    public void setCategoria(Categorias categoria) {
        this.categoria = categoria;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ArrayList<String> getRegrasAtendidas() {
        return regrasAtendidas;
    }

    public int getTempoBeneficio() {
        return tempoBeneficio;
    }

    public double getValorBeneficio() {
        return valorBeneficio;
    }

    public double obterValorBeneficio(int tempoDesempregado, int qtdFuncionarios){
        double valorBeneficio = 0;
        if(LocalDate.now().getYear() - dataNascimento.getYear() >= 18)
        {
            regrasAtendidas.add("Benefício concedido pois o beneficiário é maior de idade");
            if(categoria == Categorias.Empregado){
                valorBeneficio = (Math.random() * 800) + 1000;
            }
            if(categoria == Categorias.Empregador){
                valorBeneficio = qtdFuncionarios * 200;
            }
            if(categoria == Categorias.Desempregado){
                valorBeneficio = (Math.random() * 800) + 1500;
                if(tempoDesempregado < 6){
                    valorBeneficio *= 0.9;
                    regrasAtendidas.add("Benefício reduzido em 10% pois o beneficiário está desempregado a menos de 6 meses");
                }
            }
            if(estado.equalsIgnoreCase("SP")){
                valorBeneficio *= 1.10;
                regrasAtendidas.add("Benefício aumentado em 10% pois o beneficiário é de São Paulo");
            }
            if(estado.equalsIgnoreCase("PA")){
                valorBeneficio *= 1.09;
                regrasAtendidas.add("Benefício aumentado em 9% pois o beneficiário é do Pará");
            }
        } else{
            regrasAtendidas.add("Benefício não concedido pois o beneficiário é menor de idade");
        }
        this.valorBeneficio = valorBeneficio;
        return this.valorBeneficio;
    }

    public int obterMesesBeneficio(boolean aposentado){
        int mesesBeneficio = 2;
        if(aposentado){
            mesesBeneficio = 6;
            regrasAtendidas.add("Benefício será de 6 meses pois o beneficiário é aposentado");
        } else{
            mesesBeneficio = (int)(Math.random() * 10) + 2;
        }
        this.tempoBeneficio = mesesBeneficio;
        return this.tempoBeneficio;
    }
}
