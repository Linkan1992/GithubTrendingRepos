package com.linkan.githubtrendingrepos.data.local.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.linkan.githubtrendingrepos.data.model.db.TrendingRepo;


@Database(entities = {TrendingRepo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{

    public abstract TrendingRepoDao repoDao();

}
