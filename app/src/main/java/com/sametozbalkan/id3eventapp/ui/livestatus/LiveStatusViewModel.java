package com.sametozbalkan.id3eventapp.ui.livestatus;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sametozbalkan.id3eventapp.data.repository.LiveStatusRepository;

import java.util.List;

public class LiveStatusViewModel extends ViewModel {

    private final LiveStatusRepository repository =
            new LiveStatusRepository();

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable ticker;

    private final MutableLiveData<String> statusText = new MutableLiveData<>();
    private final MutableLiveData<Integer> countdown = new MutableLiveData<>();

    private int currentStatusIndex = 0;
    private int secondsLeft = 30;

    private final List<String> statusMessages;

    public LiveStatusViewModel() {
        statusMessages = repository.getDefaultStatuses();
        start();
    }

    public LiveData<String> getStatusText() {
        return statusText;
    }

    public LiveData<Integer> getCountdown() {
        return countdown;
    }

    public void start() {
        stop();

        secondsLeft = 30;
        emit();

        ticker = new Runnable() {
            @Override
            public void run() {
                secondsLeft--;

                if (secondsLeft <= 0) {
                    advanceStatus();
                    secondsLeft = 30;
                }

                emit();
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(ticker, 1000);
    }

    public void refreshNow() {
        advanceStatus();
        secondsLeft = 30;
        emit();
    }

    private void advanceStatus() {
        if (currentStatusIndex < statusMessages.size() - 1) {
            currentStatusIndex++;
        }
    }

    private void emit() {
        statusText.setValue(statusMessages.get(currentStatusIndex));
        countdown.setValue(secondsLeft);
    }

    public void stop() {
        if (ticker != null) {
            handler.removeCallbacks(ticker);
            ticker = null;
        }
    }

    @Override
    protected void onCleared() {
        stop();
    }
}
