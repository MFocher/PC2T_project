package src;
import java.util.Scanner;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class Databaze {
	
	private Scanner sc;
	private Map<Integer, Zamestnanec> vnitrniDatabaze;
	//public List <Spoluprace> seznamSpolupraci = new ArrayList<>();	//DO5E3IT pro ADDspolupraci
	public Databaze() {
		this.sc = new Scanner(System.in);
		this.vnitrniDatabaze = new HashMap<>();
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
		System.out.println("\n│-----------------------------------------│");
		System.out.println("  Zadejte JMENO, PRIJMENI a ROK NAROZENI:");
        System.out.println("\n│-----------------------------------------│\n");
		
        String jmeno = sc.next();
        String prijmeni = sc.next();
        int rok = sc.nextInt();
        
        Random r = new Random();
        int maxIC = 999999, minIC = 100000;
        int IC = r.nextInt((maxIC - minIC + 1) + minIC);
        
        Zamestnanec novy = new Zamestnanec(skupina, IC, jmeno, prijmeni, rok);
        
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
			System.out.println("	-Odebran zamestnanec: " + remove.getJmeno() + " " + remove.getPrijmeni() + ", rok narozeni: " + remove.getRok() + ", IC: " + remove.getIC() + ", skupina: " + remove.getSkupina());			
			
			/*try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				System.out.println("	-Uspani nezdareno");
			}*/
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
		System.out.println("		Vyberte IC zamestnance: ");
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
        }
		
        //UROVEN SPOLOPRACE VYRESIT
        
        System.out.println("\n│-----------------------------------------│");
		System.out.println("	Vytvorena spoluprace mezi: ");
		System.out.println("\n	" + s.getJmeno() + " " + s.getPrijmeni() + " (" + s.getIC() + ") ");
		System.out.println("		a ");
		System.out.println("\n	" + t.getJmeno() + " " + t.getPrijmeni() + " (" + t.getIC() + ") ");
		System.out.println("\n│-----------------------------------------│\n");
        
	}
	
	//	4
	public void infoZamestnanci() {
		System.out.println("	Vypis vsech zamestnancu: ");
		for (Zamestnanec s : vnitrniDatabaze.values()) {
			System.out.println("	-Zamestnanec: " + s.getJmeno() + " " + s.getPrijmeni() + ", rok narozeni: " + s.getRok() + ", IC: " + s.getIC() + ", skupina: " + s.getSkupina());
        }
	}
	
	//	5
	public void infoICZamestnanec() {
		System.out.println("\n│-----------------------------------------│");
		System.out.println("	Vypiste IC zamestnance: \n");
        System.out.println("\n│-----------------------------------------│\n");
		
		int IC = sc.nextInt();
		Zamestnanec s = vnitrniDatabaze.get(IC);
        if (s != null) {
        	System.out.println("	-Zamestnanec: " + s.getJmeno() + " " + s.getPrijmeni() + ", rok narozeni: " + s.getRok() + ", IC: " + s.getIC() + ", skupina: " + s.getSkupina());
        } else {
            System.out.println("	-Zamestnanec nenalezen!");
        }
	}
	
	//	6
	public void infoSkupinaZamestnance() {
		System.out.println("\n│-----------------------------------------│");
		System.out.println("	Zvolte skupinu zamestnancu: ");
		
        System.out.println("\n	1 ... Datoví analytici");
        System.out.println("	2 ... Bezpecnostní specialisté");
        System.out.println("│-----------------------------------------│\n");
        
        int zvolenaSkupina = sc.nextInt();
        List<Zamestnanec> vybraniZamestnanci = new ArrayList<>();
        
        for (Zamestnanec z : vnitrniDatabaze.values()) {
        	if (z.getSkupina() == zvolenaSkupina) {
        		vybraniZamestnanci.add(z);
        	}
        }
        
        if (vybraniZamestnanci.isEmpty()) {
        	System.out.println("	-Nelze zobrazit skupinu, nebot je prazdna!");
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
        
        int pocet = 0;
        
        for (Zamestnanec z : vnitrniDatabaze.values()) {
        	if (z.getSkupina() == pocetniSkupina) {
        		pocet++;
        	}
        }
        
        
        System.out.println("\n│-----------------------------------------│");
		System.out.println("	Pocet zamestnancu ve skupine " + pocetniSkupina + ": ");
        System.out.println("\n	"+ pocet);
        System.out.println("│-----------------------------------------│\n");
	}
	
	public Zamestnanec getIC(int IC) {
		return vnitrniDatabaze.get(IC);
	}
	
}
