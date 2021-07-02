import java.util.*;
import java.io.*;
import java.math.BigInteger;

class KCoreMaintenance
{
	public static Graph g;
	public static String temp;
	public static BufferedReader br;
	public static long starttime;

	static class Graph
	{
		int V; // No. of vertices

		Vector<Integer>[] adj;
		LinkedList<LinkedList<String> > newArray;

		@SuppressWarnings("unchecked")
		Graph(int V)
		{
			this.V = V;
			this.adj = new Vector[V];
			this.newArray = new LinkedList<LinkedList<String> >();
			for (int i = 0; i < V; i++){
				adj[i] = new Vector<>();
			}
		}

		// function to add an edge to graph
		void addEdge(int u, int v)
		{
			this.adj[u].add(v);
			this.adj[v].add(u);
		}

		boolean DFSUtil(int v, boolean[] visited, int[] vDegree, int k)
		{

			visited[v] = true;
			for (int i : adj[v])
			{

				if (vDegree[v] < k)
					vDegree[(int)(i)]--;

				if (!visited[(int)(i)])
				{

					DFSUtil((int)(i), visited, vDegree, k);
				}
				
			}

			return (vDegree[v] < k);
		}

		void printKCores(int k, int update, String currentLine)
		{

			boolean[] visited = new boolean[V];
			boolean[] processed = new boolean[V];
			Arrays.fill(visited, false);
			Arrays.fill(processed, false);

			int mindeg = Integer.MAX_VALUE;
			int startvertex = 0;

			int[] vDegree = new int[V];
			for (int i = 0; i < V; i++)
			{
				vDegree[i] = adj[i].size();

				if (vDegree[i] < mindeg)
				{
					mindeg = vDegree[i];
					startvertex = i;
				}
			}

			DFSUtil(startvertex, visited, vDegree, k);

			for (int i = 0; i < V; i++)
				if (!visited[i])
					DFSUtil(i, visited, vDegree, k);

	        if (update == 0) {
				// System.out.println("K-Cores : ");
				for (int v = 0; v < V; v++)
				{

					if (vDegree[v] >= k)
					{
						
						int smallcounter = 0;
                        int largecounter = 0;

						newArray.add(new LinkedList<String>());

						newArray.get(v).add(v+ "."+vDegree[v]);

						int ite = 1;
						int tempVdegreeforvertex = vDegree[v];
						ArrayList<Integer> arr = new ArrayList<Integer>();

						for (int i : adj[v])
							if (vDegree[i] >= k) {

								arr.add(vDegree[i]);

								newArray.get(v).add(i+ "."+vDegree[i]);

								if (ite < adj[v].size()) {

									ite++;
								} else {
									if (vDegree[v] != 0) {
                                    }
									
								}
							}

						Collections.sort(arr,Collections.reverseOrder());

						/* check in sorted array of degrees for observation of k cores */
                        for (int m = arr.size() -1; m >= 0; m--) {
                            if (arr.get(m) < tempVdegreeforvertex) {
                                tempVdegreeforvertex--;
                                smallcounter++;
                            } else {
                                largecounter++;
                            }
                        }
                        /* */

                        newArray.get(v).set(0,v + "." + (vDegree[v]-smallcounter));

					}
					// System.out.println();
				}

				// System.out.println("First adding vertex with neighbours and their degrees=====>>>"+ newArray);

				// System.out.println("Observation started===> ");
			
			} else if (update == 1){
				ArrayList<String> setForIncreasing = new ArrayList<String>();
				ArrayList<String> tempSet = new ArrayList<String>();

				ArrayList<String> anotherSet = new ArrayList<String>();

				// System.out.println("currentline == " + currentLine);
				String[] s = currentLine.split("\\s+");
				
				anotherSet.add(s[0]);
				anotherSet.add(s[1]);

				int increaseValueOfs0 = 0;
				int increaseValueOfs1 = 0;

				for (int v = 0; v < V; v++)
				{
					if ( v == Integer.parseInt(s[0])) {
						boolean gotBoth = false;
						
						// System.out.println("Whese s[0]= "+ s[0] +" GEtting value for s[1] core from newarray= "+ newArray.get(Integer.parseInt(s[1])).getFirst());
						
						for (int qq = 0; qq < newArray.size(); qq++) {
							int existsInBoth = 0;
							for ( int tt = 0; tt < newArray.get(qq).size(); tt++) {
								String ss = newArray.get(qq).get(tt);
								String vertex = ss.substring(0,ss.indexOf("."));
								if (vertex.equals(s[0]) || vertex.equals(s[1])) {
									existsInBoth++;
								}
							}
							if ( existsInBoth == 2) {
								setForIncreasing.add(String.valueOf(qq));
								gotBoth = true;
							}
							// System.out.println("Exists In both where core is == " + qq + " and existsInboth value== " + existsInBoth);
						}

						String s0 = newArray.get(Integer.parseInt(s[0])).getFirst();
						String s0Degree = s0.substring(s0.indexOf(".")+1);

						String s1 = newArray.get(Integer.parseInt(s[1])).getFirst();
						String s1Degree = s1.substring(s1.indexOf(".")+1);

						if (gotBoth == true) {							

							if ( (Integer.parseInt(s0Degree) < Integer.parseInt(s1Degree)) || (Integer.parseInt(s0Degree) == Integer.parseInt(s1Degree)) ) {
								increaseValueOfs0++;
								setForIncreasing.add(s[0]);
							}
							// System.out.println("boolean chekcer !" + s0Degree + "Value of increaseValueofs0 ==== " + increaseValueOfs0);
						} else {
							if ( (Integer.parseInt(s0Degree) < Integer.parseInt(s1Degree)) || (Integer.parseInt(s0Degree) == Integer.parseInt(s1Degree)) ) {
								increaseValueOfs0++;
								setForIncreasing.add(s[0]);
							}
							// System.out.println("false bata airacha that both ma chaina add vako duita vertices!!!");
						}
					} else if (v == Integer.parseInt(s[1])) {
						boolean gotBoth = false;
						
						// System.out.println("Whese s[1]= "+ s[1] +" GEtting value for s[0] core from newarray= "+ newArray.get(Integer.parseInt(s[0])).getFirst());
						
						for (int qq = 0; qq < newArray.size(); qq++) {
							int existsInBoth = 0;
							for ( int tt = 0; tt < newArray.get(qq).size(); tt++) {
								String ss = newArray.get(qq).get(tt);
								String vertex = ss.substring(0,ss.indexOf("."));
								if (vertex.equals(s[0]) || vertex.equals(s[1])) {
									existsInBoth++;
								}
							}
							if ( existsInBoth == 2) {
								setForIncreasing.add(String.valueOf(qq));
								gotBoth = true;
							}
							// System.out.println("Exists In both where core is == " + qq + " and existsInboth value== " + existsInBoth);
						}

						String s0 = newArray.get(Integer.parseInt(s[0])).getFirst();
						String s0Degree = s0.substring(s0.indexOf(".")+1);

						String s1 = newArray.get(Integer.parseInt(s[1])).getFirst();
						String s1Degree = s1.substring(s1.indexOf(".")+1);

						if (gotBoth == true) {

							if ( (Integer.parseInt(s0Degree) > Integer.parseInt(s1Degree)) || (Integer.parseInt(s0Degree) == Integer.parseInt(s1Degree)) ) {
								increaseValueOfs1++;
								setForIncreasing.add(s[1]);
							} 
							// System.out.println("boolean chekcer !" + s1Degree + "Value of increaseValueofs1 ==== " + increaseValueOfs1);
						} else {
							if ( (Integer.parseInt(s0Degree) > Integer.parseInt(s1Degree)) || (Integer.parseInt(s0Degree) == Integer.parseInt(s1Degree)) ) {
								increaseValueOfs1++;
								setForIncreasing.add(s[1]);
							} 
							// System.out.println("false bata airacha that both ma chaina add vako duita vertices!!!");
						}
					} 		// System.out.println();
				}
				
				// removing duplicate values
				for (int i =0; i < setForIncreasing.size(); i ++) {
					if (!tempSet.contains(setForIncreasing.get(i))) {
						tempSet.add(setForIncreasing.get(i));
					}
				}
				Collections.sort(tempSet);
				// System.out.println("SetforIncreasing " +tempSet + " " + currentLine);
				// System.out.println("value of incresing s0 and s1 ==" + increaseValueOfs0 +" "+ increaseValueOfs1 );
				// System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\\n" +newArray);

				// for (int ts = 0; ts < tempSet.size(); ts++) {
					for (int v = 0; v < V; v++)
					{
						if ( (tempSet.contains(String.valueOf(v)) && v == Integer.parseInt(s[0])) || ( anotherSet.contains(String.valueOf(v)) && v == Integer.parseInt(s[0])) ) {
							// System.out.println("------------------------------   First if condition ------------------------------------");
							String vd = newArray.get(v).getFirst();
							String vertexWithDot = vd.substring(0,vd.indexOf(".")+1);
							String vertexDegree = vd.substring(vd.indexOf(".")+1);
							newArray.get(v).set(0,vertexWithDot + String.valueOf(Integer.parseInt(vertexDegree)+1));

							String nv = newArray.get(Integer.parseInt(s[1])).getFirst();
							String neighbourVertex = nv.substring(0,nv.indexOf("."));
							String neighbourVertexWithDegree = nv.substring(nv.indexOf(".")+1);
							if ( tempSet.contains(neighbourVertex) ) {
								newArray.get(v).add(neighbourVertex + "." + String.valueOf(Integer.parseInt(neighbourVertexWithDegree)+1));
							} else {
								// System.out.println("ma else ma chu laaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
								newArray.get(v).add(nv);
							}
							// System.out.println("Reached OOOOOKKKKKAAAAAAYYYYYYYYYYYYY!!!!!!!!!! "+ v + "   " +vd + " " + newArray.get(v).get(0));
						} else if (tempSet.contains(String.valueOf(v)) && v == Integer.parseInt(s[1])) {

							// System.out.println("------------------------------  Else if condition ------------------------------------");
							String vd = newArray.get(v).getFirst();
							String vertexWithDot = vd.substring(0,vd.indexOf(".")+1);
							String vertexDegree = vd.substring(vd.indexOf(".")+1);
							newArray.get(v).set(0,vertexWithDot + String.valueOf(Integer.parseInt(vertexDegree)+1));

							String nv = newArray.get(Integer.parseInt(s[0])).getFirst();
							String neighbourVertex = nv.substring(0,nv.indexOf("."));
							String neighbourVertexWithDegree = nv.substring(nv.indexOf(".")+1);

							newArray.get(v).add(nv);

							// System.out.println("Reached OOOOOKKKKKAAAAAAYYYYYYYYYYYYY!!!!!!!!!! "+ v + "   " +vd + " " + newArray.get(v).get(0));

						} else if ( tempSet.contains(String.valueOf(v)) && v != Integer.parseInt(s[0]) && v != Integer.parseInt(s[1])) {
							// System.out.println("------------------------------  Second Else if condition ------------------------------------");
							String vd = newArray.get(v).getFirst();
							String vertexWithDot = vd.substring(0,vd.indexOf(".")+1);
							String vertexDegree = vd.substring(vd.indexOf(".")+1);
							newArray.get(v).set(0,vertexWithDot + String.valueOf(Integer.parseInt(vertexDegree)+1));
							
							for (int m = 0; m < newArray.get(v).size(); m++) {
								String od = newArray.get(v).get(m);
								String ownVertex = od.substring(0,od.indexOf("."));
								String ownVertexDegree = od.substring(od.indexOf(".")+1);
								// System.out.println("Own Vertex===> " + ownVertex);
								if (ownVertex.equals(s[0]) || ownVertex.equals(s[1])) {
									newArray.get(v).set(m,ownVertex + "." + String.valueOf(Integer.parseInt(ownVertexDegree) + 1));
									// System.out.println("////////////////////////////////////////////////////////////////////////////////////////////");
								}

							}

							// System.out.println("Reached OOOOOKKKKKAAAAAAYYYYYYYYYYYYY!!!!!!!!!! "+ v + "   " +vd + " " + newArray.get(v).get(0));
						}
						// System.out.println();
					}
				// }
			
			} else {
				if (update == 2) {
					/* Deleting edge */
					String[] deleteLine = currentLine.split("\\s+");
					// System.out.println("Update Function==>" +deleteLine[0] +", " +deleteLine[1]);
					String firstVertextoBeDeleted = deleteLine[0];
					String secondVertextoBeDeleted = deleteLine[1];
					for (int i = 0; i < V; i++) {
						// System.out.println("before deleting==== "+adj[i]);
					}
					/**/
					for (int i = 0; i < V; i++) {
						// System.out.println("before deleting==== "+adj[i]);
						if (i == Integer.parseInt(firstVertextoBeDeleted) ) {
							int mm = 0;
							for (int j = 0; j < adj[i].size(); j++) {
								if ( adj[i].get(j) == Integer.parseInt(secondVertextoBeDeleted)) {

									// System.out.println("Adjacency Array::: "+adj[i].get(j) + "mm+++ " + mm);
									adj[i].remove(j);
								}
								mm++;
							}
							// System.out.println(adj[i]);
						} else {
							if (i == Integer.parseInt(secondVertextoBeDeleted) ) {
								int mm = 0;
								for (int j = 0; j < adj[i].size(); j++) {
									if ( adj[i].get(j) == Integer.parseInt(firstVertextoBeDeleted)) {

										// System.out.println("Adjacency Array::: "+adj[i].get(j) + "mm+++ " + mm);
										adj[i].remove(j);
									}
									mm++;
								}
								// System.out.println(adj[i]);
							}
						}
						// System.out.println(adj[i]);

					}

					for (int i = 0; i < V ; i++) {
						// Iterator<String> iterate = newArray.get(i).iterator();
						String cv = newArray.get(i).getFirst();
						String coreVertex = cv.substring(0,cv.indexOf("."));
						int vertexMatch = 0;
						if(coreVertex.equals(firstVertextoBeDeleted)) {
							for(int j = 0; j < newArray.get(i).size(); j++) {
								String vd = newArray.get(i).get(j);
								String vertexG = vd.substring(0,vd.indexOf("."));
								String vertexDegreeG = vd.substring(vd.indexOf(".")+1);

								if (j == 0) {
									newArray.get(i).set(j,vertexG+"."+(String.valueOf(Integer.parseInt(vertexDegreeG) -1)));
								} else {
									if (vertexG.equals(secondVertextoBeDeleted)) {
										newArray.get(i).remove(j);
									}
								}

							}
						} else {
							if (coreVertex.equals(secondVertextoBeDeleted)){

								for(int j = 0; j < newArray.get(i).size(); j++) {
									String vd = newArray.get(i).get(j);
									String vertexG = vd.substring(0,vd.indexOf("."));
									String vertexDegreeG = vd.substring(vd.indexOf(".")+1);

									if (j == 0) {
										newArray.get(i).set(j,vertexG+"."+(String.valueOf(Integer.parseInt(vertexDegreeG) -1)));
									} else {
										if (vertexG.equals(firstVertextoBeDeleted)) {
											newArray.get(i).remove(j);
										}
									}

								}
							}

						}
					}
				}
				// System.out.println(newArray);
			}
			// observeArray(V,newArray);
			// Long endTimeKCore = System.currentTimeMillis();
			// System.out.println("Time Taken to compute Kcore----> "+ ((endTimeKCore- starttime)/1000F)+" sec");

			// System.out.println("Final Obeserved Array~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~::: " + newArray);
			// System.out.println(newArray);
			// for (int i =0; i < V; i++) {
			// 	String fff = newArray.get(i).getFirst();
			// 	// System.out.println("ffffffffffffffffffffff================"+ fff);
			// 	String firstfirst = fff.substring(fff.indexOf(".")+1);
			// 	// System.out.println("ffffffffffffffffffffff================"+ firstfirst);
			// 	if (!firstfirst.equals("0")) {
			// 		// System.out.print(newArray.get(i)+ " --- ");
			// 	}
			// }			
		}
	}
	public static void observeArray(int V, LinkedList<LinkedList<String> > newArray) {
		/****************************/
				for ( int i=0; i < V; i++) {
					int smallestDegree = 0;
					int edgeDegree = 0;
					int countRepeat = 1;

					Iterator<String> iterator = newArray.get(i).iterator();
					String degree = iterator.next();
					int vertexDegree = Integer.parseInt(degree.substring(degree.indexOf(".")+1));

					int tempVertexDegree = vertexDegree;
					
					if (vertexDegree != 0) {
						int checkIfNeighboursDegreeisGreaterOrEqual = 0;
						// System.out.println("Vertex Degree:= "+vertexDegree);
						while (iterator.hasNext()){
							
							String ss = iterator.next();
							edgeDegree = Integer.parseInt(ss.substring ( ss.indexOf ("." )+1 ));

							if (edgeDegree!= 0 && smallestDegree == 0 || smallestDegree > edgeDegree) {
								smallestDegree = edgeDegree;
							} else {
								if (smallestDegree < edgeDegree) {
									smallestDegree = smallestDegree;
								}
							}

							if (smallestDegree == edgeDegree && smallestDegree != 0 && edgeDegree != 0) {
								countRepeat = countRepeat;
								tempVertexDegree = tempVertexDegree-1;
							}

							if (edgeDegree >= vertexDegree) {
								checkIfNeighboursDegreeisGreaterOrEqual++;
							}

						}
							//check if the checkIfNeighboursDegreeisGreaterOrEqual >= current vertex degree
							int take = 0;
							int decreaseDegree = 0;
							while (checkIfNeighboursDegreeisGreaterOrEqual < vertexDegree ) {
								Iterator<String> iterator2 = newArray.get(i).iterator();
								String degree2 = iterator2.next();
								int vertexDegree2 = Integer.parseInt(degree2.substring(degree2.indexOf(".")+1));
								while (iterator2.hasNext()) {
									String ss2 = iterator2.next();
									int edgeDegree2 = Integer.parseInt(ss2.substring ( ss2.indexOf ("." )+1 ));
									if(edgeDegree2 >= (vertexDegree - 1)) {
										take++;
									}
								}
								checkIfNeighboursDegreeisGreaterOrEqual = take;
								vertexDegree = vertexDegree -1;

								decreaseDegree++;
							}
							// System.out.println();

						// System.out.println();
						edgeDegree = 0;
						int j =0;
						// smallestDegree = 0;
						if (decreaseDegree > 0) {
							// System.out.println("trueee");
							// for (int j=0; j < newArray.get(i).size(); j++) {
								Iterator<String> updateArray = newArray.get(i).iterator();
								int vd = 0;
										
								while(updateArray.hasNext()) {

									String dd = updateArray.next();									
									String vertexWithDot= dd.substring(0,dd.indexOf(".")+1);								
									vd = Integer.parseInt(dd.substring(dd.indexOf(".")+1));
									// System.out.println("Vertex Degree Before ===> " + vd);

									if (j == 0) {
										int vertexWithDegreeUpdated = vd - decreaseDegree;
										newArray.get(i).set(j,vertexWithDot+String.valueOf(vertexWithDegreeUpdated));
									}
										j++;
								}
								// System.out.println("Vertex Degree After ===> " + vd);
								// System.out.println("Updated Array===> "+updateArray);
							// }
						} else {

							// System.out.println("false");
						}
					}
					
				}
				/*******************************/
	}
	public static void balanceArray(int V, LinkedList<LinkedList<String> > newArray) {

		ArrayList<String> coreVertexWithDegree = new ArrayList<String>();

		ArrayList<String> allVertices = new ArrayList<String>();
		
		// System.out.println("New Array SIZE====> " + V);
		
		// System.out.println("New Array " + newArray);
		
		for( int i = 0; i < newArray.size(); i++ ) {
			for (int j = 0; j < newArray.get(i).size(); j++ ) {
				String vd = newArray.get(i).get(j);
				String vertices = vd.substring(0,vd.indexOf("."));
				String verticesWithDegree = vd.substring(vd.indexOf(".")+1);

				allVertices.add(vd);
				// }
			}
		}

		// System.out.println("All Vertices before ===> " +allVertices);

		for (int i = 0; i < V; i++ ) {
			String fff = newArray.get(i).getFirst();
			String firstVertex = fff.substring(0,fff.indexOf("."));
			String firstVertexD = fff.substring(fff.indexOf(".")+1);
			// coreVertexWithDegree.add(fff);

			// System.out.println("Firstvertex "+ firstVertex);

			if (!firstVertexD.equals("0")) {
				coreVertexWithDegree.add(fff);
			}
			for (int j = 0; j < allVertices.size(); j++ ) {
				String vd = allVertices.get(j);
				String vertices = vd.substring(0,vd.indexOf("."));
				String verticesWithDegree = vd.substring(vd.indexOf(".")+1);

				if (vertices.equals(firstVertex) && !firstVertexD.equals("0")) {
					allVertices.set(j,vertices+"."+firstVertexD);
				} 
			}
		}
	}

