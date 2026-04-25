package src;
import java.util.Scanner;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;

public class Databaze {
	
	private Scanner sc;
	private Map<Integer, Zamestnanec> vnitrniDatabaze;
	
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
			return;
		} else {
			System.out.println("	-Zamestnanec s IC: " + removeIC + " nenalezen!");
		}
	}
	
	public void infoZamestnanci() {
		System.out.println("	Vypis vsech zamestnancu: ");
		for (Zamestnanec s : vnitrniDatabaze.values()) {
			System.out.println("	-Zamestnanec: " + s.getJmeno() + " " + s.getPrijmeni() + ", rok narozeni: " + s.getRok() + ", IC: " + s.getIC() + ", skupina: " + s.getSkupina());
        }
	}
	
	public Zamestnanec getIC(int IC) {
		return vnitrniDatabaze.get(IC);
	}
	
}
