package lab5b;

import java.util.Arrays;

public class CheckerRunnable implements Runnable {

    private final int[] aCount = new int[Application.getStringBuilders().length];
    private final int[] bCount = new int[Application.getStringBuilders().length];

    public void run() {
        Arrays.fill(aCount, 0);
        Arrays.fill(bCount, 0);
        for (int i = 0; i < Application.getStringBuilders().length; i++) {
            countAB(Application.getStringBuilders()[i], i);
        }

        if (checkIfDone()) {
            for (int i = 0; i < Application.getThreads().length; i++) {
                Application.getThreads()[i].interrupt();
                System.out.println(Application.getStringBuilders()[i].toString());
            }
        }
    }

    private void countAB(StringBuilder stringBuilder, int index) {
        for (int i = 0; i < stringBuilder.length(); i++) {
            if (stringBuilder.charAt(i) == 'A') {
                aCount[index]++;
            } else if (stringBuilder.charAt(i) == 'B') {
                bCount[index]++;
            }
        }
    }

    private boolean checkIfDone() {
        int totalACount = 0;
        int totalBCount = 0;
        for (int j : aCount) {
            totalACount += j;
        }
        for (int j : bCount) {
            totalBCount += j;
        }
        for (int i = 0; i < aCount.length; i++) {
            if (totalACount - aCount[i] == totalBCount - bCount[i]) {
                System.out.println("Done, count success with strings with numbers: " + getText(i, aCount.length));
                return true;
            }
        }
        return false;
    }

    private String getText(int excludedIndex, int size) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if (i != excludedIndex) {
                stringBuilder.append(i).append(",");
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
