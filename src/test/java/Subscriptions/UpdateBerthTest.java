package Subscriptions;

import data.Subscriprions.UpdateBerths;

import java.util.ArrayList;
import java.util.TreeMap;

public class UpdateBerthTest {
    public static void main(String ... args){
        TreeMap<Integer, ArrayList<ArrayList<String>>> oldOne = new TreeMap<>();
        TreeMap<Integer, ArrayList<ArrayList<String>>> newOne = new TreeMap<>();
        ArrayList<ArrayList<String>> arr = new ArrayList<>();
        ArrayList<String> vessel = new ArrayList<>();
        vessel.add("Nabiru");
        vessel.add("22.05.2019");
        arr.add(vessel);
        newOne.put(101, arr);
        UpdateBerths.compareResults(oldOne, newOne);
        System.out.print("Test 1: Инилизация, пусто ");
        UpdateBerths.whoToSendToConsole();

        ArrayList<ArrayList<String>> arrOld = new ArrayList<>();
        ArrayList<String> vesselOld = new ArrayList<>();
        vesselOld.add("Nabiru");
        vesselOld.add("22.05.2019");
        arrOld.add(vesselOld);
        oldOne.put(101, arrOld);
        UpdateBerths.compareResults(oldOne, newOne);
        System.out.print("Test 2: Нет изменений ");
        UpdateBerths.whoToSendToConsole();

        UpdateBerths.compareResults(oldOne, newOne);
        System.out.print("Test 3: Нет изменений ");
        UpdateBerths.whoToSendToConsole();

        vessel.set(0, "Mukola");
        ArrayList<String> vessel2 = new ArrayList<>();
        vessel2.add("Kira");
        vessel2.add("22.05.2019");
        arr.add(vessel2);
        UpdateBerths.compareResults(oldOne, newOne);
        System.out.print("Test 4: Nabiru отшвартовано, пришвартованы Nikola and Kira ");
        UpdateBerths.whoToSendToConsole();

    }
}
