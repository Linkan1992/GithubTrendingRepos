package com.linkan.githubtrendingrepos.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.linkan.githubtrendingrepos.data.model.db.TrendingRepo;

import java.util.List;


@Dao
public interface TrendingRepoDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertRepo(TrendingRepo trendingRepo);

  @Insert
  void insertMultipleListRepo(List<TrendingRepo> repoList);

  @Delete
  void deleteRepo(TrendingRepo trendingRepo);

  @Query("DELETE FROM trending_repo")
  void deleteRepoRecord();

  @Query("SELECT * FROM trending_repo")
  List<TrendingRepo> loadAllRepo();

}
