package it.polito.tdp.genes.model;

	import java.util.HashMap;
	import java.util.LinkedList;
	import java.util.List;
	import java.util.Map;
	import java.util.Set;

	import org.jgrapht.Graphs;
	import org.jgrapht.graph.DefaultEdge;
	import org.jgrapht.graph.DefaultWeightedEdge;
	import org.jgrapht.graph.DirectedWeightedMultigraph;
	import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.WeightedMultigraph;

import it.polito.tdp.genes.db.GenesDao;

	




	public class Model {
		
		
		WeightedMultigraph<Genes,DefaultWeightedEdge> grafo;
		Map<String,Genes> idMap;
		GenesDao dao;
		

		public Model() {
			dao= new GenesDao();
			idMap= new HashMap<String,Genes>();
			dao.getAllGenes(idMap);
			
		}
		
		public void CreaGrafo( ) {
			this.grafo= new WeightedMultigraph<Genes,DefaultWeightedEdge>(DefaultWeightedEdge.class);
			Map<String,Genes> vertici=new HashMap<String,Genes>();
			for(Genes g: idMap.values()) {
				if(g.getEssential().equals("Essential")) {
					vertici.put(g.getGeneId(),g);
				}
			  }
			Graphs.addAllVertices(grafo, vertici.values());
			
			
			// aggiungere gli archi
	            for(Archi a: dao.getArchi(vertici) ) {
	            	
	            	if(this.grafo.getEdge(a.getG1(), a.getG2()) == null) {
	    				Graphs.addEdgeWithVertices(grafo, a.getG1(), a.getG2(), a.getPeso());
	    			}
					
				}
	            System.out.println("# Vertici: " + this.grafo.vertexSet().size());
	    		System.out.println("# Archi: " + this.grafo.edgeSet().size());
			}
			
			
			public List<Archi> lista (){
				List<Archi> lista= new LinkedList<Archi>();
				for(DefaultWeightedEdge e : this.grafo.edgeSet()) {
					lista.add(new Archi(this.grafo.getEdgeSource(e), 
							this.grafo.getEdgeTarget(e),this.grafo.getEdgeWeight(e)));
					
			}
				return lista;
			}
	
	
}
