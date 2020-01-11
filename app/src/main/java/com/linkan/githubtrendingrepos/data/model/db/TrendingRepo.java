package com.linkan.githubtrendingrepos.data.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Comparator;


// this class must be the POJO class not a Bean Class
@Entity(tableName = "trending_repo")
public class TrendingRepo implements Comparable<TrendingRepo>, Comparator<TrendingRepo> {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "repo_id")
  public int repo_id;

  @ColumnInfo(name = "author")
  public String repo_author;

  @ColumnInfo(name = "name")
  public String name;

  @ColumnInfo(name = "avatar_url")
  public String avatar_url;

  @ColumnInfo(name = "repo_description")
  public String repo_description;

  @ColumnInfo(name = "language")
  public String language;

  @ColumnInfo(name = "languageColor")

  public String languageColor;

  @ColumnInfo(name = "stars")
  public Long stars;

  @ColumnInfo(name = "forks")
  public Long forks;

  @ColumnInfo(name = "currentPeriodStars")
  public Long currentPeriodStars;


  public int getRepo_id() {
    return repo_id;
  }

  public void setRepo_id(int repo_id) {
    this.repo_id = repo_id;
  }

  public String getRepo_author() {
    return repo_author;
  }

  public void setRepo_author(String repo_author) {
    this.repo_author = repo_author;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAvatar_url() {
    return avatar_url;
  }

  public void setAvatar_url(String avatar_url) {
    this.avatar_url = avatar_url;
  }

  public String getRepo_description() {
    return repo_description;
  }

  public void setRepo_description(String repo_description) {
    this.repo_description = repo_description;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getLanguageColor() {
    return languageColor;
  }

  public void setLanguageColor(String languageColor) {
    this.languageColor = languageColor;
  }

  public Long getStars() {
    return stars;
  }

  public void setStars(Long stars) {
    this.stars = stars;
  }

  public Long getForks() {
    return forks;
  }

  public void setForks(Long forks) {
    this.forks = forks;
  }


  public Long getCurrentPeriodStars() {
    return currentPeriodStars;
  }

  public void setCurrentPeriodStars(Long currentPeriodStars) {
    this.currentPeriodStars = currentPeriodStars;
  }


  /**
   * Sorting in Descending Order by stars
   * @param trendingRepo
   * @return
   */
  @Override
  public int compareTo(TrendingRepo trendingRepo) {
    return (this.stars > trendingRepo.stars ? -1 :
      (this.stars.equals(trendingRepo.stars) ? 0 : 1));

  }


  /**
   * Sorting in Ascending Order by repoName
   * @return
   */
  @Override
  public int compare(TrendingRepo repo1, TrendingRepo repo2) {
    return repo1.name.compareTo(repo2.name);
  }





}
