package me.bzcoder.rxjava2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author : BaoZhou
 * @date : 2019/8/12 15:29
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ThreadInfo";
    private Button btStartRxjava;
    private TextView tvResult;
    private Button btStartRxjava2;
    private Button btStartRxjava3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tvResult = findViewById(R.id.tv_result);
        btStartRxjava = findViewById(R.id.bt_start_rxjava);
        btStartRxjava2 = findViewById(R.id.bt_start_rxjava2);
        btStartRxjava3 = findViewById(R.id.bt_start_rxjava3);

        btStartRxjava.setOnClickListener(this);
        btStartRxjava2.setOnClickListener(this);
        btStartRxjava3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start_rxjava:
                //test1();
                test2();
                break;
            case R.id.bt_start_rxjava2:
                flatmap();
                break;
            case R.id.bt_start_rxjava3:
                concatmap();
                break;
            default:
                break;

        }
    }

    private void test3() {
        Observable<Object> objectObservable = Observable.create(new ObservableOnSubscribe<List<String>>() {

            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                emitter.onNext(Arrays.asList("123", "456", "789"));
            }
        }).flatMap(new Function<List<String>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(List<String> strings) throws Exception {
                return null;
            }
        });
    }

    private void test2() {
        Observable<String> map = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("4");
            }
        }).map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                return s + "号";
            }
        });
        map.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("提前预备");
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });
    }

    private void test1() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("123");
                emitter.onNext("456");
                emitter.onNext("789");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        tvResult.setText(tvResult.getText().toString() + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void test0() {
        new Thread() {
            @Override
            public void run() {
                Log.d(TAG, "Thread run() 所在线程为 :" + Thread.currentThread().getName());
                Observable
                        .create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(ObservableEmitter<String> emitter) {
                                Log.d(TAG, "Observable subscribe() 所在线程为 :" + Thread.currentThread().getName());
                                emitter.onNext("文章1");
                                emitter.onNext("文章2");
                                emitter.onComplete();
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.d(TAG, "Observer onSubscribe() 所在线程为 :" + Thread.currentThread().getName());
                            }

                            @Override
                            public void onNext(String s) {
                                Log.d(TAG, "Observer onNext() 所在线程为 :" + Thread.currentThread().getName());
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "Observer onError() 所在线程为 :" + Thread.currentThread().getName());
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "Observer onComplete() 所在线程为 :" + Thread.currentThread().getName());
                            }
                        });
            }
        }.start();

    }

    private void flatmap() {
        Observable.fromArray(1,2,3,4,5)
                .flatMap(new Function<Integer, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(@NonNull Integer integer) throws Exception {

                        int delay = 0;
                        if(integer == 3){
                            delay = 500;//延迟500ms发射
                        }
                        return Observable.just(integer *10).delay(delay, TimeUnit.MILLISECONDS);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.e(TAG,"accept:"+integer);
                    }
                });
    }

    private void concatmap() {
        Observable.fromArray(1,2,3,4,5)
                .concatMap(new Function<Integer, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(@NonNull Integer integer) throws Exception {

                        int delay = 0;
                        if(integer == 3){
                            delay = 500;//延迟500ms发射
                        }
                        return Observable.just(integer *10).delay(delay, TimeUnit.MILLISECONDS);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.e(TAG,"accept:"+integer);
                    }
                });
    }
}
