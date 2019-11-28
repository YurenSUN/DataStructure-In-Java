import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
  
/**
 * Filename: PackageManager.java Project: p4 Author: Yuren Sun
 * 
 * PackageManager is used to process json package dependency files and provide function that make
 * that information available to other users.
 * 
 * Each package that depends upon other packages has its own entry in the json file.
 * 
 * Package dependencies are important when building software, as you must install packages in an
 * order such that each package is installed after all of the packages that it depends on have been
 * installed.
 * 
 * For example: package A depends upon package B, then package B must be installed before package A.
 * 
 * This program will read package information and provide information about the packages that must
 * be installed before any given package can be installed. all of the packages in
 * 
 * You may add a main method, but we will test all methods with our own Test classes.
 */

/**
 * Package Manager for packages to process json packages and check installation
 * 
 * @author sun
 *
 */
public class PackageManager {

  private Graph graph; // internal graph object

  /**
   * Package Manager default no-argument constructor. Initializes an empty "internal" graph object
   * (instance field)
   */
  public PackageManager() {
    this.graph = new Graph();
  }

  /**
   * Takes in a file path for a json file and builds the package dependency graph from it. process
   * (read and parse) the json file and build the internal graph. Add vertices and edges for the
   * packages and their dependencies.
   * 
   * @param jsonFilepath the name of json data file with package dependency information
   * @throws FileNotFoundException if file path is incorrect
   * @throws IOException           if the give file cannot be read
   * @throws ParseException        if the given json cannot be parsed
   */
  public void constructGraph(String jsonFilepath)
      throws FileNotFoundException, IOException, ParseException {
    // parsing jsonFile
    Object jsonFile = new JSONParser().parse(new FileReader(jsonFilepath));

    // casting Object to JSONObject
    JSONObject json = (JSONObject) jsonFile;

    JSONArray packages = (JSONArray) json.get("packages");
    // represent the array contain every package

    // traverse packages
    for (int i = 0; i < packages.size(); i++) {
      JSONObject jsonPackage = (JSONObject) packages.get(i);
      // represent each package contains name and dependencies

      String name = (String) jsonPackage.get("name");
      graph.addVertex(name);

      // traverse JSON array for dependencies to add edges
      // dependencies as predecessors
      JSONArray dependencies = (JSONArray) jsonPackage.get("dependencies");
      for (int j = 0; j < dependencies.size(); j++) {
        String dependency = (String) dependencies.get(j);
        graph.addEdge(name, dependency);
      }
    }
  }

  /**
   * Helper method to get all packages in the graph.
   * 
   * @return Set<String> of all the packages
   */
  public Set<String> getAllPackages() {
    return graph.getAllVertices();
  }

  /**
   * Given a package name, returns a list of packages in a valid installation order.
   * 
   * Valid installation order means that each package is listed before any packages that depend upon
   * that package.
   * 
   * @return List<String>, order in which the packages have to be installed
   * 
   * @throws CycleException           if you encounter a cycle in the graph while finding the
   *                                  installation order for a particular package. Tip: Cycles in
   *                                  some other part of the graph that do not affect the
   *                                  installation order for the specified package, should not throw
   *                                  this exception.
   * 
   * @throws PackageNotFoundException if the package passed does not exist in the dependency graph.
   */
  public List<String> getInstallationOrder(String pkg)
      throws CycleException, PackageNotFoundException {
    if (!graph.getAllVertices().contains(pkg)) // package not exist
      throw new PackageNotFoundException();

    // detect cycle
    boolean cycle = false;
    List<String> visited = new LinkedList<String>();
    List<String> over = new LinkedList<String>();
    if (!acycle(pkg, visited, over, cycle))
      throw new CycleException();

    List<String> order = new ArrayList<String>(); // list to return

    // use stack to get dependencies
    Stack<String> stack = new Stack<String>();
    stack.add(pkg);

    // traverse the stack
    while (!stack.isEmpty()) {
      String cur = stack.peek();
      List<String> curDependencies = graph.getAdjacentVerticesOf(cur);

      int count = 0; // count the number of dependencies already in the order
      for (String i : curDependencies) {
        if (order.contains(i)) // i is installed in the order
          count++;
        else
          stack.add(i);
      }
      if (count == curDependencies.size()) { // all of dependencies are installed
        if (!order.contains(cur))
          order.add(cur);
        stack.remove(cur);
      }
    }
    return order;

  }

  /**
   * test whether there is a cycle of the pkg there will be be cycle when pkg is visited and not
   * done
   * 
   * @param pkg
   * @returnr false when cycle exist
   */
  private boolean acycle(String pkg, List<String> visited, List<String> over, boolean cycle) {
    visited.add(pkg);
    List<String> dependencies = graph.getAdjacentVerticesOf(pkg); // store the dependencies of pkg

    // will be a cycle when pkg is visited and not done
    for (String i : dependencies) {
      if (!visited.contains(i))
        return acycle(i, visited, over, cycle);
      else if (!over.contains(i))
        return false;
    }

    over.add(pkg);
    return true;
  }

