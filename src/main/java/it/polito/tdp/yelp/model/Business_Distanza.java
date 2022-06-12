package it.polito.tdp.yelp.model;

public class Business_Distanza {
	String nome;
	double distanza;
	
	
	public Business_Distanza(String nome, double distanza) {
		super();
		this.nome = nome;
		this.distanza = distanza;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getDistanza() {
		return distanza;
	}
	public void setDistanza(double distanza) {
		this.distanza = distanza;
	}
	@Override
	public String toString() {
		return this.nome+" = "+this.distanza;
	}
	
	
}
