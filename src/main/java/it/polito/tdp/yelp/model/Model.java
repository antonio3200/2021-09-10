package it.polito.tdp.yelp.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	
	YelpDao dao;
	SimpleWeightedGraph<Business,DefaultWeightedEdge> grafo;
	List<Business> vertici;
	
	public Model() {
		dao= new YelpDao();
	}
	
	public List<String> getCitta() {
		List<String> risultato= this.dao.getCitta();
		Collections.sort(risultato);
		return risultato;
	}
	
	public void creaGrafo(String citta) {
		this.grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.vertici=dao.getBusinessPerCitta(citta);
		//Aggiungo vertici
		Graphs.addAllVertices(this.grafo,this.vertici);
		//Aggiungo gli archi
		for(Business b1: this.vertici) {
			for(Business b2 : this.vertici) {
				if(!b1.equals(b2)) {
				double peso = LatLngTool.distance(b1.getPosizione(), b2.getPosizione(), LengthUnit.KILOMETER);
					Graphs.addEdge(this.grafo, b1, b2, peso);
				}
			}
		}
		System.out.format("Grafo creato con %d vertici e %d archi",this.grafo.vertexSet().size(),this.grafo.edgeSet().size());
	}
	
	public int getVertex() {
		return this.grafo.vertexSet().size();
	}
	
	public int getEdge() {
		return this.grafo.edgeSet().size();
	}

	public List<Business> getVertici() {
		return  this.vertici;
	}
	
	public List<Business_Distanza> getLocaliDistanza(Business selezionato){
		double max=0.0;
		List<Business_Distanza> result= new LinkedList<Business_Distanza>();
		for(DefaultWeightedEdge e : this.grafo.edgesOf(selezionato)) {
			if(this.grafo.getEdgeWeight(e)>max) {
				max=this.grafo.getEdgeWeight(e);
			}
		}
		
		for(DefaultWeightedEdge e : this.grafo.edgesOf(selezionato)) {
			if(max==this.grafo.getEdgeWeight(e)) {
				Business b= Graphs.getOppositeVertex(this.grafo, e, selezionato);
				Business_Distanza bd = new Business_Distanza(b.getBusinessName(),max); 
				result.add(bd);
			}
	}
		return result; 
	}

	public Business getBusinessNome(String bus) {
		for(Business b : this.vertici) {
			if(b.getBusinessName().compareTo(bus)==0)
				return b;
		}
		return null;
	}
	
}
