package projeto;

import java.util.Set;

public class Parede extends MapaObject
{

	Enum<Direcao> direcao = Direcao.Norte;
	
	public Parede(Enum<Direcao> direcao, int x, int y)
	{
		this.direcao = direcao;
		setX(x);
		setY(y);
	}
	
	public String toString() 
	{
		if(direcao == Direcao.Norte)
		{
			return "^";
		}
		else if(direcao == Direcao.Sul)
		{
			return "_";
		}
		else if(direcao == Direcao.Leste)
		{
			return "|-";
		}
		else
		{
			return "-|";
		}
	}
}
