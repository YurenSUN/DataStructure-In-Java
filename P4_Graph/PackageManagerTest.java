////////////////// File Header////////////////////
// Course: CS400 Lecture 001
// Assignment Name: p4
// Filename: PackageManagerTest.java
// Author: Yuren Sun
// Email: ysun299@wisc.edu
// Due date: April 19
// Other source credits: N/A
/////////////////////////////////////////////////

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit Test class for PackageManager
 * 
 * @author sun
 *
 */
class PackageManagerTest {
  PackageManager manager; // used for tests

  /**
   * run before every test initialize the graph
   * 
   * @throws Exception
   */
  @BeforeEach
  public void setUp() throws Exception {
    manager = new PackageManager();
  }

  /**
   * run after every test set graph to null
   * 
   * @throws Exception
   */
  @AfterEach
  public void tearDown() throws Exception {
    manager = null;
  }

  /**
   * test to load JSON File without exceptions
   */
  @Test
  public void test00_Load_File() {
    try {
      manager.constructGraph("cyclic.json");
      manager.constructGraph("valid.json");
    } catch (Exception e) {
      fail("fail to load JSON File");
      e.printStackTrace();
    }
  }

  /**
   * test getAllPackages with valid.json
   */
  @Test
  public void test01_getAllPackages_valid() {
    try {
      manager.constructGraph("valid.json");
      if (!manager.getAllPackages().toString().equals("[A, B, C, D, E]"))
        fail("fail to get all packages");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test getInstallationOrder with valid.json
   */
  @Test
  public void test02_getInstallationOrder_allPackages_valid() {
    try {
      manager.constructGraph("valid.json");
      // test getInstallationOrder on every package
      if (!manager.getInstallationOrder("A").toString().equals("[D, C, B, A]"))
        fail("fail to get InstallationOrder");

      if (!manager.getInstallationOrder("B").toString().equals("[D, C, B]"))
        fail("fail to get InstallationOrder");

      if (!manager.getInstallationOrder("C").toString().equals("[C]"))
        fail("fail to get InstallationOrder");

      if (!manager.getInstallationOrder("D").toString().equals("[D]"))
        fail("fail to get InstallationOrder");

      if (!manager.getInstallationOrder("E").toString().equals("[D, C, B, E]"))
        fail("fail to get InstallationOrder");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test toInstall with valid.json
   */
  @Test
  public void test03_toInstall_valid() {
    try {
      manager.constructGraph("valid.json");
      if (!manager.toInstall("A", "B").toString().equals("[A]"))
        fail("fail for toInstall");

      if (!manager.toInstall("A", "C").toString().equals("[D, B, A]"))
        fail("fail for toInstall");

      if (!manager.toInstall("A", "D").toString().equals("[C, B, A]"))
        fail("fail for toInstall");

      if (!manager.toInstall("A", "E").toString().equals("[A]"))
        fail("fail for toInstall");

      if (!manager.toInstall("B", "C").toString().equals("[D, B]"))
        fail("fail for toInstall");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test getInstallationOrderForAllPackages with valid.json
   */
  @Test
  public void test04_getInstallationOrderForAllPackages_valid() {
    try {
      manager.constructGraph("valid.json");
      if (!manager.getInstallationOrderForAllPackages().toString().equals("[C, D, B, E, A]"))
        fail("fail for getInstallationOrderForAllPackages");

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test getPackageWithMaxDependencies with valid.json
   */
  @Test
  public void test05_getPackageWithMaxDependencies_valid() {
    try {
      manager.constructGraph("valid.json");
      if (!manager.getPackageWithMaxDependencies().equals("A"))
        fail("fail for getPackageWithMaxDependencies");

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test getAllPackages with cyclic.json
   */
  @Test
  public void test06_getAllPackages_cyclic() {
    try {
      manager.constructGraph("cyclic.json");
      if (!manager.getAllPackages().toString().equals("[A, B]"))
        fail("fail to get all packages");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test getInstallationOrder with cyclic.json, should throw CycleException
   */
  @Test
  public void test07_getInstallationOrder_cyclic() {
    try {
      manager.constructGraph("cyclic.json");
      // test getInstallationOrder on every package
      manager.getInstallationOrder("A");
      fail("fail to throw CycleException");
    } catch (CycleException e) {
      // expected
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test toInstall with cyclic.json, should throw CycleException
   */
  @Test
  public void test08_toInstall_cyclic() {
    try {
      manager.constructGraph("cyclic.json");
      manager.toInstall("A", "B");
      fail("fail to throw CycleException");
    } catch (CycleException e) {
      // expected
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test getInstallationOrderForAllPackages with cyclic.json
   */
  @Test
  public void test09_getInstallationOrderForAllPackages_cyclic() {
    try {
      manager.constructGraph("cyclic.json");
      manager.getInstallationOrderForAllPackages();
      fail("fail to throw CycleException");

    } catch (CycleException e) {
      // expected
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test getPackageWithMaxDependencies with valid.json
   */
  @Test
  public void test10_getPackageWithMaxDependencies_cyclic() {
    try {
      manager.constructGraph("cyclic.json");
      manager.getPackageWithMaxDependencies();
      fail("fail to throw CycleException");

    } catch (CycleException e) {
      // expected
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test getInstallationOrder with shared_dependencies.json
   */
  @Test
  public void test11_getInstallationOrder_allPackages_shared_dependencies() {
    try {
      manager.constructGraph("shared_dependencies.json");
      // test getInstallationOrder on every package
      if (!manager.getInstallationOrder("A").toString().equals("[D, C, B, A]"))
        fail("fail to get InstallationOrder");

      if (!manager.getInstallationOrder("B").toString().equals("[D, B]"))
        fail("fail to get InstallationOrder");

      if (!manager.getInstallationOrder("C").toString().equals("[D, C]"))
        fail("fail to get InstallationOrder");

      if (!manager.getInstallationOrder("D").toString().equals("[D]"))
        fail("fail to get InstallationOrder");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test toInstall with shared_dependencies.json
   */
  @Test
  public void test12_toInstall_shared_dependencies() {
    try {
      manager.constructGraph("shared_dependencies.json");
      if (!manager.toInstall("A", "B").toString().equals("[C, A]"))
        fail("fail for toInstall");

      if (!manager.toInstall("A", "C").toString().equals("[B, A]"))
        fail("fail for toInstall");

      if (!manager.toInstall("A", "D").toString().equals("[C, B, A]"))
        fail("fail for toInstall");

      if (!manager.toInstall("B", "C").toString().equals("[B]"))
        fail("fail for toInstall");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test getInstallationOrderForAllPackages with shared_dependencies.json
   */
  @Test
  public void test13_getInstallationOrderForAllPackages_shared_dependencies() {
    try {
      manager.constructGraph("shared_dependencies.json");
      if (!manager.getInstallationOrderForAllPackages().toString().equals("[D, C, B, A]"))
        fail("fail for getInstallationOrderForAllPackages");

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test getPackageWithMaxDependencies with shared_dependencies.json
   */
  @Test
  public void test14_getPackageWithMaxDependencies_shared_dependencies() {
    try {
      manager.constructGraph("shared_dependencies.json");
      if (!manager.getPackageWithMaxDependencies().equals("A"))
        fail("fail for getPackageWithMaxDependencies");

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test getInstallationOrder with partCyclic.json
   */
  @Test
  public void test15_getInstallationOrder_allPackages_partCyclic_acyclic() {
    try {
      manager.constructGraph("partCyclic.json");
      // test getInstallationOrder on every package
      if (!manager.getInstallationOrder("C").toString().equals("[F, E, D, C]"))
        fail("fail to get InstallationOrder");

      if (!manager.getInstallationOrder("D").toString().equals("[F, E, D]"))
        fail("fail to get InstallationOrder");

      if (!manager.getInstallationOrder("E").toString().equals("[F, E]"))
        fail("fail to get InstallationOrder");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test getInstallationOrder with partCyclic.json, should throw CycleException
   */
  @Test
  public void test16_getInstallationOrder_partCyclic_cyclic() {
    try {
      manager.constructGraph("partCyclic.json");
      // test getInstallationOrder on every package
      manager.getInstallationOrder("B");
      fail("fail to throw CycleException");
    } catch (CycleException e) {
      // expected
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test toInstall with valid.json
   */
  @Test
  public void test17_toInstall_paftCyclic_aCyclic() {
    try {
      manager.constructGraph("partCyclic.json");
      if (!manager.toInstall("C", "D").toString().equals("[C]"))
        fail("fail for toInstall");

      if (!manager.toInstall("C", "E").toString().equals("[D, C]"))
        fail("fail for toInstall");

      if (!manager.toInstall("D", "E").toString().equals("[D]"))
        fail("fail for toInstall");

      if (!manager.toInstall("E", "C").toString().equals("[]"))
        fail("fail for toInstall");

      if (!manager.toInstall("E", "D").toString().equals("[]"))
        fail("fail for toInstall");

      if (!manager.toInstall("D", "C").toString().equals("[]"))
        fail("fail for toInstall");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test toInstall with partCyclic.json, should throw CycleException
   */
  @Test
  public void test18_toInstall_partCyclic_cyclic() {
    try {
      manager.constructGraph("partCyclic.json");
      manager.toInstall("A", "B");
      fail("fail to throw CycleException");
    } catch (CycleException e) {
      // expected
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test getInstallationOrderForAllPackages with partCyclic.json
   */
  @Test
  public void test19_getInstallationOrderForAllPackages_partCyclic() {
    try {
      manager.constructGraph("partCyclic.json");
      manager.getInstallationOrderForAllPackages();
      fail("fail to throw CycleException");

    } catch (CycleException e) {
      // expected
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test getPackageWithMaxDependencies with partCyclic.json
   */
  @Test
  public void test20_getPackageWithMaxDependencies_partCyclic() {
    try {
      manager.constructGraph("partCyclic.json");
      manager.getPackageWithMaxDependencies();
      fail("fail to throw CycleException");

    } catch (CycleException e) {
      // expected
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }
}
