package com.mdl.springboot.aigc.service.openai;

import io.netty.handler.codec.http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.HttpResponseStatus;
import org.asynchttpclient.handler.StreamedAsyncHandler;
import org.reactivestreams.Publisher;

/**
 * 事件流处理器
 *
 * @Description 事件流处理器
 * @Author Mr.dan
 * @Date 2023-05-04 10:15
 */
@Slf4j
public class ChatGptStreamedHandler implements StreamedAsyncHandler {
    @Override
    public State onStream(Publisher publisher) {
        publisher.subscribe(new ChatGptSubscriber());
        log.info("》》》》》 onStream");
        return State.CONTINUE;
    }

    @Override
    public State onStatusReceived(HttpResponseStatus responseStatus) throws Exception {
        log.info("》》》》》onStatusReceived");
        return responseStatus.getStatusCode() == 200 ? State.CONTINUE : State.ABORT;
    }

    @Override
    public State onHeadersReceived(HttpHeaders headers) throws Exception {
        log.info("》》》》》 头信息处理");
        return State.CONTINUE;
    }

    @Override
    public State onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
        log.info("》》》》》 onBodyPartReceived");
        return State.CONTINUE;
    }

    @Override
    public void onThrowable(Throwable t) {
        log.error("发生异常", t);
    }

    @Override
    public Object onCompleted() throws Exception {
        log.info("》》》》》 完成");
        return State.ABORT;
    }
}

