package com.tnv.moneymanager.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.tnv.moneymanager.model.Bill;
import com.tnv.moneymanager.model.MenuItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Dao
public interface BillDao {
    @Query("SELECT * FROM bill")
    List<Bill> getAll();

    @Query("SELECT * FROM bill where bid = :id")
    Bill getById(long id);

    @Query("SELECT DISTINCT * FROM bill where date BETWEEN :from AND :to group by date ORDER BY date desc")
    List<Bill> getBillByMonth(Date from, Date to);

    @Query("SELECT sum(money) FROM bill where uid = :id AND date < :current  ORDER BY date(date) desc")
    long getSumPriceByMonthFirst(Date current, long id);

    @Query("SELECT sum(money) FROM bill where uid = :id AND date BETWEEN :from AND :to  ORDER BY date(date) desc")
    long getSumPriceByMonth(Date from, Date to, long id);

    @Query("SELECT sum(money) FROM bill where uid = :id AND date > :current  ORDER BY date(date) desc")
    long getSumPriceByMonthLast(Date current, long id);

    @Query("SELECT DISTINCT CAST(strftime('%d', datetime(date/1000, 'unixepoch')) AS int) AS day FROM bill where uid = :id AND date < :current group by date ORDER BY date desc")
    List<Integer> getBillGroupMonthFirst(Date current, long id);

    @Query("SELECT DISTINCT CAST(strftime('%d', datetime(date/1000, 'unixepoch')) AS int) AS day FROM bill where uid = :id AND date BETWEEN :from AND :to group by date ORDER BY date desc")
    List<Integer> getBillGroupMonth(Date from, Date to, long id);

    @Query("SELECT DISTINCT CAST(strftime('%d', datetime(date/1000, 'unixepoch')) AS int) AS day FROM bill where uid = :id AND date > :current group by date ORDER BY date desc")
    List<Integer> getBillGroupMonthLast(Date current, long id);


    @Query("SELECT * FROM bill where uid = :id AND date  < :current AND CAST(strftime('%d', datetime(date/1000, 'unixepoch')) AS int) = :day ORDER BY date desc")
    List<Bill> getBillByDayFirst(Date current, int day, long id);

    @Query("SELECT * FROM bill where uid = :id AND date BETWEEN :from AND :to AND CAST(strftime('%d', datetime(date/1000, 'unixepoch')) AS int) = :day ORDER BY date desc")
    List<Bill> getBillByDay(Date from, Date to, int day, long id);

    @Query("SELECT * FROM bill where uid = :id AND date  > :current AND CAST(strftime('%d', datetime(date/1000, 'unixepoch')) AS int) = :day ORDER BY date desc")
    List<Bill> getBillByDayLast(Date current, int day, long id);


    @Query("SELECT sum(money) FROM bill where uid = :id AND date < :current AND CAST(strftime('%d', datetime(date/1000, 'unixepoch')) AS int) = :day ORDER BY date desc")
    long getSumPriceBillByDayFirst(Date current, int day, long id);


    @Query("SELECT sum(money) FROM bill where uid = :id AND date BETWEEN :from AND :to AND CAST(strftime('%d', datetime(date/1000, 'unixepoch')) AS int) = :day ORDER BY date desc")
    long getSumPriceBillByDay(Date from, Date to, int day, long id);

    @Query("SELECT sum(money) FROM bill where uid = :id AND date > :current AND CAST(strftime('%d', datetime(date/1000, 'unixepoch')) AS int) = :day ORDER BY date desc")
    long getSumPriceBillByDayLast(Date current, int day, long id);

    //Get bill by month
    @Query("SELECT sum(money) FROM bill where uid = :id AND CAST(strftime('%m', datetime(date/1000, 'unixepoch')) AS int) = :month AND CAST(strftime('%Y', datetime(date/1000, 'unixepoch')) AS int) = :year ORDER BY date desc")
    long getMoneySumByMonth(int month, int year ,long id);
    //Get bill by month
    @Query("SELECT sum(money) FROM bill where uid = :id AND menuId = :menuId AND CAST(strftime('%m', datetime(date/1000, 'unixepoch')) AS int) = :month AND CAST(strftime('%Y', datetime(date/1000, 'unixepoch')) AS int) = :year")
    long getMoneySumMenuByMonth(int month, int year ,long id, int menuId);


    @Insert
    long insert(Bill bill);

}
