/**
 * class which acts a simple bean used as a return type from various functions
 * 
 * @author Ravindhar Reddy Thallapureddy
 * Last modified on : 5/12/2016 10:37 am
 */
public class EulerBean {

	Vertex oddVertex; //represents a vertex with odd degree
	int eulerTourOrPath; // variable to capture graph property abt euler
	// 1 represent tour, 2 represents path, 3 represents not euler
	
	public EulerBean(int val, Vertex v) {
		eulerTourOrPath = val;
		oddVertex = v;
	}
}
