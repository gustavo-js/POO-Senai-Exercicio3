package Beneficiarios;

public enum Categorias {
    Empregado (1),
    Empregador (2),
    Desempregado (3);
	
	public int valor;
	
	private Categorias(int valor) {
		this.valor = valor;
	}
}
