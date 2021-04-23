package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	
	private List<Rilevamento> rilevamenti;
	private Map<String, Double> umiditaMedia;
	private MeteoDAO meteoDao;
	
	private List<Citta> citta;
	private List<Citta> soluzioneMigliore;
	private List<Citta> parziale;
	private double costoMigliore;

	public Model() {
		meteoDao=new MeteoDAO();
	}

	// of course you can change the String output with what you think works best
	public Map<String, Double> getUmiditaMedia(int mese) {
		rilevamenti=meteoDao.getAllRilevamentiMese(mese);
		umiditaMedia=new HashMap<>();
		calcolaMediaUmidita();
		/*
		for(Rilevamento r:rilevamenti)
			System.out.println(r+" "+r.getLocalita()+"\n");
			*/
		
		return umiditaMedia;
	}

	// of course you can change the String output with what you think works best
	public List<Citta> trovaSequenza(int mese) {
		citta=new ArrayList<>();
		citta.add(new Citta("genova",meteoDao.getAllRilevamentiLocalitaMese(mese, "Genova")));
		citta.add(new Citta("milano",meteoDao.getAllRilevamentiLocalitaMese(mese, "Milano")));
		citta.add(new Citta("torino",meteoDao.getAllRilevamentiLocalitaMese(mese, "Torino")));
		
		parziale=new ArrayList<>();
		costoMigliore=Double.MAX_VALUE;
		cerca(parziale,0);
		
		return soluzioneMigliore;
	}
	
	
	
	private void cerca(List<Citta> parziale, int livello) {
		//caso terminale
		if(parziale.size()==this.NUMERO_GIORNI_TOTALI) {
			double costo=0;
			//calcolo costo soluzione
			for(int i=0; i<parziale.size(); i++) {
				costo+=parziale.get(i).getRilevamenti().get(i).getUmidita();
				if(i>0) {
					if(!(parziale.get(i).getNome().equals(parziale.get(i-1).getNome())))
						costo+=this.COST;
				}
			}
			
			if(costo<costoMigliore) {
				soluzioneMigliore=new ArrayList<>(parziale);
				costoMigliore=costo;
			}
			return;
		}
		
		//ricorsione
		for(Citta c:citta) {
			if(c.getCounter()<this.NUMERO_GIORNI_CITTA_MAX) {
				boolean nuova=false;
				Citta cittaPrecedente1=null;
				Citta cittaPrecedente2=null;
				
				parziale.add(c);
				c.increaseCounter();
				
				if(livello>0 && livello<3) {
					//controllo di rimanere nella stessa città i primi 3 giorni
					if(!(parziale.get(livello).getNome().equals(parziale.get(livello-1).getNome())))
						return;
				}
				if(livello>=3) {
					//controllo se ho cambiato citta -> se si verifico poi di rimanerci almeno 3 giorni
					if(!(parziale.get(livello).getNome().equals(parziale.get(livello-1).getNome()) && !nuova)) {
						nuova=true;
						cittaPrecedente1=parziale.get(livello);
						cittaPrecedente2=null;
					}
					else if(nuova && cittaPrecedente2==null) {
						if(parziale.get(livello).getNome().equals(cittaPrecedente1.getNome()))
							cittaPrecedente2=parziale.get(livello);
						else 
							return;
					}
					else if(nuova && cittaPrecedente2!=null) {
						if(parziale.get(livello).getNome().equals(cittaPrecedente2.getNome()))
							nuova=false;
						else
							return;
					}
				}
				
				cerca(parziale, livello+1);
				parziale.remove(c);
				c.setCounter(c.getCounter()-1);
			}
		}
		
	}

	private void calcolaMediaUmidita() {
		int rilGenova=0;
		int umiditaGen=0;
		int rilTorino=0;
		int umiditaTor=0;
		int rilMilano=0;
		int umiditaMil=0;
		
		for(int i=0; i<rilevamenti.size(); i++) {
			if(rilevamenti.get(i).getLocalita().equals("Genova")) {
				umiditaGen+=rilevamenti.get(i).getUmidita();
				rilGenova++;
				//System.out.println(rilTorino);
			} else if (rilevamenti.get(i).getLocalita().equals("Milano")) {
				umiditaMil+=rilevamenti.get(i).getUmidita();
				rilMilano++;
			} else if (rilevamenti.get(i).getLocalita().equals("Torino")) {
				umiditaTor+=rilevamenti.get(i).getUmidita();
				rilTorino++;
				//System.out.println(rilTorino);
			}
		}
		Double umidita= (double)umiditaGen/(double)rilGenova;
		umiditaMedia.put("genova", umidita);
		//System.out.println(umidita);
		
		umidita=(double)umiditaMil/(double)rilMilano;
		umiditaMedia.put("milano", umidita);
		//System.out.println(umidita);

		umidita=(double)umiditaTor/(double)rilTorino;
		umiditaMedia.put("torino", umidita);
		//System.out.println(umidita);

		
	}

}
