package Modele;

import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;


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
		afficher_message(String.valueOf(getSoldeClient())+"€");
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
	
	private double getSoldeMachine()
	{
		double somme = 0;
		try
		{
			for(Piece p : stock_pieces)
			{
				System.out.println(p.getQte()+" pieces de "+p.toString());
				somme += (p.getValeur() * p.getQte());
			}
		}
		catch(Exception e)
		{
			return 0;
		}
		return (double)Math.round(somme * 100) / 100;
	}
	
	private double getSoldeClient()
	{
		double somme = 0;
		try
		{
			for(Piece p : pieces_inserees)
				somme += p.getValeur();
		}
		catch(Exception e)
		{
			return 0;
		}
		return (double)Math.round(somme * 100) / 100;
	}

	private boolean solde_suffisant()
	{
		return getSoldeClient() >= produit_select.getPrix();
	}
	
	public boolean rendre_monnaie(boolean act)
	{
		if(produit_select == null)
		{
			if(act)	afficher_message("Rendu : "+getSoldeClient()+"€");
			pieces_inserees.clear();
			return true;
		}
		double solde = getSoldeClient();
		double prix = produit_select.getPrix();
		double a_rendre = solde - prix;
		a_rendre = (double)Math.round(a_rendre * 100.00) / 100.00;
		
		if (act) afficher_message("Rendu : "+a_rendre+"€");
		
		// Chercher les pieces qui peuvent etre rendues dans les pieces inserees
		for(int i = 0 ; i < pieces_inserees.size() ; i++)
		{
			if(a_rendre == 0 && act == false) return true;
			if(pieces_inserees.get(i).getValeur() <= a_rendre && pieces_inserees.get(i).getQte() > 0)
			{
				a_rendre -= pieces_inserees.get(i).getValeur();
				a_rendre = (double)Math.round(a_rendre * 100) / 100;
				if (act)
				{
					pieces_inserees.remove(i);
				}
				i--;
			}
		}
		// Chercher les pieces qui peuvent etre rendues dans le stock machine
		for(int i = 0 ; i < stock_pieces.size() ; i++)
		{
			if(a_rendre == 0 && act == false) return true;
			if(stock_pieces.get(i).getValeur() <= a_rendre && stock_pieces.get(i).getQte() > 0)
			{
				a_rendre -= stock_pieces.get(i).getValeur();
				a_rendre = (double)Math.round(a_rendre * 100) / 100;
				if (act)
				{
					stock_pieces.get(i).retirer();
				}
				i--;
			}
		}
		// Ajouter les pieces inserees restantes au stock machine
		if (act)
		{
			for(Piece p : pieces_inserees)
			{
				for(Piece p_machine : stock_pieces)
				{
					if (p.getValeur() == p_machine.getValeur())
						p_machine.ajouter();
				}
			}
			pieces_inserees.clear();
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
			rendre_monnaie(true);
			produit_select = null;
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
		euro deuxEuros = euro.DEUXEUROS;
		euro unEuro = euro.UNEURO;
		euro cinquanteCents = euro.CINQUANTECENTS;
		euro vingtCents = euro.VINGTCENTS;
		euro dixCents = euro.DIXCENTS;
		euro cinqCents = euro.CINQCENTS;
		euro deuxCents = euro.DEUXCENTS;
		euro unCent = euro.UNCENT;
		List<Piece> stock_pieces = new ArrayList<Piece>();
		stock_pieces.add(deuxEuros);
		stock_pieces.add(unEuro);
		stock_pieces.add(cinquanteCents);
		stock_pieces.add(vingtCents);
		stock_pieces.add(dixCents);
		stock_pieces.add(cinqCents);
		stock_pieces.add(deuxCents);
		stock_pieces.add(unCent);
		
		List<Boisson> stock_produits = new ArrayList<Boisson>();
		stock_produits.add(new Boisson("Coca", 0.2, 6, "canette", true));
		stock_produits.add(new Boisson("Orangina", 0.45, 5, "bouteille", true));
		
		Distributeur<Boisson> dist = new Distributeur<Boisson>(stock_produits, stock_pieces);		
		
		dist.inserer_piece(cinqCents);
		dist.inserer_piece(deuxCents);
		dist.inserer_piece(cinqCents);
		dist.inserer_piece(dixCents);
		
		dist.choisir_produit("Coca");
		
		System.out.println(dist.getSoldeMachine());
		
		dist.rendre_monnaie(true);
		
	}
	
}