package com.willowtreeapps.namegame.util;

import com.willowtreeapps.namegame.model.Profile;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NameGameUtil {

    private static final Random random = new Random();
    private static final ListRandomizer listRandomizer = new ListRandomizer(random);
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public static List<Profile> getRandomProfiles(List<Profile> profiles) {
        List<Profile> filteredGroup = filterHeadShots(profiles);
        return listRandomizer.pickN(filteredGroup, 5);
    }

    public static List<Profile> getSpecificProfiles(List<Profile> profiles, String name) {
        List<Profile> filteredGroup = filterHeadShots(profiles);
        List<Profile> newList = new ArrayList<>();
        for (Profile person : filteredGroup) {
            if (person.getFirstName().startsWith(name)) {
                newList.add(person);
            }
        }

        return listRandomizer.pickN(newList, 5);
    }

    private static List<Profile> filterHeadShots(List<Profile> people) {
        List<Profile> result = new ArrayList<>();
        for (Profile person : people) {
            if (person.getHeadshot().getUrl() != null) {
                result.add(person);
            }
        }
        return result;
    }

    public static String displayRound(int round, int roundLimit) {
        return String.valueOf(round) + "/" + String.valueOf(roundLimit);
    }

    public static int getRandomRound(int roundLimit) {
        final int min = 2;
        return random.nextInt((roundLimit - min) + 1) + min;
    }

    public static int getRandomPosition(List<Profile> list) {
        return random.nextInt(list.size());
    }

    public static String formatDoubleToString(double value) {
        return decimalFormat.format(value);
    }

}
