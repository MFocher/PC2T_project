package src;

public class Zamestnanec {
	public Zamestnanec(int skupina, int IC, String jmeno, String prijmeni, int rok) {
		this.skupina = skupina;
		this.IC = IC;
		this.jmeno = jmeno;
		this.prijmeni = prijmeni;
		this.rok = rok;
	}
	
	public int getSkupina() {
		return skupina;
	}
	
	public int getIC() {
		return IC;
	}
	
	public String getJmeno() {
		return jmeno;
	}
	
	public String getPrijmeni() {
		return prijmeni;
	}
	
	public int getRok() {
		return rok;
	}
	
	private int skupina;
	private int IC;
	private String jmeno;
	private String prijmeni;
	private int rok;
}
