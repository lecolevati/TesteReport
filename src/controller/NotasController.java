package controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import persistence.NotasDao;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class NotasController {

	public void ReportNotas(String nomeMateria) throws URISyntaxException {
		NotasDao nDao;
		try {
			String url = this.getClass().getClassLoader()
					.getResource("reports/report4.jasper").getFile();
			System.out.println(url.replaceFirst("/", ""));
			nDao = new NotasDao();
			ResultSet rs = nDao.tabelaNotas(nomeMateria);
			JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
			JasperPrint impressao = JasperFillManager.fillReport(url,
					new HashMap<String, Object>(), jrRS);
			String saida = "C:\\Users\\lecol_000\\Desktop\\teste.pdf";
			JasperExportManager.exportReportToPdfFile(impressao, saida);
			Desktop desktop = Desktop.getDesktop();
			desktop.open(new File(saida));

		} catch (SQLException | JRException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

}
