package Modele;

public class Boisson extends Produit {

	private String format;
	private boolean froid;
	
	Boisson(String nom, double prix, int qte, String format, boolean froid) {
		super(nom, prix, qte);
		// TODO Auto-generated constructor stub
		this.format = format;
		this.froid = froid;
	}

}
