package com.mdl.design.pattern.behavioral.mediator;

import java.util.Date;

/**
 * Created by geely
 */
public class StudyGroup {

    public static void showMessage(User user, String message){
        System.out.println(new Date().toString() + " [" + user.getName() + "] : " + message);
    }
}
