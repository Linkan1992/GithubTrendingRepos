package com.linkan.githubtrendingrepos.utils;


import com.linkan.githubtrendingrepos.data.model.api.TrendingResponse;
import com.linkan.githubtrendingrepos.data.model.db.TrendingRepo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class UtilFunction {

    private UtilFunction() {
        // This utility class is not publicly instantiable
    }


    public static List<TrendingRepo> getTrendingRepoList(List<TrendingResponse> repoList) {

        List<TrendingRepo> trendingRepoList = new ArrayList<>();

        for (TrendingResponse response : repoList) {

            TrendingRepo trendingRepo = new TrendingRepo();

            trendingRepo.setRepo_author(response.getAuthor());
            trendingRepo.setName(response.getName());
            trendingRepo.setAvatar_url(response.getAvatar());
            trendingRepo.setRepo_description(response.getDescription());
            trendingRepo.setLanguage(response.getLanguage());
            trendingRepo.setLanguageColor(response.getLanguageColor());
            trendingRepo.setStars(response.getStars());
            trendingRepo.setForks(response.getForks());
            trendingRepo.setCurrentPeriodStars(response.getCurrentPeriodStars());

            trendingRepoList.add(trendingRepo);

        }

        // sorting the list by stars in descending order
       // Collections.sort(trendingRepoList);

        return trendingRepoList;

    }

}