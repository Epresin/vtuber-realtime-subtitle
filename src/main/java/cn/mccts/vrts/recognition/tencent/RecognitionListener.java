package cn.mccts.vrts.recognition.tencent;

import cn.mccts.vrts.gui.JsonAnalysis;
import cn.mccts.vrts.gui.MainGui;
import cn.mccts.vrts.gui.TestGUI;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.tencent.asr.model.SpeechRecognitionResponse;
import com.tencent.asr.service.SpeechRecognitionListener;
import com.tencent.core.utils.JsonUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class RecognitionListener extends SpeechRecognitionListener {

    private RecognitionListener() {
    }

    @Getter
    private final static RecognitionListener recognitionListener = new RecognitionListener();

    @Override
    public void onRecognitionResultChange(SpeechRecognitionResponse response) {
//        System.out.println("识别结果:" + JsonUtil.toJson(response));
        log.debug("识别结果:" + JsonUtil.toJson(response));
    }

    @Override
    public void onRecognitionStart(SpeechRecognitionResponse response) {
        log.info("开始识别:" + JsonUtil.toJson(response));

    }

    @Override
    public void onSentenceBegin(SpeechRecognitionResponse response) {
        log.debug("一句话开始:" + JsonUtil.toJson(response));
    }

    @Override
    public void onSentenceEnd(SpeechRecognitionResponse response) {
        log.debug("一句话结束:" + JsonUtil.toJson(response));
    }

    @Override
    public void onRecognitionComplete(SpeechRecognitionResponse response) {
        log.info("识别结束:" + JsonUtil.toJson(response));

    }

    @Override
    public void onFail(SpeechRecognitionResponse response) {
        log.error("错误:" + JsonUtil.toJson(response));


    }

    @Override
    public void onMessage(SpeechRecognitionResponse response) {
        log.info("识别结果:" + JsonUtil.toJson(response));
        TestGUI.repaint(JsonAnalysis.getResult(JsonUtil.toJson(response)));
    }
}
