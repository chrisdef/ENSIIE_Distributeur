package Modele;

public class Distributeur<T extends Produit> {
	
	private T produit [];
	
	public Distributeur (T produit [])
	{
		this.produit = produit;
	}
	
	public boolean delivrer (T produit)
	{
		for (int i = 0 ; i < this.produit.length ; i++)
		{
			if (this.produit[i].getNom() == produit.getNom())
			{
				try
				{
					this.produit[i].piocherStock();
				}
				catch (Exception e)
				{
					return false;
				}
				return true;
			}
		}
		return false;
	}
	
}
