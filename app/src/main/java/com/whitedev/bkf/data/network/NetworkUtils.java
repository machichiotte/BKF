package com.whitedev.bkf.data.network;

import android.util.Log;
import com.whitedev.bkf.data.network.pojo.TableList;
import com.whitedev.bkf.model.ServiceResponse;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String BASE_URL = "http://api.bkf.fr/api/";

    private static Retrofit getRetrofit() {
        return new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory
                .create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
    }

    public static Disposable getDataFromService(String token,
                                                DisposableObserver<ServiceResponse> observer) {
        Log.d(LOG_TAG, "Getting data from the server");
        try {
            RestApi service = getRetrofit().create(RestApi.class);

            Observable<ServiceResponse> observable = service.getListColumnAtelierObs(token);
            return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread
                    ()).subscribeWith(observer);

        } catch (Exception e) {
            Log.d(LOG_TAG, "Getting data process has been failed. ", e);
        }
        return null;
    }

    public static List<TableList> convertToTableList(ServiceResponse serviceResponse) {
        List<TableList> users = new ArrayList<>();

        Log.d(LOG_TAG, "Converting the response.");
        try {
            users.addAll(Objects.requireNonNull(serviceResponse.getList()));
        } catch (Exception e) {
            Log.d(LOG_TAG, "Converting the response process has been failed. ", e);
        }

        return users;
    }

    private static Date getDate(String stringData) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        return format.parse(stringData);
    }


}