	public static void runGC() {
     	Runtime runtime = Runtime.getRuntime();
    	long memoryMax = runtime.maxMemory();
	    long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
	    double memoryUsedPercent = (memoryUsed * 100.0) / memoryMax;
	    System.out.println("memoryUsedPercent: " + memoryUsedPercent);
	     if (memoryUsedPercent > 90.0)
	        System.gc();

  	}

	public static void main(String[] args) throws Exception
	{
		// starttime = System.currentTimeMillis();
		

		HashMap<Integer, Integer> data = new HashMap<Integer, Integer>();
		Set<Integer> ver = new HashSet<Integer>();
		// Create a graph given in the above diagram
		int k = 0;

		// File file = new File("email-Eu-core.txt");
		// File file = new File("com-youtube.ungraph.txt");
		// File file = new File("facebook_combined.txt");
		// File file = new File("com-amazon.ungraph.txt");
		File file = new File("testing-1.txt");
		// File file = new File("web-BerkStan.txt");
	
        
        InputStream is = new FileInputStream(file);
        // BufferedReader forTotalLines = new BufferedReader(new InputStreamReader(is));
        int totalLines = 0;
        br = new BufferedReader(new InputStreamReader(is));
        temp = null;

		Scanner sc;
		try {
			sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String[] s = sc.nextLine().split("\\s+");
				ver.add(Integer.parseInt(s[0]));
				ver.add(Integer.parseInt(s[1]));
				totalLines++;
			}
		System.out.println("Total number of lines in the file: "+totalLines);
		
		// System.out.println(" ver size : "+ ver.size());
		// System.out.println(" ver size : "+ ver);
		
		int max = Collections.max(ver);
		// System.out.println(" maaaaaaaaaaaaaaaaaaaaaax : "+ max);

		
		g = new Graph(max+1);

		int count = 0;

		int readTotalLinesAtOnce = 100000;

		int initialValueToStartReading = 0;
		////////////////////////
		//Start run//
		int lineNumber=0;
		int test= 50000;
		int maximumLine = max+2;
		int sixtyPercent = (totalLines * 60) / 100;

		System.out.println("Sixty Percent==> " +sixtyPercent);
		// int tempvalue = 0;

		int quotient = sixtyPercent / test;
		// System.out.println("quotient==> " +quotient);
		int inc = 2;
		int tempvalue = test*quotient;
		// System.out.println("tempvalue " +tempvalue);
		// if( tempvalue < sixtyPercent) {
		// 	quotient = quotient +1;
		// }

		long firststarttime = System.currentTimeMillis();

		int i;
		for (i = 0; i < sixtyPercent; i++) {
			// readline(lineNumber,test,0);

			// for (int j = lineNumber; j < test; j++) {
				temp = br.readLine();
					// System.out.println("Printing lines===> "+temp);
				 String[] s = temp.split("\\s+");
		        g.addEdge(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
				// cc++;
			// }
			// System.out.println("Value of TEST: "+test);
			// lineNumber = test;
			// test = test + 50000;
			// inc++;
		}
		String currentLine = "";
		g.printKCores(0,0,currentLine);

		Long firstendTimeKCore = System.currentTimeMillis();
		System.out.println("Time Taken for observation----> "+ ((firstendTimeKCore- firststarttime)/1000F)+" sec");

		runGC();
		int mb = 1024 * 1024;
		   // get Runtime instance
		   Runtime instanceFirst = Runtime.getRuntime();
		   System.out.println("***** Heap utilization statistics [MB] *****\n");
		   // available memory
		   System.out.println("Total Memory: " + instanceFirst.totalMemory() / mb + " mb");
		   // free memory
		   System.out.println("Free Memory: " + instanceFirst.freeMemory() / mb  + " mb");
		   // used memory
		   System.out.println("Used Memory: "
		           + (instanceFirst.totalMemory() - instanceFirst.freeMemory()) / mb  + " mb");
		   // Maximum available memory
		   System.out.println("Max Memory: " + instanceFirst.maxMemory() / mb  + " mb");

		long insertstarttime = System.currentTimeMillis();
		if ( i < totalLines ) {
			readline(i, totalLines, 1);
		}
		
		Long insertendTimeKCore = System.currentTimeMillis();
		System.out.println("Time Taken to insert edges----> "+ ((insertendTimeKCore- insertstarttime)/1000F)+" sec");

		runGC();
	 	mb = 1024 * 1024;
	   // get Runtime instance
	   Runtime instanceInsert = Runtime.getRuntime();
	   System.out.println("***** Heap utilization statistics [MB] *****\n");
	   // available memory
	   System.out.println("Total Memory: " + instanceInsert.totalMemory() / mb + " mb");
	   // free memory
	   System.out.println("Free Memory: " + instanceInsert.freeMemory() / mb  + " mb");
	   // used memory
	   System.out.println("Used Memory: "
	           + (instanceInsert.totalMemory() - instanceInsert.freeMemory()) / mb  + " mb");
	   // Maximum available memory
	   System.out.println("Max Memory: " + instanceInsert.maxMemory() / mb  + " mb");

	/*-----------------Delete edges----------------*/
		long deletestarttime = System.currentTimeMillis();
		int twentyPercent = (totalLines * 20) / 100;

		ArrayList<Integer> totalNumbers = new ArrayList<Integer>();
		for(int m = 0; m < totalLines; m++)
		{
			totalNumbers.add(m);
		}
		Collections.shuffle(totalNumbers);
		// System.out.println(totalNumbers + " "+twentyPercent+ " " +totalLines);
		// System.out.print("random selected 20% numbers==");
		for(int j =0; j < twentyPercent; j++)
		{
			// System.out.print(totalNumbers.get(j) + " ");
			lineNumber = totalNumbers.get(j);
			// System.out.println("Deleted Line: " + lineNumber);
			readline(lineNumber,test,2);
			// readline(3,test,2);
		}

		Long deleteendTimeKCore = System.currentTimeMillis();
		System.out.println("Time Taken to delete edges----> "+ ((deleteendTimeKCore- deletestarttime)/1000F)+" sec");

		runGC();
		 mb = 1024 * 1024;
		   // get Runtime instance
		   Runtime instanceDelete = Runtime.getRuntime();
		   System.out.println("***** Heap utilization statistics [MB] *****\n");
		   // available memory
		   System.out.println("Total Memory: " + instanceDelete.totalMemory() / mb + " mb");
		   // free memory
		   System.out.println("Free Memory: " + instanceDelete.freeMemory() / mb  + " mb");
		   // used memory
		   System.out.println("Used Memory: "
		           + (instanceDelete.totalMemory() - instanceDelete.freeMemory()) / mb  + " mb");
		   // Maximum available memory
		   System.out.println("Max Memory: " + instanceDelete.maxMemory() / mb  + " mb");

		/*------------End Delete Edges------------------*/
	    	String tvalue= "";
	    // readline(lineNumber,test,2);

	 //    Long endTimeKCore = System.currentTimeMillis();
		// System.out.println("Time Taken to compute Kcore----> "+ ((endTimeKCore- starttime)/1000F)+" sec");

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
	}

	public static void readline(int lineNumber, int test, int update) throws Exception {
		// System.out.println("VAlue of Linenumber and TEST: " + lineNumber + "  "+ test);
		int cc = 0;
		if (update == 0) {
			for (int i = lineNumber; i < test; i++) {
				temp = br.readLine();
					// System.out.println("Printing lines===> "+temp);
				 String[] s = temp.split("\\s+");
		        g.addEdge(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
				cc++;
			}
			String currentLine = "";
			g.printKCores(0,0,currentLine);
			System.out.println("------------------------------------------------------------------------------------------");
		}  else if( update == 1) {
			// System.out.println("Going from update ###################");
			for (int i = lineNumber; i < test; i++) {
				temp = br.readLine();
				// System.out.println("Printing lines===> "+temp);
				String[] s = temp.split("\\s+");
		        g.addEdge(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
				// starttime = System.currentTimeMillis();
		        g.printKCores(0,1,temp);
				// Long endTimeKCore = System.currentTimeMillis();
				// System.out.println("Time Taken to compute Kcore----> "+ ((endTimeKCore- starttime)/1000F)+" sec");
				cc++;
			}
			
		} else {
				String currentLine = "";
				try (BufferedReader brr = new BufferedReader(new FileReader("testing-1.txt"))) {
				for (int i = 0; i < lineNumber; i++) {
					brr.readLine();
				}
				currentLine = brr.readLine();
				// System.out.println("CurrentLine to be deleted===> "+currentLine);
				g.printKCores(0,2,currentLine);
			} catch(IOException e){
				System.out.println(e);
			}
			
		}
	}
}