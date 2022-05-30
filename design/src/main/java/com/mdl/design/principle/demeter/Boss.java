package com.mdl.design.principle.demeter;


/**
 * Created by meidanlong
 */
public class Boss {

    public void commandCheckNumber(TeamLeader teamLeader){
        teamLeader.checkNumberOfCourses();
    }

}
