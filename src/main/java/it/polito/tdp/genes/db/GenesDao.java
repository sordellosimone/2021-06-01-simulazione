package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Archi;
import it.polito.tdp.genes.model.Genes;


public class GenesDao {
	
	public void getAllGenes(Map<String,Genes> idMap){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				idMap.put(genes.getGeneId(), genes);
			}
			res.close();
			st.close();
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public LinkedList<Archi>  getArchi (Map<String,Genes> idMap){
		String sql = "SELECT Distinct i1.GeneID1 AS id1, i1.GeneID2 AS id2, i1.Expression_Corr AS peso "
				   + "	FROM  interactions i1 "
				   + "	WHERE  i1.GeneID1>i1.GeneID2 ";
		Connection conn = DBConnect.getConnection();
		LinkedList<Archi> lista= new LinkedList<Archi>();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Archi arco = new Archi(idMap.get(res.getString("id1")), 
						idMap.get(res.getString("id2")), 
						res.getDouble("peso"));
				
				
					lista.add(arco);
				
				
				for(Archi a: lista) {
					if(idMap.containsKey(a.getG1().getGeneId()) && idMap.containsKey(a.getG2().getGeneId())) {
						lista.remove(a);
					}
					
				
				}
				for(Archi a: lista) {
					if(a.getG1().getChromosome()==a.getG2().getChromosome()) {
						
						a.setPeso(a.getPeso()*2);
					}
					
				
				}
				
			}
			res.close();
			st.close();
			conn.close();
			return lista;


			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

	
}
