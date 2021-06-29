package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TestModel {
	

		public static void main(String[] args) {
			Model m= new Model();
			
			m.CreaGrafo();
		  List<Archi> lista = new ArrayList<>( m.lista());
		  for(Archi c: lista) {
			  System.out.println(c.toString());
		  }

		
	       
	}

}
