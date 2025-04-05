package com.rith.notetaker2.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rith.notetaker2.model.TokenAdvanceModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CustomViewModel extends ViewModel {
    Context context;
    List<String> listOfString = new ArrayList<>(Arrays.asList("hello","key","api"));
    MutableLiveData<String> apiData = new MutableLiveData<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    MutableLiveData<TokenAdvanceModel> tokenAdvanceMutableLiveData = new MutableLiveData<>();
    List<TokenAdvanceModel> tokenModelList = new ArrayList<>(Arrays.asList(new TokenAdvanceModel(10,"h"),new TokenAdvanceModel(20,"l")));

    public CustomViewModel(Context context){
        this.context = context;
    }
    void getListOfToken(){
        Disposable disposable = Observable.fromIterable(listOfString)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(i -> apiData.postValue(i))
                .filter(i ->{
                    return i.equals("api");
                })
                .map(i->{
                    return tokenModelList.stream().filter(j->j.getTokenSymbol().equals(i)).findFirst().orElse(new TokenAdvanceModel());
                })
                .subscribe(
                        success->{
                            tokenAdvanceMutableLiveData.postValue(success);
                        },err->{
                            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                        }
                );
        compositeDisposable.add(disposable);
    }

    void getListOfHey(){
        Observable.fromIterable(listOfString)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .filter(i-> i.equals("Hey"))
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(String s) {
                        apiData.postValue(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("err",e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Logger.getLogger("hello");
                    }
                });
    }
}
