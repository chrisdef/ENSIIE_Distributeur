package Modele;

import java.util.ArrayList;
import java.util.List;


public class Distributeur<T extends Produit> {
	
	private List<T> stock_produits;
	private T produit_select;
	private List<euro> stock_pieces;
	private List<euro> pieces_inserees;
	
	
	public Distributeur (List<T> stock_produits, List<euro> stock_pieces)
	{
		this.stock_produits = stock_produits;
		produit_select = null;
		this.stock_pieces = stock_pieces;
		pieces_inserees = new ArrayList<euro>();
		initialiser_stock(10);
	}
	
	private void initialiser_stock(int nb)
	{
		for(euro piece : this.stock_pieces)
			piece.setQte(nb);
	}
	
	public boolean choisir_produit(String nom)
	{
		for(T p : stock_produits)
		{
			if( p.getNom() == nom && p.getQte() > 0)
			{
				produit_select = p;
				return true;
			}
		}
		return false;
	}
	
	private boolean solde_suffisant()
	{
		int somme = 0;
		if(produit_select == null) return false;
		
		try
		{
			for(euro p : pieces_inserees)
				somme += p.getValeur();
		}
		catch(Exception e)
		{
			return false;
		}
		
		return somme > produit_select.getPrix();
	}
	
	public boolean delivrer ()
	{
		if(solde_suffisant())
		{
			try
			{
				produit_select.piocherStock();
			}
			catch (NullPointerException e)
			{
				afficher_message("Choississez un produit SVP");
				return false;
			}
			catch (Exception e)
			{
				afficher_message("Produit indisponible");
				produit_select = null;
				return false;
			}
			produit_select = null;
			return true;
		}
		else return false;
	}
	
	public void afficher_message(String message)
	{
		System.out.println(message);
	}
	
	public void inserer_piece(euro p)
	{
		pieces_inserees.add(p);
	}
	
	public boolean rendre_monnaie()
	{
		if(produit_select == null)
		{
			return true;
		}
		return true;
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
		List<euro> stock_pieces = new ArrayList<euro>();
		stock_pieces.add(euro.UNCENT);
		stock_pieces.add(euro.UNEURO);
		for(euro p : stock_pieces)
			System.out.println(p.getQte()+" pieces de "+p);
		
		List<Produit> stock_produits = new ArrayList<Produit>();
		stock_produits.add(new Produit("Coca", 0.5, 6));
		stock_produits.add(new Produit("Orangina", 0.45, 5));
		for(Produit p : stock_produits)
			System.out.println(p.getQte()+" canettes de "+p.getNom()+" à "+p.getPrix()+"€");
		
		Distributeur<Produit> dist = new Distributeur<Produit>(stock_produits, stock_pieces);
		dist.afficher_message("Je suis un distributeur");
		dist.afficher_message("Produit choisi : "+dist.getProduitChoisi());
		dist.choisir_produit("Coca");
		dist.afficher_message("Produit choisi : "+dist.getProduitChoisi());
		
		euro un = euro.UNEURO;
		
		dist.inserer_piece(un);
		
		for(int i = 0 ; i < 8 ; i++)
		{
			if(dist.delivrer())
				System.out.println("produit "+i+" délivré");
			else
				System.out.println("produit "+i+" non délivré");
		}
	}
	
}