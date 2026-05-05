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
		
		vnitrniDatabaze.nactiSQL();
		
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
	            System.out.println("	1  ... pridani noveho zamestnance");
	            System.out.println("	2  ... odebrani zamestnance");
	            System.out.println("	3  ... pridani spoluprace");
	            System.out.println("	4  ... vypsani vsech zamestnancu");
	            System.out.println("	5  ... vypsani zamestnance dle IC");
	            System.out.println("	6  ... vypsani zamestnancu dle prijmeni ve skupinach");
	            System.out.println("	7  ... vypis poctu zamestnacu ve skupinach");
	            System.out.println("	8  ... statistiky");
	            System.out.println("	9  ... ulozeni zamestnance do souboru");
	            System.out.println("	10 ... nacteni zamestnance ze souboru");
	            System.out.println("	11 ... zvoleni profilu zamestnance");
	            System.out.println("	12 ... atribut skupiny zamestnance");
	            System.out.println("	0  ... UKONCENI PROGRAMU");
	            System.out.println("│-----------------------------------------│\n");
	            
	            volba = pouzeCelaCisla(sc);
	            
	            switch (volba) {
	            	case 1:
	            		vnitrniDatabaze.addZamestnanec();
	            		break;
	            		
	            	case 2:
	            		vnitrniDatabaze.removeZamestnanec();
	            		break;
	            		
	            	case 3:
	            		vnitrniDatabaze.addSpoluprace();
	            		break;
	            		
	            	case 4:
	            		vnitrniDatabaze.infoZamestnanci();
	            		break;
	            	
	            	case 5:
	            		vnitrniDatabaze.infoICZamestnanec();
	            		break;
	            	
	            	case 6:
	            		vnitrniDatabaze.infoSkupinaZamestnance();
	            		break;
	            		
	            	case 7:
	            		vnitrniDatabaze.infoSkupinaPocet();
	            		break;
	            		
	            	case 8:
	            		vnitrniDatabaze.statistikyZamestnani();
	            		break;
	            		
	            	case 9:
	            		vnitrniDatabaze.saveZamestnanec();
	            		break;
	            		
	            	case 10:
	            		vnitrniDatabaze.loadZamestnanec();
	            		break;
	            		
	            	case 11:
	            		vnitrniDatabaze.choiseProfil();
	            		break;
	            		
	            	case 12:
	            		vnitrniDatabaze.atributProfil();
	            		break;
	            		
	            	case 0:
	            		run = false;
	            		vnitrniDatabaze.ulozSQL();
	            		System.out.println("\n	-PROGRAM UKONCEN!     BYE");
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
