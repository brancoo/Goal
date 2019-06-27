package com.example.Models.Competition;

import java.util.ArrayList;
import java.util.List;

public class CompetitionList {
    private static String[] freeTierCompetitions = new String[]{"2001","2017","2021","2003","2002","2015","2019","2014","2016","2013","2000","2018"};
    private List<Competition> competitions;

    public List<Competition> getAvailableCompetitions()
    {
        List<Competition> competitionList = new ArrayList<>();
        for(Competition competition : competitions)
        {
            if(arrayContainsValue(freeTierCompetitions,competition.getId()))
                competitionList.add(competition);
        }
        return competitionList;
    }

    private static boolean arrayContainsValue(String[] arr, String targetValue) {
        for (String s : arr) {
            if (s.equals(targetValue))
                return true;
        }
        return false;
    }

    public CompetitionList(List<Competition> competitions) {
        this.competitions = competitions;
    }

    public List<Competition> getCompetitions() {
        return competitions;
    }
}
