package com.agh.givealift.service.implementation;

import com.agh.givealift.model.response.PushNotificationResponse;
import com.agh.givealift.model.response.SubscriptionResponse;
import com.agh.givealift.model.response.WebFCMResponse;
import com.agh.givealift.service.NotificationService;
import com.agh.givealift.service.threads.NotifyBotThread;
import com.agh.givealift.service.threads.NotifyMobileThread;
import com.agh.givealift.service.threads.NotifyOneWebThread;
import com.agh.givealift.service.threads.NotifyWebThread;
import com.stefanik.cod.controller.COD;
import com.stefanik.cod.controller.CODFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    private static final COD cod = CODFactory.get();
    private final ApplicationContext applicationContext;
    private final TaskExecutor taskExecutor;

    @Autowired
    public NotificationServiceImpl(ApplicationContext applicationContext, TaskExecutor taskExecutor) {
        this.applicationContext = applicationContext;
        this.taskExecutor = taskExecutor;
    }


    @Override
    public void notifyBot(List<SubscriptionResponse> subscriptionResponseList) {
        if (!subscriptionResponseList.isEmpty()) {
            taskExecutor.execute(() -> {
                NotifyBotThread notifyThread = applicationContext.getBean(NotifyBotThread.class);
                notifyThread.setSubscriptionResponseList(subscriptionResponseList);
                taskExecutor.execute(notifyThread);
            });
        }
    }

    @Override
    public void notifyWeb(List<PushNotificationResponse> pushNotificationResponses) {


//        if (!pushNotificationResponses.isEmpty()) {
//            taskExecutor.execute(() -> {
//                NotifyWebThread notifyThread = applicationContext.getBean(NotifyWebThread.class);
//                notifyThread.setPushNotificationResponses(pushNotificationResponses);
//                taskExecutor.execute(notifyThread);
//            });
//        }

        for (PushNotificationResponse pnr : pushNotificationResponses) {
            taskExecutor.execute(() -> {
                NotifyOneWebThread notifyThread = applicationContext.getBean(NotifyOneWebThread.class);
                notifyThread.setPushNotificationResponse(new WebFCMResponse(pnr) );
                taskExecutor.execute(notifyThread);
            });
        }
    }

    @Override
    public void notifyMobile(List<PushNotificationResponse> pushNotificationResponses) {
        if (!pushNotificationResponses.isEmpty()) {
            taskExecutor.execute(() -> {
                NotifyMobileThread notifyThread = applicationContext.getBean(NotifyMobileThread.class);
                notifyThread.setPushNotificationResponses(pushNotificationResponses);
                taskExecutor.execute(notifyThread);
            });
        }
    }
}
