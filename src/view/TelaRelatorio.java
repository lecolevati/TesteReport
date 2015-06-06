package view;

import java.net.URISyntaxException;

import controller.NotasController;

public class TelaRelatorio {

	public static void main(String[] args) {
		String nomeMateria = "Banco de Dados";
		NotasController nc = new NotasController();
		try {
			nc.ReportNotas(nomeMateria);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
