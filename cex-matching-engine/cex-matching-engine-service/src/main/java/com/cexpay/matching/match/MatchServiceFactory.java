package com.cexpay.matching.match;

import com.cexpay.common.enums.MatchStrategy;

import java.util.HashMap;
import java.util.Map;

public class MatchServiceFactory {

    private static Map<MatchStrategy, MatchService> matchServiceMap = new HashMap<>();


    public static MatchService getMatchServiceMap(MatchStrategy matchStrategy) {
        return matchServiceMap.get(matchStrategy);
    }

    public static void addMatchService(MatchStrategy matchStrategy, MatchService matchService) {
        matchServiceMap.put(matchStrategy, matchService);
    }

}
