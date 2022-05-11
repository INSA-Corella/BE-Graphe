package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.BinaryPathReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;
import org.junit.Test;

public class TestsPath {

	@Test
	public void test() throws IOException {
        // Visit these directory to see the list of available files on Commetud.
        final String mapName = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
        final String correctPathName = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Paths/path_fr31insa_rangueil_r2.path";

        // Create a graph reader.
        final GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

        final Graph graph = reader.read();
        
        final PathReader pathReader = new BinaryPathReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(correctPathName))));

        final Path correctPath = pathReader.readPath(graph);
        Node origin = correctPath.getOrigin();
        Node dest = correctPath.getDestination();

        List<ArcInspector> ai = ArcInspectorFactory.getAllFilters();
        
        
        //    public ShortestPathData(Graph graph, Node origin, Node destination, ArcInspector arcInspector) {
        ShortestPathData sh = new ShortestPathData(graph, origin, dest, ai.get(0));
        DijkstraAlgorithm d = new DijkstraAlgorithm(sh);
        
        ShortestPathSolution solution = d.run();
        
        assertTrue((solution.getPath().compareTo(correctPath) == 0));
        
	}

}
