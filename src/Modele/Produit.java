package Modele;

public class Produit 
{
	private String nom;
	private double prix;
	private int qte;
	
	Produit(String nom, double prix, int qte)
	{
		this.nom = nom;
		this.prix = prix;
		this.qte = qte;
	}
	
	public double getPrix() 
	{
		return prix;
	}
	
	public void setPrix(double prix) 
	{
		this.prix = prix;
	}
	
	public String getNom() 
	{
		return nom;
	}
	
	public void setNom(String nom) 
	{
		this.nom = nom;
	}

	public int getQte() 
	{
		return qte;
	}

	public void setQte(int qte) 
	{
		this.qte = qte;
	}

}
