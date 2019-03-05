package CalcTest;

import data.Subscriprions.SubsLauncher;

public class TreadSleepTimeTest {

    public static void main(String[] args){
        for (int i = 0; i <= 24; i++) {
            int time = SubsLauncher.getTime(i, 5, 7);
            System.out.println(i + ":05 = " + (double)time/(1000*60*60));
        }
    }
}
