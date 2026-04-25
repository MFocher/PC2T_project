package src;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	
	public static int pouzeCelaCisla(Scanner sc) {
        int cislo = 0;
        try {
            cislo = sc.nextInt();
        } 
        catch (InputMismatchException e) {
            System.out.println("Nastala vyjimka typu " + e.toString());
            System.out.println("zadejte prosim cele cislo ");
            sc.nextLine();
            cislo = pouzeCelaCisla(sc);
        }
        return cislo;
	}
	
	public static void main(String[] args) {
	
		Scanner sc = new Scanner(System.in);
		Databaze vnitrniDatabaze = new Databaze();
		
		boolean run = true;
		int volba;
		String jmeno;
		String prijmeni;
		int rok;
		int IC;
		String skupina;
		
		while (run) {
			try {
				System.out.println("\n│-----------------------------------------│");
				System.out.println("	Vyberte pozadovanou cinnost:\n");
	            System.out.println("	1 ... pridani noveho zamestnance");
	            System.out.println("	X2 ... odebrani zamestnance");
	            System.out.println("	X3 ... pridani spoluprace");
	            System.out.println("	4 ... vypsani vsech zamestnancu");
	            System.out.println("	5 ... vypsani zamestnance dle IC");
	            System.out.println("	X6 ... vypsani zamestnancu dle prijmeni ve skupinach");
	            System.out.println("	X7 ... vypis poctu zamestnacu ve skupinach");
	            System.out.println("	X8 ... ulozeni zamestnance do souboru");
	            System.out.println("	X9 ... nacteni zamestnance ze souboru");
	            System.out.println("	Xpri ukonceni ... ulozeni vsech dat do SQL souboru");
	            System.out.println("	Xpri startu ... nacteni vsech dat z SQL souboru");
	            System.out.println("	0 ... UKONCENI PROGRAMU");
	            System.out.println("│-----------------------------------------│\n");
	            
	            volba = pouzeCelaCisla(sc);
	            
	            switch (volba) {
	            	case 1:
	            		vnitrniDatabaze.addZamestnanec();
	            		break;
	            		
	            	case 2:
	            		vnitrniDatabaze.removeZamestnanec();
	            		break;
	            	
	            	case 4:
	            		vnitrniDatabaze.infoZamestnanci();
	            		break;
	            	
	            	case 5:
	            		System.out.println("\n│-----------------------------------------│");
	            		System.out.println("	Vypiste IC zamestnance: \n");
	                    System.out.println("\n│-----------------------------------------│\n");
	            		
	            		IC = sc.nextInt();
	            		Zamestnanec s = vnitrniDatabaze.getIC(IC);
                        if (s != null) {
                        	System.out.println("	-Zamestnanec: " + s.getJmeno() + " " + s.getPrijmeni() + ", rok narozeni: " + s.getRok() + ", IC: " + s.getIC() + ", skupina: " + s.getSkupina());
                        } else {
                            System.out.println("	-Zamestnanec nenalezen!");
                        }
	            		break;
	            		
	            	case 0:
	            		run = false;
	            		System.out.println("	-PROGRAM UKONCEN!");
	            		break;
	            	
	            	default:
	            		System.out.println("	-Vlozena neplatna volba!");
	            		break;
	            }
	            
			}
			
			catch (NullPointerException e) {
                System.out.println("Chyba: " + e.getMessage());
			}
			
		}
		sc.close();
	}

}
