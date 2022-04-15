package org.insa.graphs.model;

public class Label implements Comparable<Label>{
	
	private Node sommetCourant;
	private boolean marque;
	private float cout;
	private Arc pere;
	
	public Label(Node sommetCourant) {
		
		this.sommetCourant = sommetCourant;
		this.marque = false;
		this.cout = Float.MAX_VALUE;
		this.pere = null;
	}

	
	public Node getSommetCourant() {
		return this.sommetCourant;
	}

	public boolean isMarque() {
		return this.marque;
	}

	public float getCost() {
		return this.cout;
	}

	public void setSommetCourant(Node sommetCourant) {
		this.sommetCourant = sommetCourant;
	}


	public void setMarque(boolean marque) {
		this.marque = marque;
	}


	public void setCout(float cout) {
		this.cout = cout;
	}


	public void setPere(Arc pere) {
		this.pere = pere;
	}


	public Arc getPere() {
		return this.pere;
	}


	@Override
	public int compareTo(Label l) {
		if(this.cout > l.cout)
			return 1;
		else if (this.cout == l.cout)
			return 0;
		else
			return -1;
	}


}
