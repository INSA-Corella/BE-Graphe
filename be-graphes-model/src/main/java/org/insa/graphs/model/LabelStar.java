package org.insa.graphs.model;

public class LabelStar extends Label{
    
    private double coutInferieur;
    
    public LabelStar(Node sommetCourant, Node destination)
    {
        super(sommetCourant);

        this.coutInferieur = Point.distance(sommetCourant.getPoint(), destination.getPoint());
                
    }
    
    @Override
    protected double getTotalCost()
    {
        return this.getCost() + this.coutInferieur;
    }
    
    
}