package com.mdl.design.pattern.behavioral.command;

/**
 * Created by meidanlong
 */
public class CloseCourseVideoCommand implements Command {
    private CourseVideo courseVideo;

    public CloseCourseVideoCommand(CourseVideo courseVideo) {
        this.courseVideo = courseVideo;
    }

    @Override
    public void execute() {
        courseVideo.close();
    }
}
