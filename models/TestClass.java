package models;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

/**
 * This class is used to test the method called getStats in CoinRankApi class.
 * @author Kyle
 */
public class TestClass {

    private LinkedList<SingleCoin> testList1;
    private LinkedList<SingleCoin> testList2;
    private LinkedList<SingleCoin> testList3;
    private LinkedList<SingleCoin> testList4;
    private LinkedList<SingleCoin> testList5;
    private LinkedList<SingleCoin> testList6;
    private double RANGE_MIN_POS = 1.00;
    private double RANGE_MIN_NEG = -100.00;
    private double RANGE_MAX_POS = 100.00;
    // Edge case: even length list
    private final String[] testPrices1 = {"0.0001", "0.10", "5.002", "100.0", "17.9", "11.11"};
    // Edge case: empty list
    private final String[] testPrices2 = {};
    // Edge Case: two lowest values
    private final String[] testPrices3 = {"9.53", "0.00", "1900.32245", "100.0", "0.000012", "13.665", "0.00"};
    // Edge Case: single element
    private final String[] testPrices4 = {"9.53"};
    private CoinRankApi cri;


    public TestClass() {
        this.cri = new CoinRankApi();
        this.cri.start();
        this.cri.join();
        this.testList2 = new LinkedList();
        this.testList3 = new LinkedList();
        this.testList4 = new LinkedList();
        this.testList5 = new LinkedList();
        this.testList6 = new LinkedList();
    }

    /**
     * Create custom lists.
     */
    public void createCustomLists() {
        for(String item : testPrices1) {
            SingleCoin coin = new SingleCoin(item);
            this.testList3.add(coin);
        }
        for(String item : testPrices2) {
            SingleCoin coin = new SingleCoin(item);
            this.testList4.add(coin);
        }
        for(String item : testPrices3) {
            SingleCoin coin = new SingleCoin(item);
            this.testList5.add(coin);
        }
        for(String item : testPrices4) {
            SingleCoin coin = new SingleCoin(item);
            this.testList6.add(coin);
        }
    }

    public void testCustomList1() {
        for(String item : testPrices1) {
            SingleCoin coin = new SingleCoin(item);
            this.testList3.add(coin);
        }
        for(String item : testPrices2) {
            SingleCoin coin = new SingleCoin(item);
            this.testList4.add(coin);
        }
        for(String item : testPrices3) {
            SingleCoin coin = new SingleCoin(item);
            this.testList5.add(coin);
        }
        for(String item : testPrices4) {
            SingleCoin coin = new SingleCoin(item);
            this.testList6.add(coin);
        }


        HashMap<String, Double> map1 = this.cri.getStats(this.testList3);

        // Test List 1
        System.out.println("\nTest list 1, Odd length\n");

        System.out.println("Highest Price: ");
        if (map1.get("Highest Price") == 100){
            System.out.println("Pass (T)");
        } else {
            System.out.println("Fail (F)");
        }

        System.out.println("Lowest Price: ");
        if (map1.get("Lowest Price") == 0.0001){
            System.out.println("Pass (T)");
        } else {
            System.out.println("Fail (F)");
        }

        System.out.println("Range: ");
        if (map1.get("Range") == 99.9999){
            System.out.println("Pass (T)");
        } else {
            System.out.println("Fail (F)");
        }

        System.out.println("Mean: ");
        if (map1.get("Mean") == 22.352016666666668){
            System.out.println("Pass (T)");
        } else {
            System.out.println("Fail (F)");
        }

        System.out.println("Total: ");
        if (map1.get("Total") == 134.1121){
            System.out.println("Pass (T)");
        } else {
            System.out.println("Fail (F)");
        }

        System.out.println("Median: ");
        if (map1.get("Median") == 5.002){
            System.out.println("Pass (T)");
        } else {
            System.out.println("Fail (F)");
        }

        HashMap<String, Double> map2 = this.cri.getStats(this.testList4);
        // Test List 2
        System.out.println("\nTest list 2, Empty list\n");

        System.out.println("Highest Price: ");
        if (map2.get("Highest Price") == 0.00){
            System.out.println("Pass (T)");
        } else {
            System.out.println("Fail (F)");
        }

        System.out.println("Lowest Price: ");
        if (map2.get("Lowest Price") == 0.00){
            System.out.println("Pass (T)");
        } else {
            System.out.println("Fail (F)");
        }

        System.out.println("Range: ");
        if (map2.get("Range") == 0.00){
            System.out.println("Pass (T)");
        } else {
            System.out.println("Fail (F)");
        }

        System.out.println("Mean: ");
        if (map2.get("Mean") == 0.00){
            System.out.println("Pass (T)");
        } else {
            System.out.println("Fail (F)");
        }

        System.out.println("Total: ");
        if (map2.get("Total") == 0.00){
            System.out.println("Pass (T)");
        } else {
            System.out.println("Fail (F)");
        }

        System.out.println("Median: ");
        if (map2.get("Median") == 0.00){
            System.out.println("Pass (T)");
        } else {
            System.out.println("Fail (F)");
        }

        System.out.println("");

        HashMap<String, Double> map3 = this.cri.getStats(this.testList5);
        // Test List 3
        System.out.println("\nTest list 3, Normal list\n");

        System.out.println("Highest Price: ");
        if (map3.get("Highest Price") == 1900.32245){
            System.out.println("Pass (T)");
        } else {
            System.out.println("Fail (F)");
        }

        System.out.println("Lowest Price: ");
        if (map3.get("Lowest Price") == 0.00){
            System.out.println("Pass (T)");
        } else {
            System.out.println("Fail (F)");
        }

        System.out.println("Range: ");
        if (map3.get("Range") == 1900.32245){
            System.out.println("Pass (T)");
        } else {
            System.out.println("Fail (F)");
        }

        System.out.println("Mean: ");
        if (map3.get("Mean") == 289.0739231428571){
            System.out.println("Pass (T)");
        } else {
            System.out.println("Fail (F)");
        }

        System.out.println("Total: ");
        if (map3.get("Total") == 2023.5174619999998){
            System.out.println("Pass (T)");
        } else {
            System.out.println("Fail (F)");
        }

        System.out.println("Median: ");
        if (map3.get("Median") == 9.53){
            System.out.println("Pass (T)");
        } else {
            System.out.println("Fail (F)");
        }

        System.out.println("");
        HashMap<String, Double> map4 = this.cri.getStats(this.testList6);
        // Test List 4
        System.out.println("\nTest list 4, Single element list\n");

        System.out.println("Highest Price: ");
        if (map4.get("Highest Price") == 9.53){
            System.out.println("Pass (T)");
        } else {
            System.out.println("Fail (F)");
        }

        System.out.println("Lowest Price: ");
        if (map4.get("Lowest Price") == 9.53){
            System.out.println("Pass (T)");
        } else {
            System.out.println("Fail (F)");
        }

        System.out.println("Range: ");
        if (map4.get("Range") == 0){
            System.out.println("Pass (T)");
        } else {
            System.out.println("Fail (F)");
        }

        System.out.println("Mean: ");
        if (map4.get("Mean") == 9.53){
            System.out.println("Pass (T)");
        } else {
            System.out.println("Fail (F)");
        }

        System.out.println("Total: ");
        if (map4.get("Total") == 9.53){
            System.out.println("Pass (T)");
        } else {
            System.out.println("Fail (F)");
        }

        System.out.println("Median: ");
        if (map4.get("Median") == 9.53){
            System.out.println("Pass (T)");
        } else {
            System.out.println("Fail (F)");
        }

        System.out.println("");
    }

}
