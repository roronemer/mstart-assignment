package com.mstart.task.dao;

import com.mstart.task.model.RemoteConvert;
import com.mstart.task.network.HttpHelper;
import com.mstart.task.network.MyCall;

import retrofit2.http.GET;
import retrofit2.http.Query;

public class HijriRemoteDao {
    private static  HijriRemoteDao instance;
    private DashboardHijri dashboardHijri;

    public HijriRemoteDao() {
        dashboardHijri = HttpHelper.getInstance().create(DashboardHijri.class);
    }


    public  static  synchronized  HijriRemoteDao getInstance(){
        if(instance ==null)
            instance=new HijriRemoteDao();
        return instance;
    }
    public MyCall<RemoteConvert> getData(String data ) {
        return dashboardHijri.getData(data);
    }


    private interface DashboardHijri {
        @GET("gToH")
        public MyCall<RemoteConvert> getData(@Query("date") String date);
    }
}
