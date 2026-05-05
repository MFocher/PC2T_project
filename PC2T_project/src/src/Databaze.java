package src;
import java.util.Scanner;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Databaze {
	
	private Scanner sc;
	private Map<Integer, Zamestnanec> vnitrniDatabaze;
	private List <Spoluprace> seznamSpolupraci;	
	private Zamestnanec aktualniProfil = null;
	private final String URL_SQL = "jdbc:sqlite:firemni_databaze.db";
	
	public Databaze() {
		this.sc = new Scanner(System.in);
		this.vnitrniDatabaze = new HashMap<>();
		this.seznamSpolupraci = new ArrayList<>();
	}
	
	public void viewSkupiny() {
		System.out.println("\n│-----------------------------------------│");
		System.out.println("	Zvolte skupinu k prirazeni: ");
		
        System.out.println("\n	1 ... Datoví analytici");
        System.out.println("	2 ... Bezpecnostní specialisté");
        System.out.println("│-----------------------------------------│\n");
	}
	
	//	1
	public void addZamestnanec() {
		viewSkupiny();
		int skupina = sc.nextInt();
		while (skupina != 1 && skupina != 2) {
	        System.out.println("	-Neplatna skupina (volte 1 nebo 2):");
	        skupina = sc.nextInt();
	    }
		System.out.println("\n│-----------------------------------------│");
		System.out.println("  Zadejte JMENO, PRIJMENI a ROK NAROZENI:");
        System.out.println("\n│-----------------------------------------│\n");
		
        String jmeno = sc.next();
        String prijmeni = sc.next();
        int rok = sc.nextInt();
        
        Random r = new Random();
        int maxIC = 999999, minIC = 100000;
        int IC = r.nextInt((maxIC - minIC + 1) + minIC);
        
        Zamestnanec novy = new Zamestnanec(IC, jmeno, prijmeni, rok, skupina);
        
        vnitrniDatabaze.put(IC, novy);
        
        System.out.println("	-Pridan zamestnanec: " + novy.getJmeno() + " " + novy.getPrijmeni() + ", rok narozeni: " + novy.getRok() + ", IC: " + novy.getIC() + ", skupina: " + novy.getSkupina());
    }
	
	//	2
	public void removeZamestnanec() {
		if (vnitrniDatabaze.isEmpty()) {
			System.out.println("	-Nelze odebrat, nebot databaze je prazdna!");
			return;
		}
		
		System.out.println("\n│-----------------------------------------│");
		System.out.println("  Vyberte IC zamestnance k odebrani: ");
		System.out.println("\n│-----------------------------------------│\n");
		for (Zamestnanec s : vnitrniDatabaze.values()) {
			System.out.println("	-Zamestnanec: " + s.getJmeno() + " " + s.getPrijmeni() + ", rok narozeni: " + s.getRok() + ", IC: " + s.getIC() + ", skupina: " + s.getSkupina());
        }
		int removeIC = sc.nextInt();
		
		Zamestnanec remove = vnitrniDatabaze.remove(removeIC);
		if (remove != null) {
			if (seznamSpolupraci != null) {
	            seznamSpolupraci.removeIf(spol -> spol.getZamestnanecIC1() == removeIC || spol.getZamestnanecIC2() == removeIC);
	        }
			
			System.out.println("	-Odebran zamestnanec: " + remove.getJmeno() + " " + remove.getPrijmeni() + ", rok narozeni: " + remove.getRok() + ", IC: " + remove.getIC() + ", skupina: " + remove.getSkupina());			
			
			return;
			
		} else {
			System.out.println("	-Zamestnanec s IC: " + removeIC + " nenalezen!");
		}
	}
	
	//	3
	public void addSpoluprace() {
		if (vnitrniDatabaze.size() < 2) {
			System.out.println("	-Nelze vytvorit spolupraci, s méně než 2 osobami!");
			return;
		} else if (vnitrniDatabaze.isEmpty()) {
			System.out.println("	-Nelze vytvorit spolupraci, nebot databaze je prazdna!");
			return;
		}
		
		System.out.println("\n│-----------------------------------------│");
		System.out.println("	Vyberte IC zamestnance: ");
		System.out.println("\n│-----------------------------------------│\n");
		for (Zamestnanec s : vnitrniDatabaze.values()) {
			System.out.println("	-Zamestnanec: " + s.getJmeno() + " " + s.getPrijmeni() + ", rok narozeni: " + s.getRok() + ", IC: " + s.getIC() + ", skupina: " + s.getSkupina());
        }
		int searchIC = sc.nextInt();
		Zamestnanec s = vnitrniDatabaze.get(searchIC);
        if (s != null) {
        	System.out.println("	-Zvolen zamestnanec: " + s.getJmeno() + " " + s.getPrijmeni() + ", rok narozeni: " + s.getRok() + ", IC: " + s.getIC() + ", skupina: " + s.getSkupina());
        } else {
            System.out.println("	-Zamestnanec nenalezen!");
            return;
        }
        
        System.out.println("\n│-----------------------------------------│");
		System.out.println("	Vyberte IC zamestnance k spolupraci: ");
		System.out.println("\n│-----------------------------------------│\n");
					
		int coopIC = sc.nextInt();
		if (searchIC == coopIC) {
			System.out.println("	-Zamestnanec nemuze spolupracovat sam se sebou!");
			return;
		}
		
		Zamestnanec t = vnitrniDatabaze.get(coopIC);
        if (t != null) {
        	System.out.println("	-Zvolen zamestnanec: " + t.getJmeno() + " " + t.getPrijmeni() + ", rok narozeni: " + t.getRok() + ", IC: " + t.getIC() + ", skupina: " + t.getSkupina());
        } else {
            System.out.println("	-Zamestnanec nenalezen!");
            return;
        }
		
        System.out.println("\n│-----------------------------------------│");
		System.out.println("	Zvolte uroven spoluprace: ");
		System.out.println("\n	1 ... DOBRA");
        System.out.println("	2 ... PRUMERNA");
        System.out.println("	3 ... SPATNA");
        System.out.println("	4 ... NAHODNA");
		System.out.println("\n│-----------------------------------------│\n");
		
		int urovenSpol = sc.nextInt();
		String ulozenaUroven = "x";
	    
	    switch (urovenSpol) {
	        case 1:
	            ulozenaUroven = "DOBRA";
	            break;
	        case 2:
	            ulozenaUroven = "PRUMERNA";
	            break;
	        case 3:
	            ulozenaUroven = "SPATNA";
	            break;
	        case 4:
	            String[] moznosti = {"DOBRA", "PRUMERNA", "SPATNA"};
	            ulozenaUroven = moznosti[(int)(Math.random() * 3)];
	            break;
	        default:
	            System.out.println("	-Neplatna volba, nastavuji: PRUMERNA.");
	            ulozenaUroven = "PRUMERNA";
	            break;
	    }
        
	    Spoluprace novaSpoluprace = new Spoluprace(s.getIC(), t.getIC(), ulozenaUroven);
	    seznamSpolupraci.add(novaSpoluprace);
	    
        System.out.println("\n│-----------------------------------------│");
		System.out.println("	Vytvorena spoluprace mezi: ");
		System.out.println("\n		" + s.getJmeno() + " " + s.getPrijmeni() + " (" + s.getIC() + ") ");
		System.out.println("		  a ");
		System.out.println("\n		" + t.getJmeno() + " " + t.getPrijmeni() + " (" + t.getIC() + ") ");
		System.out.println("		  " + ulozenaUroven);
		System.out.println("\n│-----------------------------------------│\n");
        
	}
	
	//	4
	public void infoZamestnanci() {
		if (vnitrniDatabaze.isEmpty()) {
			System.out.println("	-Databaze je prazdna!");
			return;
		}
		
		System.out.println("	Vypis vsech zamestnancu: ");
		for (Zamestnanec s : vnitrniDatabaze.values()) {
			System.out.println("	-Zamestnanec: " + s.getJmeno() + " " + s.getPrijmeni() + ", rok narozeni: " + s.getRok() + ", IC: " + s.getIC() + ", skupina: " + s.getSkupina());
        }
	}
	
	//	5
	public void infoICZamestnanec() {
		if (vnitrniDatabaze.isEmpty()) {
			System.out.println("	-Databaze je prazdna!");
			return;
		}
		
		System.out.println("\n│-----------------------------------------│");
		System.out.println("	Vypiste IC zamestnance: \n");
        System.out.println("│-----------------------------------------│\n");
		
		int IC = sc.nextInt();
		Zamestnanec s = vnitrniDatabaze.get(IC);
        if (s != null) {
        	System.out.println("	-Zamestnanec: " + s.getJmeno() + " " + s.getPrijmeni() + ", rok narozeni: " + s.getRok() + ", IC: " + s.getIC() + ", skupina: " + s.getSkupina());
        } else {
            System.out.println("	-Zamestnanec nenalezen!");
        }
        
        List<Spoluprace> osobniSpoluprace = new ArrayList<>();
        if (seznamSpolupraci != null) {
        	for (Spoluprace spol : seznamSpolupraci) {
        		if (spol.getZamestnanecIC1() == IC || spol.getZamestnanecIC2() == IC) {
                    osobniSpoluprace.add(spol);
                }
            }
        }
        
        if(osobniSpoluprace.isEmpty()) {
        	System.out.println("	   -Zamestnanec nema spoluprace!");
        } else {
            System.out.println("\n	-> Osobni spoluprace (" + IC +"):");
            
            for (Spoluprace spol : osobniSpoluprace) {
                int idKolegy = (spol.getZamestnanecIC1() == IC) ? spol.getZamestnanecIC2() : spol.getZamestnanecIC1();
                Zamestnanec kolega = vnitrniDatabaze.get(idKolegy);
                
                String jmenoKolegy = (kolega != null) ? (kolega.getPrijmeni() + " " + kolega.getJmeno()) : "Neznamy (" + idKolegy + ")";
                System.out.println("		-> s kolegou: " + jmenoKolegy + " ("+ kolega.getIC() +")" + " , spoluprace: " + spol.getUroven());
            }
        } 
	}
	
	
	//	6
	public void infoSkupinaZamestnance() {
		System.out.println("\n│-----------------------------------------│");
		System.out.println("	Zvolte skupinu zamestnancu: ");
		
        System.out.println("\n	1 ... Datovi analytici");
        System.out.println("	2 ... Bezpecnostní specialiste");
        System.out.println("│-----------------------------------------│\n");
        
        int zvolenaSkupina = sc.nextInt();
        List<Zamestnanec> vybraniZamestnanci = new ArrayList<>();
        
        for (Zamestnanec z : vnitrniDatabaze.values()) {
        	if (z.getSkupina() == zvolenaSkupina) {
        		vybraniZamestnanci.add(z);
        	}
        }
        
        if (vybraniZamestnanci.isEmpty()) {
        	System.out.println("	-Nelze zobrazit skupinu, nebot je prazdna nebo neexistuje!");
        	return;
        }
        
        vybraniZamestnanci.sort(Comparator.comparing(Zamestnanec::getPrijmeni));
        
        System.out.println("\n│-----------------------------------------│");
		System.out.println("	Abecedni vypis skupiny " + zvolenaSkupina + ":");
        System.out.println("\n│-----------------------------------------│\n");
        
        for (Zamestnanec z : vybraniZamestnanci) {
        	System.out.println("	- " + z.getPrijmeni() + " " + z.getJmeno() + ", rok narozeni: " + z.getRok() + ", IC: " + z.getIC());
        }
	}
	
	//	7
	public void infoSkupinaPocet() {
		System.out.println("\n│-----------------------------------------│");
		System.out.println("	Zvolte skupinu zamestnancu: ");
		
        System.out.println("\n	1 ... Datoví analytici");
        System.out.println("	2 ... Bezpecnostní specialisté");
        System.out.println("│-----------------------------------------│\n");
        
        int pocetniSkupina = sc.nextInt();
        while (pocetniSkupina != 1 && pocetniSkupina != 2) {
	        System.out.println("	-Neplatna skupina (volte 1 nebo 2):");
	        pocetniSkupina = sc.nextInt();
	    }
        
        int pocet = 0;
        
        for (Zamestnanec z : vnitrniDatabaze.values()) {
        	if (z.getSkupina() == pocetniSkupina) {
        		pocet++;
        	}
        }
        
        System.out.println("\n│-----------------------------------------│");
		System.out.println("	Pocet zamestnancu ve skupine " + pocetniSkupina + ": ");
        System.out.println("\n		"+ pocet);
        System.out.println("│-----------------------------------------│\n");
	}
	
	//	8
	public void statistikyZamestnani() {
		System.out.println("\n│-----------------------------------------│");
		System.out.println("  Celkove statistiky firmy ");
		System.out.println("│-----------------------------------------│\n");

		if (seznamSpolupraci == null || seznamSpolupraci.isEmpty()) {
			System.out.println("	-Zadne statistiky k zobrazeni, neexistuji zadne spoluprace.");
			return;
		}

		int dobra = 0;
		int prumerna = 0;
		int spatna = 0;

		Map<Integer, Integer> pocetVazeb = new HashMap<>();

		for (Spoluprace spol : seznamSpolupraci) {
			
			String uroven = spol.getUroven().toUpperCase();
			if (uroven.contains("DOBR")) {
				dobra++;
			} else if (uroven.contains("PRUMER") || uroven.contains("PRŮMĚR")) {
				prumerna++;
			} else if (uroven.contains("SPATN") || uroven.contains("ŠPATN")) {
				spatna++;
			}

			int ic1 = spol.getZamestnanecIC1();
			int ic2 = spol.getZamestnanecIC2();
			
			pocetVazeb.put(ic1, pocetVazeb.getOrDefault(ic1, 0) + 1);
			pocetVazeb.put(ic2, pocetVazeb.getOrDefault(ic2, 0) + 1);
		}

		String prevazujiciKvalita = "DOBRA";
		int maxKvalita = dobra;

		if (prumerna > maxKvalita) {
			maxKvalita = prumerna;
			prevazujiciKvalita = "PRUMERNA";
		}
		if (spatna > maxKvalita) {
			maxKvalita = spatna;
			prevazujiciKvalita = "SPATNA";
		}

		System.out.println("	Prevazujici kvalita spoluprace:\n");
		System.out.println("	-> " + prevazujiciKvalita + " (Pocet vyskytu: " + maxKvalita + ")");
		System.out.println("	   (Rozlozeni - Dobra: " + dobra + ", Prumerna: " + prumerna + ", Spatna: " + spatna + ")\n");

		int maxVazeb = -1;
		int topIC = -1;

		for (Map.Entry<Integer, Integer> zaznam : pocetVazeb.entrySet()) {
			if (zaznam.getValue() > maxVazeb) {
				maxVazeb = zaznam.getValue();
				topIC = zaznam.getKey();
			}
		}

		Zamestnanec topZamestnanec = vnitrniDatabaze.get(topIC);
		
		System.out.println("	Zamestnanec s nejvice vazbami:\n");
		if (topZamestnanec != null) {
			System.out.println("	-> " + topZamestnanec.getJmeno() + " " + topZamestnanec.getPrijmeni() + " (IC: " + topIC + ")");
			System.out.println("	   Pocet vazeb: " + maxVazeb);
		} else {
			System.out.println("	-> Neznamy/Smazany zamestnanec, IC: " + topIC + " ");
			System.out.println("	   (Pocet vazeb: " + maxVazeb + ")");
		}
	}
	
	//	9
	public void saveZamestnanec() {
		if (vnitrniDatabaze.isEmpty()) {
			System.out.println("	-Databaze je prazdna!");
			return;
		}
		
		System.out.println("\n│-----------------------------------------│");
		System.out.println("	Vyberte IC zamestnance: \n");
        System.out.println("│-----------------------------------------│\n");
        for (Zamestnanec s : vnitrniDatabaze.values()) {
			System.out.println("	-Zamestnanec: " + s.getJmeno() + " " + s.getPrijmeni() + ", rok narozeni: " + s.getRok() + ", IC: " + s.getIC() + ", skupina: " + s.getSkupina());
        }
		
		int saveIC = sc.nextInt();
		Zamestnanec z = vnitrniDatabaze.get(saveIC);
	        
	        if (z != null) {
	            String nazevSouboru = "UlozeniZamestnanci.txt";
	            
	            try (PrintWriter pw = new PrintWriter(new FileWriter(nazevSouboru, true))) {
	                
	                pw.println(z.getIC() + ";" + z.getJmeno() + ";" + z.getPrijmeni() + ";" + z.getRok() + ";" + z.getSkupina());
	                
	                System.out.println("	-Zamestnanec (" + z.getJmeno() + " " + z.getPrijmeni() + " (" + z.getIC() +") byl uspesne pridan do souboru.");
	            } catch (IOException e) {
	                System.out.println("	-Doslo k chybe pri ukladani do souboru: " + e.getMessage());
	            }
	        } else {
	            System.out.println("	-Zamestnanec s IC: " + saveIC + " nenalezen!");
	            return;
	        }
	}
	
	//	10
	public void loadZamestnanec() {
	    String nazevSouboru = "UlozeniZamestnanci.txt";
	    
	    System.out.println("\n│-----------------------------------------│");
	    System.out.println("	Nacitani zamestnancu ze souboru...");
	    System.out.println("\n│-----------------------------------------│\n");

	    Map<Integer, Zamestnanec> docasnaDatabaze = new HashMap<>();

	    try (BufferedReader br = new BufferedReader(new FileReader(nazevSouboru))) {
	        String radek;
	        while ((radek = br.readLine()) != null) {
	            if (radek.trim().isEmpty()) continue;
	            
	            String[] data = radek.split(";");
	            if (data.length == 5) {
	                try {
	                    int ic = Integer.parseInt(data[0]);
	                    String jmeno = data[1];
	                    String prijmeni = data[2];
	                    int rok = Integer.parseInt(data[3]);
	                    int skupina = Integer.parseInt(data[4]);
	                    
	                    Zamestnanec z = new Zamestnanec(ic, jmeno, prijmeni, rok, skupina);
	                    docasnaDatabaze.put(ic, z);
	                    
	                } catch (NumberFormatException e) {
	                }
	            }
	        }
	    } catch (FileNotFoundException e) {
	        System.out.println("	- Soubor '" + nazevSouboru + "' nebyl nalezen.");
	        return;
	    } catch (IOException e) {
	        System.out.println("	- Doslo k chybe pri cteni souboru: " + e.getMessage());
	        return;
	    }

	    if (docasnaDatabaze.isEmpty()) {
	        System.out.println("	- Soubor existuje, ale zadna platna data zamestnancu.");
	        return;
	    }

	    System.out.println("	Dostupni zamestnanci k nacteni ze souboru:\n");
	    for (Zamestnanec z : docasnaDatabaze.values()) {
	        System.out.println("	- " + z.getJmeno() + " " + z.getPrijmeni() + " rok narozeni: " + z.getRok() + ", IC: " + z.getIC());
	    }

	    System.out.println("\n	Zadejte IC zamestnance, ktereho chcete nacist:");
	    int loadIC = sc.nextInt();
	    
	    Zamestnanec vybrany = docasnaDatabaze.get(loadIC);
	    
	    if (vybrany != null) {
	        if (!vnitrniDatabaze.containsKey(loadIC)) {
	            vnitrniDatabaze.put(loadIC, vybrany);
	            System.out.println("\n	- Zamestnanec uspesne nacten do programu: " + vybrany.getJmeno() + " " + vybrany.getPrijmeni() + ", IC:" + vybrany.getIC());
	        } else {
	            System.out.println("	- Zamestnanec s IC (" + loadIC + ") uz v aktivnim programu je!");
	        }
	    } else {
	        System.out.println("	- Zamestnanec s IC: " + loadIC + " v souboru nenalezen!");
	    }
	}
	
	//	11
	public void choiseProfil() {
		if (vnitrniDatabaze.isEmpty()) {
			System.out.println("	-Databaze je prazdna!");
			return;
		}
		
		System.out.println("\n│-----------------------------------------│");
		System.out.println("	Vyberte IC zamestnance: \n");
        System.out.println("│-----------------------------------------│\n");
        for (Zamestnanec s : vnitrniDatabaze.values()) {
			System.out.println("	-Zamestnanec: " + s.getJmeno() + " " + s.getPrijmeni() + ", rok narozeni: " + s.getRok() + ", IC: " + s.getIC() + ", skupina: " + s.getSkupina());
        }
		
		int profilIC = sc.nextInt();
		Zamestnanec s = vnitrniDatabaze.get(profilIC);
        if (s != null) {
        	aktualniProfil = s;
        	System.out.println("	-Aktivni profil zamestnance: " + s.getJmeno() + " " + s.getPrijmeni() + ", rok narozeni: " + s.getRok() + ", IC: " + s.getIC() + ", skupina: " + s.getSkupina());
        } else {
            System.out.println("	-Zamestnanec nenalezen!");
        }
	}
	
	//	12
	public void atributProfil() {
		if (aktualniProfil == null) {
			System.out.println("	-Neni nastaven aktualni zamestnanec!");
			return;
		}
		
		int skupinaProfilu = aktualniProfil.getSkupina();
		
		if (skupinaProfilu == 1) {
			atributDatAn();
		} else if (skupinaProfilu == 2) {
			atributBezSpec();
		} else {
			System.out.println("	-Chybna skupina!");
			return;
		}
	}
	
	public void atributDatAn() {
		System.out.println("\n│-----------------------------------------│");
		System.out.println("	Modul datovych analytiku \n");
        System.out.println("│-----------------------------------------│\n");

        int mujIC = aktualniProfil.getIC();

        Set<Integer> mojiSpolupracovnici = new HashSet<>();
        if (seznamSpolupraci != null) {
            for (Spoluprace spol : seznamSpolupraci) {
                if (spol.getZamestnanecIC1() == mujIC) {
                    mojiSpolupracovnici.add(spol.getZamestnanecIC2());
                } else if (spol.getZamestnanecIC2() == mujIC) {
                    mojiSpolupracovnici.add(spol.getZamestnanecIC1());
                }
            }
        }

        if (mojiSpolupracovnici.isEmpty()) {
            System.out.println("	-Nemate zadne spolupracovniky, nelze hledat spolecne kontakty.");
            return;
        }

        int maxSpolecnych = -1;
        Zamestnanec topKolega = null;

        for (Zamestnanec z : vnitrniDatabaze.values()) {
            if (z.getIC() == mujIC) continue; 

            Set<Integer> jehoSpolupracovnici = new HashSet<>();
            for (Spoluprace spol : seznamSpolupraci) {
                if (spol.getZamestnanecIC1() == z.getIC()) {
                    jehoSpolupracovnici.add(spol.getZamestnanecIC2());
                } else if (spol.getZamestnanecIC2() == z.getIC()) {
                    jehoSpolupracovnici.add(spol.getZamestnanecIC1());
                }
            }

            int spolecniCount = 0;
            for (Integer id : jehoSpolupracovnici) {
                if (mojiSpolupracovnici.contains(id)) {
                    spolecniCount++;
                }
            }

            if (spolecniCount > maxSpolecnych) {
                maxSpolecnych = spolecniCount;
                topKolega = z;
            }
        }

        if (topKolega != null && maxSpolecnych > 0) {
            System.out.println("	Nejvice spolecnych spolupracovniku mate s kolegou:");
            System.out.println("	-> " + topKolega.getJmeno() + " " + topKolega.getPrijmeni() + " (" + topKolega.getIC() + ")");
            System.out.println("	Pocet spolecnych vazeb: " + maxSpolecnych);
        } else {
            System.out.println("	-Zadny jiny zamestnanec s vami nema spolecneho spolupracovnika.");
        }
	}
	
	public void atributBezSpec() {
		System.out.println("\n│-----------------------------------------│");
		System.out.println("	Modul bezpecnostnich specialistu \n");
        System.out.println("│-----------------------------------------│\n");
        
        if (aktualniProfil == null) {
            System.out.println("	-Chyba: Neni vybran profil.");
            return;
        }

        int mujIC = aktualniProfil.getIC();
        int pocetSpolupracovniku = 0;
        double sumaRizika = 0;

        if (seznamSpolupraci != null) {
            for (Spoluprace spol : seznamSpolupraci) {
                if (spol.getZamestnanecIC1() == mujIC || spol.getZamestnanecIC2() == mujIC) {
                    pocetSpolupracovniku++;
                    
                    String uroven = spol.getUroven().toUpperCase();
                    
                    if (uroven.contains("DOBR")) {
                        sumaRizika += 1.0;
                    } else if (uroven.contains("PRUMER") || uroven.contains("PRŮMĚR")) {
                        sumaRizika += 2.0;
                    } else if (uroven.contains("SPATN") || uroven.contains("ŠPATN")) {
                        sumaRizika += 3.0;
                    } else {
                        sumaRizika += 2.0; 
                    }
                }
            }
        }

        if (pocetSpolupracovniku == 0) {
            System.out.println("	-Nemate zadne navazane spoluprace.");
            return;
        }

        double prumernaKvalita = sumaRizika / pocetSpolupracovniku;

        double zakladniRiziko = (prumernaKvalita - 1.0) * 40.0;

        double prirazkaZaPocet = pocetSpolupracovniku * 5.0;

        double celkoveSkore = zakladniRiziko + prirazkaZaPocet;
        if (celkoveSkore > 100.0) celkoveSkore = 100.0;
        if (celkoveSkore < 0.0) celkoveSkore = 0.0;


        System.out.println("	- Pocet spolupracovniku: " + pocetSpolupracovniku);
        System.out.printf("	- Prumerna kvalita vazeb: %.2f \n", prumernaKvalita);
        System.out.printf("	=> Celkove RIZIKOVE SKORE: %.1f %%\n", celkoveSkore);

        System.out.println("\n	Zhodnoceni:");
        if (celkoveSkore < 30) {
            System.out.println("	[NIZKE RIZIKO] ");
        } else if (celkoveSkore < 70) {
            System.out.println("	[STREDNI RIZIKO] ");
        } else {
            System.out.println("	[VYSOKE RIZIKO] ");
        }
	}
	
	public void ulozSQL() {
		System.out.println("\n	-Zalohovani dat do SQL...");
		try {
			Class.forName("org.sqlite.JDBC");
			try (Connection conn = DriverManager.getConnection(URL_SQL)) {
				if (conn != null) {
					Statement stmt = conn.createStatement();
					
					stmt.execute("CREATE TABLE IF NOT EXISTS zamestnanci (" +
								 "ic INTEGER PRIMARY KEY, jmeno TEXT, prijmeni TEXT, rok INTEGER, skupina INTEGER)");
					stmt.execute("CREATE TABLE IF NOT EXISTS spoluprace (" +
								 "ic1 INTEGER, ic2 INTEGER, uroven TEXT)");

					stmt.execute("DELETE FROM zamestnanci");
					stmt.execute("DELETE FROM spoluprace");

					String sqlZam = "INSERT INTO zamestnanci(ic, jmeno, prijmeni, rok, skupina) VALUES(?,?,?,?,?)";
					try (PreparedStatement pstmt = conn.prepareStatement(sqlZam)) {
						for (Zamestnanec z : vnitrniDatabaze.values()) {
							pstmt.setInt(1, z.getIC());
							pstmt.setString(2, z.getJmeno());
							pstmt.setString(3, z.getPrijmeni());
							pstmt.setInt(4, z.getRok());
							pstmt.setInt(5, z.getSkupina());
							pstmt.executeUpdate(); 
						}
					}

					String sqlSpol = "INSERT INTO spoluprace(ic1, ic2, uroven) VALUES(?,?,?)";
					try (PreparedStatement pstmt = conn.prepareStatement(sqlSpol)) {
						for (Spoluprace s : seznamSpolupraci) {
							pstmt.setInt(1, s.getZamestnanecIC1());
							pstmt.setInt(2, s.getZamestnanecIC2());
							pstmt.setString(3, s.getUroven());
							pstmt.executeUpdate();
						}
					}
					System.out.println("\n	-Data byla zalohovana do SQL.");
				}
			}
		}
		catch (ClassNotFoundException e) {
	        System.out.println("	-Knihovna SQLite nebyla nalezena!");
		}  catch (SQLException e) {
	        System.out.println("	-Chyba při ukládání do SQL: " + e.getMessage());
	    }
	}
	
	public void nactiSQL() {
		try (Connection conn = DriverManager.getConnection(URL_SQL)) {
			if (conn != null) {
				String sqlZam = "SELECT ic, jmeno, prijmeni, rok, skupina FROM zamestnanci";
				Statement stmt = conn.createStatement();
				ResultSet rsZam = stmt.executeQuery(sqlZam);
				
				while (rsZam.next()) {
					int ic = rsZam.getInt("ic");
					Zamestnanec z = new Zamestnanec(
						ic, 
						rsZam.getString("jmeno"), 
						rsZam.getString("prijmeni"), 
						rsZam.getInt("rok"), 
						rsZam.getInt("skupina")
					);
					vnitrniDatabaze.put(ic, z);
				}

				String sqlSpol = "SELECT ic1, ic2, uroven FROM spoluprace";
				ResultSet rsSpol = stmt.executeQuery(sqlSpol);
				while (rsSpol.next()) {
					seznamSpolupraci.add(new Spoluprace(
						rsSpol.getInt("ic1"),
						rsSpol.getInt("ic2"),
						rsSpol.getString("uroven")
					));
				}
				System.out.println("\n	-SQL zaloha nactena...");
			}
		} catch (SQLException e) {
			System.out.println("\n	-SQL zaloha nenalezena, start bez databaze.");
		}
	}
	
	public Zamestnanec getIC(int IC) {
		return vnitrniDatabaze.get(IC);
	}
	
}
