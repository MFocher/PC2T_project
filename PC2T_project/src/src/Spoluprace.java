package src;

public class Spoluprace {
	int zamestnanecIC1;
	int zamestnanecIC2;
	private String uroven;
	
	public Spoluprace (int zamestnanecIC1, int zamestnanecIC2, String uroven) {
		this.zamestnanecIC1 = zamestnanecIC1;
		this.zamestnanecIC2 = zamestnanecIC2;
		this.uroven = uroven;
	}
	
	public int getZamestnanecIC1() {
	return zamestnanecIC1;	
	}
	
	public int getZamestnanecIC2() {
	return zamestnanecIC2;	
	}
	
	public String getUroven() {
	return uroven;	
	}
}
