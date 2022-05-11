package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.*;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.*;

import java.util.ArrayList;
import java.util.Collections;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        ArrayList<Label> labels = new ArrayList<Label>(); //Création label, meme id que la list de node
        BinaryHeap<Label> tas = new BinaryHeap<Label>(); //Création du tas de l'algo
        Node nodeActuel = null;
        Label labelActuel = null;
        

        for(Node n : data.getGraph().getNodes())
        {
        	labels.add(new Label(n));        	//Association label/node 
        }
        
        tas.insert(labels.get(data.getOrigin().getId())); //Insert origin dans le tas
        
        labels.get(data.getOrigin().getId()).setCout(0); //mise a 0 pour le cout de l'origine
        this.notifyOriginProcessed(data.getOrigin());
        
        //Tant que la destination n'est pas marquée && que le tas n'est pas vide
        while(!labels.get(data.getDestination().getId()).isMarque() && !tas.isEmpty())
        {
        	labelActuel = tas.deleteMin();
        	nodeActuel = labelActuel.getSommetCourant(); //Recup mini du tas
        	labelActuel.setMarque(true); //Passage du noeud a true
        	this.notifyNodeMarked(nodeActuel);
        	for(Arc a : nodeActuel.getSuccessors())
        	{
        		if(data.isAllowed(a))
        		{
            		Node nodeSuivant = a.getDestination();
            		Label labelSuivant = labels.get(nodeSuivant.getId());
            		
            		if(!labelSuivant.isMarque())         			//Si le noeud suivant n'est pas marqué alors 
            		{
            			if(labelSuivant.getCost() > labelActuel.getCost()+(float)data.getCost(a))
            			{
            				// si le cout a été modifié, c'est qu'il se trouve déjà dans le tas
            				// s'il est dans le tas, on le remplace (remove et insert)
            				if(labelSuivant.getCost() < Float.MAX_VALUE)
            					tas.remove(labelSuivant);
            				else
            					this.notifyNodeReached(nodeSuivant);
            				//On voit si le chemin en passant par le noeud actuel est plus court que le cout du chemin actuel
                			labelSuivant.setCout(Math.min(labelSuivant.getCost(), labelActuel.getCost()+(float)data.getCost(a)));
                			tas.insert(labelSuivant);
            				labelSuivant.setPere(a);;        				
            			}
            		}
        		}		
        	}
        }
        
        if(tas.isEmpty())
        {
        	System.out.println("Le chemin n'a pas été trouvé !");
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else
        {
        	System.out.println("Chemin trouvé !");
        	this.notifyNodeReached(nodeActuel);
	        // on remonte les pères
	        nodeActuel = data.getDestination();
	        // Create the path from the array of predecessors...
	        ArrayList<Arc> arcs = new ArrayList<>();
	        while (nodeActuel.getId() != data.getOrigin().getId())
	        {
	        	// on recupere l'arc du pere 
	        	Arc pere = labels.get(nodeActuel.getId()).getPere();
	        	arcs.add(pere);
	            nodeActuel = pere.getOrigin();
	        }
	        
	        // Reverse the path...
	        Collections.reverse(arcs);
	
	        // Create the final solution.
	        solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(data.getGraph(), arcs));

        }
        
        
        return solution;
    }
    

    protected LabelStar newLabel(Node n, ShortestPathData data)
    {
    	return new LabelStar(n, data.getDestination());
    }

}
