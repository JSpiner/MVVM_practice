package net.jspiner.mvvm.ui.main;

import android.text.TextUtils;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public final class MainViewModel {

    private BehaviorSubject<CharSequence> idCharSequence = BehaviorSubject.createDefault("");
    private BehaviorSubject<CharSequence> pwCharSequence = BehaviorSubject.createDefault("");
    private BehaviorSubject<Boolean> loginButtonEnable = BehaviorSubject.createDefault(false);
    private PublishSubject<Boolean> loginResult = PublishSubject.create();

    public MainViewModel() {
        Observable.combineLatest(idCharSequence, pwCharSequence, this::isValidInput)
                .subscribe(loginButtonEnable::onNext);
    }

    private Boolean isValidInput(CharSequence id, CharSequence pw) {
        return !TextUtils.isEmpty(id) && !TextUtils.isEmpty(pw);
    }

    public void onInputIdChanged(CharSequence id) {
        idCharSequence.onNext(id);
    }

    public void onInputPwChanged(CharSequence pw) {
        pwCharSequence.onNext(pw);
    }

    public void onLoginButtonClicked() {
        CharSequence id = idCharSequence.getValue();
        CharSequence pw = pwCharSequence.getValue();

        loginResult.onNext(id.equals("test") && pw.equals("1234"));
    }

    public Observable<Boolean> getLoginButtonEnabled() {
        return loginButtonEnable;
    }

    public Observable<Boolean> getLoginResult() {
        return loginResult;
    }

}
