package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.leandrocolevati.bancodedados.GenericDao;
import br.com.leandrocolevati.bancodedados.Sgbd;

public class NotasDao {

	Connection c;
	
	public NotasDao() throws SQLException{
		GenericDao gDao = new GenericDao("localhost", "sa", "1234", "aulajoin10", true, Sgbd.SQLSERVER);
		c = gDao.getConnection();
	}
	
	public ResultSet tabelaNotas(String nomeMateria) throws SQLException{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("SUBSTRING(al.ra,1,9)+'-'+SUBSTRING(al.ra,10,1) AS ra, ");
		sql.append("al.nome, CAST(SUM(av.peso * nt.nota) AS DECIMAL(7,2)) AS nota_final, "); 
		sql.append("CASE	WHEN CAST(SUM(av.peso * nt.nota) AS DECIMAL(7,2)) >= 6.0  ");
		sql.append("		THEN 'APROVADO' ELSE 'REPROVADO' END AS conceito, ");
		sql.append("CASE	WHEN CAST(SUM(av.peso * nt.nota) AS DECIMAL(7,2)) >= 6.0 "); 
		sql.append("		THEN 0 ELSE CAST(6.0 - SUM(av.peso * nt.nota) AS DECIMAL(7,2)) "); 
		sql.append("END AS faltante, ");
		sql.append("CASE	WHEN CAST(SUM(av.peso * nt.nota) AS DECIMAL(7,2)) >= 6.0 "); 
		sql.append("		THEN 0 ELSE ");
		sql.append("			CASE WHEN CAST(SUM (av.peso * nt.nota) AS DECIMAL(7,2)) <= 3.0 ");
		sql.append("			THEN 0 ");
		sql.append("			ELSE CAST(12.0 - SUM(av.peso * nt.nota) AS DECIMAL(7,2)) "); 
		sql.append("			END  ");
		sql.append("END AS min_exame ");
		sql.append("FROM alunos al  ");
		sql.append("INNER JOIN notas nt ");
		sql.append("	ON al.ra = nt.ra_aluno ");
		sql.append("INNER JOIN avaliacoes av ");
		sql.append("ON av.id = nt.id_avaliacao ");
		sql.append("INNER JOIN materias mat ");
		sql.append("ON mat.id = nt.id_materia ");
		sql.append("WHERE mat.nome LIKE ? ");
		sql.append("GROUP BY ra, mat.nome,al.nome ");
		sql.append("ORDER BY al.nome ");
		PreparedStatement ps = c.prepareStatement(sql.toString());
		ps.setString(1, nomeMateria);
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	
}
