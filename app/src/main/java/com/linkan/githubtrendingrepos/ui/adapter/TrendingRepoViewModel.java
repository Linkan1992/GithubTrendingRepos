package com.linkan.githubtrendingrepos.ui.adapter;

import androidx.databinding.ObservableField;
import android.graphics.Color;

import com.linkan.githubtrendingrepos.data.model.db.TrendingRepo;


public class TrendingRepoViewModel {

    private TrendingRepoNavigator repoNavigator;

    private TrendingRepo trendingRepo;

    public final ObservableField<String> author;

    public final ObservableField<String> name;

    public final ObservableField<String> avatar;

    public final ObservableField<String> description;

    public final ObservableField<String> language;

    public final ObservableField<Integer> languageColor;

    public final ObservableField<String> stars;

    public final ObservableField<String> forks;

    public final ObservableField<String> currentPeriodStars;


    public TrendingRepoViewModel(TrendingRepoNavigator repoNavigator, TrendingRepo trendingRepo) {

        this.repoNavigator = repoNavigator;

        this.trendingRepo = trendingRepo;

        author = new ObservableField<>(trendingRepo.getRepo_author());

        name = new ObservableField<>(trendingRepo.getName());

        avatar = new ObservableField<>(trendingRepo.getAvatar_url());

        description = new ObservableField<>(trendingRepo.getRepo_description());

        language = new ObservableField<>(trendingRepo.getLanguage());

        languageColor = new ObservableField<>(Color.parseColor(trendingRepo.getLanguageColor() == null ? "#25282B" : trendingRepo.getLanguageColor()));

        stars = new ObservableField<>(String.valueOf(trendingRepo.getStars()));

        forks = new ObservableField<>(String.valueOf(trendingRepo.getForks()));

        currentPeriodStars = new ObservableField<>(String.valueOf(trendingRepo.getCurrentPeriodStars()));

    }


    public void onItemClick() {
        repoNavigator.expandCollapseView();
    }


}
