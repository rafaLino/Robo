package projeto;

import java.util.ArrayList;
import java.util.List;

public class Robo extends MapaObject
{
	private String nome;
	private Enum<Direcao> direcao = Direcao.Norte;
	private List<Cone> inventario = new ArrayList<>();
	private Mapa radar;
	private Cone coneAlvo;
	private List<Coordenadas> path = new ArrayList<>();
	
	public Robo(String nome, Enum<Direcao> direcao, int x, int y)
	{
		this.nome = nome;
		this.direcao = direcao;
		setX(x);
		setY(y);
	}	
	
	public Mapa getRadar() 
	{
		return radar;
	}

	public void setRadar(Mapa radar) {
		this.radar = radar;
	}



	public void mover()
	{
		if(LookAhead()==null)
		{
			path.add(new Coordenadas(getX(), getY()));
			setX(Xahead());
			setY(Yahead());
		}
		else
		{
			virar();
		}
	}
	
	public void virar()
	{
		if(direcao == Direcao.Norte)
		{
			direcao = Direcao.Oeste;
		}
		else if(direcao == Direcao.Sul)
		{
			direcao = Direcao.Leste;
		}
		else if(direcao == Direcao.Leste)
		{
			direcao = Direcao.Norte;
		}
		else if(direcao == Direcao.Oeste)
		{
			direcao = Direcao.Sul;
		}	
	}
	
	public boolean pegar(Cone cone)
	{
		inventario.add(cone);
		radar.getListaCone().remove(cone);
		return true;
	}
	
	public boolean soltar()
	{
		if(hasCone())
		{
			Cone cone = inventario.get(0);
			cone.setX(getX());
			cone.setY(getY());
			radar.adicionarCone(cone);
			inventario.remove(cone);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private int Xahead()
	{
		if(direcao == Direcao.Norte)
		{
			return getX();
		}
		else if(direcao == Direcao.Sul)
		{
			return getX();
		}
		else if(direcao == Direcao.Leste)
		{
			return (getX()+1);
		}
		else
		{
			return (getX()-1);
		}
	}
	
	private int Yahead()
	{
		if(direcao == Direcao.Norte)
		{
			return (getY()+1);
		}
		else if(direcao == Direcao.Sul)
		{
			return (getY()-1);
		}
		else
		{
			return getY();
		}
	}
	
	
	public Parede LookAhead()
	{
		Parede YesWeCan = null;
		
		for (Parede parede : radar.getListaParede()) 
		{
			if(parede.getX() == getX())
			{
				if(parede.getY() == getY())
				{
					if(parede.direcao == direcao)	
					{
						YesWeCan = parede;
					}
				}
			}
		}
		
		int xprox = Xahead();
		int yprox = Yahead();
		
		if(xprox==0)
		{
			YesWeCan = new Parede(Direcao.Leste,0,getY());
		}
		else if(yprox==0)
		{
			YesWeCan = new Parede(Direcao.Norte,getX(),0);
		}
		for (Parede parede : radar.getListaParede()) 
		{
			if(parede.getX() == xprox)
			{
				if(parede.getY() == yprox)
				{
					if(direcao == Direcao.Leste && parede.direcao == Direcao.Oeste)
					{
						YesWeCan = parede;
					}
					if(direcao == Direcao.Norte && parede.direcao == Direcao.Sul)
					{
						YesWeCan = parede;
					}
					if(direcao == Direcao.Sul && parede.direcao == Direcao.Norte)
					{
						YesWeCan = parede;
					}
					if(direcao == Direcao.Oeste && parede.direcao == Direcao.Leste)
					{
						YesWeCan = parede;
					}
					
				}
			}
		}
		
		
		return YesWeCan;
	}
	
	public Cone LookDown()
	{
		for (Cone cone : radar.getListaCone()) 
		{
			if(cone.getX() == getX())
			{
				if(cone.getY() == getY())
				{
					return cone;
				}
			}
		}
		
		return null;
	}
	

	public boolean hasCone()
	{
		if(inventario.size() > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public String toString() 
	{
		if(direcao == Direcao.Norte)
		{
			return "/\\";
		}
		else if(direcao == Direcao.Sul)
		{
			return "\\/";
		}
		else if(direcao == Direcao.Leste)
		{
			return ">";
		}
		else
		{
			return "<";
		}
	}
	
	public void ligarIA() throws InterruptedException
	{
		decidirAlvo();
		while(radar.getListaCone().size() > 0)
		{
			huntTarget();
		
			
			System.out.println(radar);
			System.out.println(direcao);
			Thread.sleep(1000);
		}
	}
	
	private void decidirAlvo()
	{
		if(radar.getListaCone().size() > 0)
		{
			int menorDiferenca = radar.getX() + radar.getY();
			
			for (Cone cone : radar.getListaCone())
			{
				int diferencaX = Math.abs(cone.getX() - getX());
				int diferencaY = Math.abs(cone.getY() - getY());
				int total = diferencaX + diferencaY;
				
				if(total < menorDiferenca)
				{
					menorDiferenca = total;
					coneAlvo = cone;
				}
				
			}
		}
	}
	
	//Começo
	private void huntTarget()
	{
		if(coneAlvo.getY() != getY())
		{
			if(getY() < coneAlvo.getY())
			{
				while(direcao != Direcao.Norte)
				{
					virar();
				}
				if(LookAhead()==null)
				{
					if(!caminhorepetido())
					{
						mover();
					}
					else
					{
						desviarParede();
					}
				}
				else
				{
					desviarParede();
				}
			}
			else
			{
				while(direcao != Direcao.Sul)
				{
					virar();
				}
				if(LookAhead()==null)
				{
					if(!caminhorepetido())
					{
						mover();
					}
					else
					{
						desviarParede();
					}
				}
				else
				{
					desviarParede();
				}
				
			}
		}
		else if(coneAlvo.getX() != getX())
		{
			if(getX() < coneAlvo.getX())
			{
				while(direcao != Direcao.Leste)
				{
					virar();
				}
				if(LookAhead()==null)
				{
					if(!caminhorepetido())
					{
						mover();
					}
					else
					{
						desviarParede();
					}
				}
				else
				{
					desviarParede();
				}
			
			}
			else
			{
				while(direcao != Direcao.Oeste)
				{
					virar();
				}
				if(LookAhead()==null)
				{
					if(!caminhorepetido())
					{
						mover();
					}
					else
					{
						desviarParede();
					}
				}
				else
				{
					desviarParede();
				}
			}
		}
		else
		{
			pegar(LookDown());
		}
	}
	//Fim
	
	public boolean desviarParede(){
			
		while(LookAhead() != null || caminhorepetido())
		{
			virar();
		}
		
			
		mover();
				

		return true;
	}
	
	public boolean caminhorepetido(){
		boolean toGo = false;
		for (Coordenadas p : path) 
			{
			if(p.getX()==Xahead() && p.getY()==Yahead()){
				toGo=true;
			}
		}
		return toGo;
	}
}
