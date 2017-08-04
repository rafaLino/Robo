package projeto;

import java.util.ArrayList;
import java.util.List;

public class Mapa 
{
	private int X = 0;
	private int Y = 0;
	private List<Cone> listaCone= new ArrayList<>();
	private List<Parede> listaParede= new ArrayList<>();
	private List<Robo> listaRobo= new ArrayList<>();
	
	
	
	public int getX() {
		return X;
	}

	public int getY() {
		return Y;
	}

	public List<Cone> getListaCone() {
		return listaCone;
	}

	public List<Parede> getListaParede() {
		return listaParede;
	}

	public List<Robo> getListaRobo() {
		return listaRobo;
	}

	public boolean adicionarRobo(Robo robo)
	{
		listaRobo.add(robo);
		tamanhoMapa(robo);
		return true;
	}
	
	public boolean adicionarParede(Parede parede)
	{
		listaParede.add(parede);
		tamanhoMapa(parede);
		return true;
	}
	
	public boolean adicionarCone(Cone cone)
	{
		listaCone.add(cone);
		tamanhoMapa(cone);
		return true;
	}
	
	public void tamanhoMapa(MapaObject mObj)
	{
		if(mObj.getX() > X)
		{
			X = (mObj.getX());
		}
		if(mObj.getY() > Y)
		{
			Y = (mObj.getY());
		}
	}
	
	public void tamanhoMapa(List<MapaObject> listaDeTudo)
	{
		for (MapaObject mapaObject : listaDeTudo) {
			if(mapaObject.getX() > X)
			{
				X = (mapaObject.getX());
			}
			if(mapaObject.getY() > Y)
			{
				Y = (mapaObject.getY());
			}
		}
	}
	
	
	@Override
	public String toString() {

		StringBuilder SB = new StringBuilder();
		
		List<MapaObject> listaDeTudo = new ArrayList<>();
		listaDeTudo.addAll(listaCone);
		listaDeTudo.addAll(listaParede);
		listaDeTudo.addAll(listaRobo);
		tamanhoMapa(listaDeTudo);
		for(int y = Y; y >= 1;y--)	
		{
			
			for(int x = 1; x <= X;x++)
			{
				SB.append("[");
				for (MapaObject mapaObject : listaDeTudo) 
				{
					if(mapaObject.getX() == x && mapaObject.getY() == y)
					{
						SB.append(mapaObject);
					}
				}
				SB.append("]");
			}
			SB.append( System.getProperty("line.separator") );
		}
		
		
		return SB.toString();
	}

}
