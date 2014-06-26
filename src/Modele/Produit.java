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
	
	public String toString()
	{
		return nom + " : " + prix + "€";
	}
	
	public double getPrix() 
	{
		return (double)Math.round(prix * 100) / 100;
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
	
	public void piocherStock() throws Exception
    {
            if (qte == 0) throw new Exception("Quantite insuffisante de "+nom+" dans le stock");
            else qte--;
    }


}