  /**
   * Given two packages - one to be installed and the other installed, return a List of the packages
   * that need to be newly installed.
   * 
   * For example, refer to shared_dependecies.json - toInstall("A","B") If package A needs to be
   * installed and packageB is already installed, return the list ["A", "C"] since D will have been
   * installed when B was previously installed.
   * 
   * @return List<String>, packages that need to be newly installed.
   * 
   * @throws CycleException           if you encounter a cycle in the graph while finding the
   *                                  dependencies of the given packages. If there is a cycle in
   *                                  some other part of the graph that doesn't affect the parsing
   *                                  of these dependencies, cycle exception should not be thrown.
   * 
   * @throws PackageNotFoundException if any of the packages passed do not exist in the dependency
   *                                  graph.
   */
  public List<String> toInstall(String newPkg, String installedPkg)
      throws CycleException, PackageNotFoundException {
    if ((!graph.getAllVertices().contains(newPkg))
        || (!graph.getAllVertices().contains(installedPkg))) // either
                                                             // package
                                                             // not exist
      throw new PackageNotFoundException();

    // represent the list to return
    List<String> returnList = new ArrayList<String>();

    // get the list of all installed and uninstalled dependencies
    List<String> installed = this.getInstallationOrder(installedPkg);
    installed.add(installedPkg);
    List<String> unInstalled = this.getInstallationOrder(newPkg);

    // add unInstalled to return list and remove installed ones
    for (String i : unInstalled)
      returnList.add(i);

    for (String i : installed) // delete the installed vertex
      if (returnList.contains(i))
        returnList.remove(i);

    return returnList;
  }

  /**
   * Return a valid global installation order of all the packages in the dependency graph.
   * 
   * assumes: no package has been installed and you are required to install all the packages
   * 
   * returns a valid installation order that will not violate any dependencies
   * 
   * @return List<String>, order in which all the packages have to be installed
   * @throws CycleException if you encounter a cycle in the graph
   */
  public List<String> getInstallationOrderForAllPackages() throws CycleException {
    Set<String> unInstall = graph.getAllVertices(); // set contains every package
    String[] unvisited = new String[unInstall.size()]; // array contains vertices not visited

    // store all packages to the unvisited
    for (String i : unInstall) {
      int index = 0;
      for (int j = 0; j < unvisited.length; j++)
        if (unvisited[j] != null)
          index++;
      unvisited[index] = i;
    }

    String[] order = new String[unvisited.length]; // the array store the order to be returned

    int num = unvisited.length - 1; // represent the position cur in while loop will store
    Stack<String> stack = new Stack<String>();

    // push all vertices without dependencies to the stack
    for (int i = 0; i < unvisited.length; i++) {
      String add = unvisited[i];
      if (graph.getAdjacentVerticesOf(add).isEmpty()) {
        stack.add(add);
        unvisited[i] = null;
      }
    }

    if (stack.isEmpty()) // no vertex without dependencies, cycle
      throw new CycleException();

    while (!stack.isEmpty()) {
      String cur = stack.peek();

      // get successors not installed
      List<String> suctoInstall = new ArrayList<String>();
      for (int i = 0; i < unvisited.length; i++) {
        String suc = unvisited[i];
        List<String> depend = graph.getAdjacentVerticesOf(suc);
        if (depend != null && depend.contains(cur))
          suctoInstall.add(suc);
      }

      if (suctoInstall.isEmpty()) { // all the successors are installed
        // add cur to the end of the order list and pop it from the stack
        order[num] = cur;
        num--;
        stack.pop();
      } else { // has successors not installed
        String install = suctoInstall.get(0);
        if (stack.contains(install)) // cycle exists
          throw new CycleException();

        // add the first vertex in the list of successors not installed
        // and delete it from toInstall
        stack.add(install);
        for (int i = 0; i < unvisited.length; i++) {
          if (unvisited[i] != null)
            if (unvisited[i].equals(install))
              unvisited[i] = null;
        }
      }
    }

    // detect cycle
    for (String i : order)
      if (i == null)
        throw new CycleException(); // cycle exist in part of graph

    // create the list to return
    List<String> returnOrder = new LinkedList<String>();
    for (String i : order)
      returnOrder.add(i);

    return returnOrder;
  }

  /**
   * Find and return the name of the package with the maximum number of dependencies.
   * 
   * Tip: it's not just the number of dependencies given in the json file. The number of
   * dependencies includes the dependencies of its dependencies. But, if a package is listed in
   * multiple places, it is only counted once.
   * 
   * Example: if A depends on B and C, and B depends on C, and C depends on D. Then, A has 3
   * dependencies - B,C and D.
   * 
   * @return String, name of the package with most dependencies.
   * @throws CycleException           if you encounter a cycle in the graph
   * @throws PackageNotFoundException
   */
  public String getPackageWithMaxDependencies() throws CycleException, PackageNotFoundException {
    Set<String> pack = graph.getAllVertices(); // set contains every package
    String[] packages = new String[pack.size()]; // array to store every package
    Integer[] numsDepend = new Integer[packages.length]; // store the dependency of every package

    // store every package to the packages array
    for (String i : pack) {
      int index = 0;
      for (int j = 0; j < packages.length; j++)
        if (packages[j] != null)
          index++;
      packages[index] = i;
    }

    // store the number of dependencies of every package to the numsDepend with the
    // same index
    for (int i = 0; i < packages.length; i++) {
      List<String> dependencies = this.getInstallationOrder(packages[i]);
      if (dependencies != null)
        numsDepend[i] = dependencies.size();
    }

    // get the index of max dependency
    int max = numsDepend[0];
    int index = 0;
    for (int i = 0; i < numsDepend.length - 1; i++) {
      if (max < numsDepend[i]) {
        index = i;
        max = numsDepend[i];
      }
    }

    return packages[index];
  }

  /**
   * main method to test the function of the class not used
   * 
   * @param args
   */
  public static void main(String[] args) {

  }
}
