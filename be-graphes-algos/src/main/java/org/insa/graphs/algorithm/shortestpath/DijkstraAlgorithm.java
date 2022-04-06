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
        BinaryHeap<Node> tas = new BinaryHeap<Node>(); //Création du tas de l'algo
        Node nodeActuel = null;
        Label labelActuel = null;

        for(Node n : data.getGraph().getNodes())
        {
        	labels.add(new Label(n));        	//Association label/node 
        }
        
        tas.insert(data.getOrigin()); //Insert origin dans le tas
        
        labels.get(data.getOrigin().getId()).setCout(0); //mise a 0 pour le cout de l'origine
        
        //Tant que la destination n'est pas marquée
        while(!labels.get(data.getDestination().getId()).isMarque())
        {
        	nodeActuel = tas.findMin(); //Recup mini du tas
        	labelActuel = labels.get(nodeActuel.getId());
        	labelActuel.setMarque(true); //Passage du noeud a true
        	for(Arc a : nodeActuel.getSuccessors())
        	{
        		Node nodeSuivant = a.getDestination();
        		Label labelSuivant = labels.get(nodeSuivant.getId());
        		if(!labelSuivant.isMarque())         			//Si le noeud suivant n'est pas marqué alors 
        		{
        			float coutPrecedent = labelSuivant.getCost(); //On enregistre le cout avant, pour voir si il a été changé après
        			labelSuivant.setCout(Math.min(labelSuivant.getCost(), labelActuel.getCost()+a.getLength())); //On voit si le chemin en passant par le noeud actuel est plus court que le cout du chemin actuel
        			if(coutPrecedent != labelSuivant.getCost()) //Si le cout a été modifié alors
        			{
        				tas.insert(nodeSuivant);
        				labelActuel.setPere(a);        				
        			}
        		}
        	}
        }
        
        // on remonte les pères
        nodeActuel = data.getDestination();
        // Create the path from the array of predecessors...
        ArrayList<Arc> arcs = new ArrayList<>();
        System.out.println("REMONTEE");
        while (nodeActuel.getId() != data.getOrigin().getId())
        {
        	// on recupere l'arc du pere 
        	arcs.add(labels.get(nodeActuel.getId()).getPere());
        }
        
        // Reverse the path...
        Collections.reverse(arcs);

        // Create the final solution.
        solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(data.getGraph(), arcs));
    
        
        
        return solution;
    }

}
