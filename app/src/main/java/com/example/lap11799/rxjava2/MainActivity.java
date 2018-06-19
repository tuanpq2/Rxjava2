package com.example.lap11799.rxjava2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.ResourceSubscriber;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        testSimpleObservable();
//        testSimpleSingle();
//        testSimpleFlowable();
    }
    @Override
    protected void onResume() {

        super.onResume();
        testSimpleFlowable();
    }
    public void testSimpleObservable(){
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
                                                               @Override
                                                               public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                                                                   //Use onNext to emit each item in the stream//
                                                                   e.onNext(1);
                                                                   e.onNext(2);
                                                                   e.onNext(3);
                                                                   e.onNext(4);

                                                                   //Once the Observable has emitted all items in the sequence, call onComplete//
                                                                   e.onComplete();
                                                               }
                                                           }
        );
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Integer value) {
                Log.e(TAG, "onNext: " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: All Done!");
            }
        };
        //Create our subscription//
        observable.subscribe(observer);
    }
    public void testSimpleSingle(){
        Single<Integer> single = Single.create(new SingleOnSubscribe<Integer>() {
            @Override
            public void subscribe(SingleEmitter<Integer> e) throws Exception {
                e.onSuccess(100);
            }
        });
        SingleObserver<Integer> observer = new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe: ");
            }

            @Override
            public void onSuccess(Integer integer) {
                Log.e(TAG, "onSuccess: " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }
        };
        single.subscribe(observer);
    }
    public void testSimpleFlowable(){
        Subscriber<Integer> subscriber1 = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
                Log.e(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(Throwable t) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");
            }
        };
        ResourceSubscriber<Integer> subscriber = new ResourceSubscriber<Integer>() {
            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(Throwable t) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");
            }
        };
        Flowable.range(1, 10).subscribe(subscriber1);
//        subscriber.dispose();
    }
}
