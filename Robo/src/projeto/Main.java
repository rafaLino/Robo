package projeto;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		Robo robo = new Robo("Charlie", Direcao.Norte, 4, 1);
		Cone cone = new Cone(1,1);
		Mapa mapa = new Mapa();
		
		mapa.adicionarRobo(robo);
		mapa.adicionarCone(cone);
		//mapa.adicionarParede(parede);
		
		robo.setRadar(mapa);
		
		robo.ligarIA();
		System.out.println(robo.hasCone());
		
		/*
		System.out.println(mapa);
		
		System.out.println("===========");
		
		robo.virar();
		
		System.out.println(mapa);
		
		System.out.println("===========");
		
		robo.mover();
		robo.mover();
		robo.mover();
		robo.mover();
		
		System.out.println(mapa);
		
		System.out.println("===========");
		
		Cone coneld = robo.LookDown();
		robo.pegar(coneld);
		
		System.out.println(mapa);
		System.out.println(robo.hasCone());
		*/
		
		
		
		

	}

}
