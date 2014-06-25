package Modele;

public enum euro implements Piece
{	
	// Enumeration
	UNCENT  (0.01) ,
	DEUXCENTS (0.02) ,
	CINQCENTS(0.05) ,
	DIXCENTS (0.10) ,
	VINGTCENTS (0.20) ,
	CINQUANTECENTS (0.50) ,
	UNEURO  (1) ,
	DEUXEUROS  (2);
	
	private double value;
	private int qte;
	
	private euro (double v)
	{
		value = v ;
	}

	@Override
	public double getValeur() 
	{
		return value;
	}
	
	public int getQte()
	{
		return qte;
	}
	
	public void setQte(int qte)
	{
		this.qte = qte;
	}
	
	public String toString()
	{
		return value + "€";
	}
	
}
