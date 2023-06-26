package com.mdl.springboot.aigc.service.openai;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * 订阅者
 *
 * @Description 订阅者
 * @Author Mr.dan
 * @Date 2023-05-04 13:18
 */
@Slf4j
public class ChatGptSubscriber implements Subscriber {
    @Override
    public void onSubscribe(Subscription s) {
        log.info(">>>>> onSubscribe  {}", JSON.toJSONString(s));
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(Object o) {
        //这里就是监听到的响应
        // {
        //  "bodyByteBuffer": {
        //    "array": "eyJlcnJvcl9jb2RlIjoxMDAsImVycm9yX21zZyI6IkludmFsaWQgcGFyYW1ldGVyIn0=",
        //    "limit": 50,
        //    "position": 0
        //  },
        //  "bodyPartBytes": "eyJlcnJvcl9jb2RlIjoxMDAsImVycm9yX21zZyI6IkludmFsaWQgcGFyYW1ldGVyIn0=",
        //  "last": false
        //}
        byte[] bodyPartBytes = JSON.parseObject(JSON.toJSONString(o)).getBytes("bodyPartBytes");
        log.info(">>>>> onNext  {}", new String(bodyPartBytes) );
    }

    @Override
    public void onError(Throwable t) {
        //这里就是监听到的响应
        log.info(">>>>> onError  {}", JSON.toJSONString(t));
    }

    @Override
    public void onComplete() {
        log.info(">>>>> onComplete ");
    }
}


