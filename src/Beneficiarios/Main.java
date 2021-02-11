package Beneficiarios;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import conexao.com.br.Conexao;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuarPrograma = true;
        Pessoa pessoa;
        boolean aposentado;
        int qtdFuncionarios;
        int qtdMesesDesempregado;
        Conexao con = new Conexao();
        
        System.out.println("O quê você deseja fazer? 1 - Cadastrar / 2 - Excluir");
        
        if (scanner.nextInt() == 1) {
        	scanner.nextLine();
	        while(continuarPrograma){
	            pessoa = new Pessoa();
	            aposentado = false;
	            qtdFuncionarios = 0;
	            qtdMesesDesempregado = 0;
	
	            System.out.println("Digite o nome completo");
	            pessoa.setNomeCompleto(scanner.nextLine());
	
	            System.out.println("Digite a data de nascimento");
	            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	            pessoa.setDataNascimento(LocalDate.parse(scanner.next(), df));
	
	            System.out.println("Escolha a categoria: \n 1- Empregado \n 2- Empregador \n 3- Desempregado");
	            switch (scanner.nextInt()){
	                case 1:
	                    pessoa.setCategoria(Categorias.Empregado);
	                    System.out.println("O beneficiário é aposentado? \n S - Sim  \n N - Não");
	                    if(scanner.next().equalsIgnoreCase("S")){
	                        aposentado = true;
	                    } else{
	                        aposentado = false;
	                    }
	                    break;
	                case 2:
	                    pessoa.setCategoria(Categorias.Empregador);
	                    System.out.println("O beneficiário possui quantos funcionários?");
	                    qtdFuncionarios = scanner.nextInt();
	                    break;
	                case 3:
	                    pessoa.setCategoria(Categorias.Desempregado);
	                    System.out.println("O beneficiário está desempregado há quantos meses?");
	                    qtdMesesDesempregado = scanner.nextInt();
	                    break;
	            }
	
	            System.out.println("Digite a sua UF");
	            pessoa.setEstado(scanner.next());
	
	            int mesesBeneficio = pessoa.obterMesesBeneficio(aposentado);
	            double valorBeneficio = pessoa.obterValorBeneficio(qtdMesesDesempregado, qtdFuncionarios);
	            
	            
	    		con.executeQuery("INSERT INTO beneficios.pessoa (nome, data, categoria, estado, tempo_beneficio, valor_beneficio) VALUES ('" + pessoa.getNomeCompleto() + "', '" + pessoa.getDataNascimento() + "', '" + pessoa.getCategoria().valor + "', '" + pessoa.getEstado() + "', '" + mesesBeneficio + "', '" + valorBeneficio + "')");
	
	            System.out.println("Nome Completo: " + pessoa.getNomeCompleto());
	            System.out.println("Data de Nascimento: " + pessoa.getDataNascimento());
	            System.out.println("Categoria: " + pessoa.getCategoria());
	            System.out.println("Regras atendidas: " + pessoa.getRegrasAtendidas());
	            System.out.println("Meses de benefício: " + mesesBeneficio);
	            System.out.println("Valor do benefício: " + valorBeneficio);
	
	            System.out.println("Deseja informar um novo beneficiário? \n S - Sim  \n N - Não");
	            if(!scanner.next().equalsIgnoreCase("S")){
	                continuarPrograma = false;
	            }
	            scanner.nextLine();
	        }
	        
	        ResultSet rs = con.executeSearch("select count(*) from beneficios.pessoa;");
	        ResultSet rs2 = con.executeSearch("select sum(valor_beneficio) as total_beneficio from beneficios.pessoa;");
	        ResultSet rs3 = con.executeSearch("select sum(tempo_beneficio) as total_tempo_beneficio from beneficios.pessoa;");
	        ResultSet rs4 = con.executeSearch("select * from beneficios.pessoa ORDER BY valor_beneficio DESC limit 2;");
	        ResultSet rs5 = con.executeSearch("select * from beneficios.pessoa ORDER BY tempo_beneficio DESC limit 2;");
	        
	        int count = 0;
	        double totalBeneficio = 0;
	        int totalTempoBeneficio = 0;
	        ArrayList<String> beneficiariosMaiorValor = new ArrayList<>();
	        ArrayList<String> beneficiariosMaiorTempo = new ArrayList<>();
	        
	        try {
				while(rs.next()) {
					count = rs.getInt("count");
				}
				
				while(rs2.next()) {
					totalBeneficio = rs2.getDouble("total_beneficio");
				}
				
				while(rs3.next()) {
					totalTempoBeneficio = rs3.getInt("total_tempo_beneficio");
				}
				
				while(rs4.next()) {
					beneficiariosMaiorValor.add(rs4.getString("nome"));
				}
				
				while(rs5.next()) {
					beneficiariosMaiorTempo.add(rs5.getString("nome"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	        System.out.println("Total de Usuários Lidos: " + count);
	        System.out.println("Total de valor concedido: " + totalBeneficio * totalTempoBeneficio);
	        System.out.println("Beneficiários que irão receber os maiores valores: " + beneficiariosMaiorValor);
	        System.out.println("Beneficiários que irão receber por mais tempo: " + beneficiariosMaiorTempo);
        } else {
        	System.out.println("Digite o ID do beneficiário que deseja excluir: ");
        	int id = scanner.nextInt();
        	con.executeQuery("DELETE from beneficios.pessoa WHERE id = "+ id);
        }
    }
}
