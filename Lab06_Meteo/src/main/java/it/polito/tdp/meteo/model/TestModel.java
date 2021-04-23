package it.polito.tdp.meteo.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model m = new Model();
		
		System.out.println(m.getUmiditaMedia(8));
		
//		for (String name: m.getUmiditaMedia(12).keySet()) {
//		    String key = name.toString();
//	    	Double value = m.getUmiditaMedia(12).get(name);
//	    	System.out.println(key + " " + value);
//		}
		
//		for(int i=1; i<13; i++)
//			System.out.println(m.trovaSequenza(i)+" "+i+"\n");
		

	}

}
