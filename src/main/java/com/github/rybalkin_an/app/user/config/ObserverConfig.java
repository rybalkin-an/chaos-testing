package com.github.rybalkin_an.app.user.config;

import com.github.rybalkin_an.app.user.service.impl.EmailObserver;
import com.github.rybalkin_an.app.user.service.impl.OtpNotificationSubject;
import com.github.rybalkin_an.app.user.service.impl.SMSObserver;
import com.github.rybalkin_an.app.user.service.impl.UserNotificationSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObserverConfig {

    @Autowired
    public ObserverConfig(UserNotificationSubject userNotificationSubject,
                          OtpNotificationSubject otpNotificationSubject,
                          EmailObserver emailObserver,
                          SMSObserver smsObserver) {
        userNotificationSubject.registerObserver(emailObserver);

        otpNotificationSubject.registerObserver(emailObserver);
        otpNotificationSubject.registerObserver(smsObserver);
    }
}
