package Modele;

import java.util.ArrayList;
import java.util.List;


public class Distributeur<T extends Produit> {
	
	private List<T> stock_produits;
	private List<Piece> stock_pieces;
	private List<Piece> pieces_inserees;
	private T produit_select;
	public Distributeur (List<T> stock_produits, List<Piece> stock_pieces)
	{
		this.stock_produits = stock_produits;
		this.stock_pieces = stock_pieces;
		pieces_inserees = new ArrayList<Piece>();
		produit_select = null;
		initialiser_stock(10);
	}
	
	private void initialiser_stock(int nb)
	{
		for(Piece piece : this.stock_pieces)
			piece.setQte(nb);
	}
	
	public void afficher_message(String message)
	{
		System.out.println(message);
	}

	public void inserer_piece(Piece p)
	{
		pieces_inserees.add(p);
	}

	public boolean choisir_produit(String nom)
	{
		for(T p : stock_produits)
		{
			if( p.getNom() == nom && p.getQte() > 0)
			{
				produit_select = p;
				if(!delivrer())
					afficher_message(produit_select.toString());
				return true;
			}
		}
		return false;
	}
	
	private double getSolde()
	{
		int somme = 0;
		try
		{
			for(Piece p : pieces_inserees)
				somme += p.getValeur();
		}
		catch(Exception e)
		{
			return 0;
		}
		return somme;
	}

	private boolean solde_suffisant()
	{
		return getSolde() >= produit_select.getPrix();
	}
	
	private boolean rendre_monnaie(boolean act)
	{
		double solde = getSolde();
		double prix = produit_select.getPrix();
		double a_rendre = solde - prix;
		if(produit_select == null)
		{
			pieces_inserees.clear();
			return true;
		}
		for(Piece p : pieces_inserees)
		{
			if(a_rendre == 0) return true;
			if(p.getValeur() <= a_rendre)
			{
				a_rendre -= p.getValeur();
			//	pieces_inserees.remove(p);
			}
		}
		for(int i = 0 ; i < stock_pieces.size() ; i++)
		{
			if(a_rendre == 0) return true;
			if(stock_pieces.get(i).getValeur() <= a_rendre && stock_pieces.get(i).getQte() > 0)
			{
				a_rendre -= stock_pieces.get(i).getValeur();
			//	stock_pieces.get(i).setQte(stock_pieces.get(i).getQte() - 1);
				//i--;
				System.out.println("rendu : "+stock_pieces.get(i).toString()+", a rendre : "+a_rendre);
			}
		}
		return a_rendre == 0;
	}
	
	private boolean delivrer ()
	{
		if(solde_suffisant())
		{
			if(!rendre_monnaie(false))
			{
				afficher_message("Pas assez de monnaie à rendre");
				return false;
			}
			try
			{
				produit_select.piocherStock();
			}
			catch (Exception e)
			{
				afficher_message("Désolé. Il n'y a plus de "+produit_select.getNom());
				produit_select = null;
				return false;
			}
			afficher_message("Voici votre "+produit_select.getNom());
			produit_select = null;
			rendre_monnaie(true);
			return true;
		}
		else 
		{
			afficher_message("Faites l'appoint");
			return false;
		}
	}
	
	public String getProduitChoisi()
	{
		try
		{
			return produit_select.getNom();
		}
		catch(Exception e)
		{
			return "Aucun produit choisi";
		}
	}
	
	public static void main(String args [])
	{
		List<Piece> stock_pieces = new ArrayList<Piece>();
		stock_pieces.add(euro.DEUXEUROS);
		stock_pieces.add(euro.UNEURO);
		stock_pieces.add(euro.CINQUANTECENTS);
		stock_pieces.add(euro.VINGTCENTS);
		stock_pieces.add(euro.DIXCENTS);
		stock_pieces.add(euro.CINQCENTS);
		stock_pieces.add(euro.DEUXCENTS);
		stock_pieces.add(euro.UNCENT);
		for(Piece p : stock_pieces)
			System.out.println(p.getQte()+" pieces de "+p);
		
		List<Produit> stock_produits = new ArrayList<Produit>();
		stock_produits.add(new Produit("Coca", 0.2, 6));
		stock_produits.add(new Produit("Orangina", 0.45, 5));
		for(Produit p : stock_produits)
			System.out.println(p.getQte()+" canettes de "+p.getNom()+" à "+p.getPrix()+"€");
		
		Distributeur<Produit> dist = new Distributeur<Produit>(stock_produits, stock_pieces);
		
		euro un = euro.DEUXEUROS;
		dist.inserer_piece(un);
		
		dist.choisir_produit("Coca");
	}
	
}