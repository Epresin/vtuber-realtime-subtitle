package cn.mccts.vrts.recognition.tencent;

import cn.mccts.vrts.gui.MainGui;
import cn.mccts.vrts.main.Main;
import cn.mccts.vrts.record.Recording;
import com.tencent.SpeechClient;
import com.tencent.asr.model.SpeechRecognitionRequest;
import com.tencent.asr.service.SpeechRecognizer;

import javax.sound.sampled.LineUnavailableException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Recognition implements Runnable {

    public Recognition() throws IOException {
    }

    private SpeechClient getSpeechClient() throws IOException {
        //从配置文件读取密钥（可自行修改）
        Properties props = new Properties();
        //从配置文件读取密钥
        props.load(new FileInputStream("config.properties"));
        String appId = props.getProperty("appId");
        String secretId = props.getProperty("secretId");
        String secretKey = props.getProperty("secretKey");
        return SpeechClient.newInstance(appId, secretId, secretKey);
    }

    private SpeechRecognizer getSpeechRecognizer() throws IOException {
        SpeechRecognitionRequest request = SpeechRecognitionRequest.initialize();
        request.setEngineModelType("16k_zh"); //指定模型类型
        request.setVoiceFormat(1);  //指定音频格式
        SpeechClient speechClient = getSpeechClient();
        return speechClient.newSpeechRecognizer(request, RecognitionListener.getRecognitionListener());
    }


    @Override
    public void run() {
        SpeechRecognizer speechWsRecognizer = null;
        try {
            speechWsRecognizer = getSpeechRecognizer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        speechWsRecognizer.start();
        try {
            Recording.start();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        while (Main.status) {
            speechWsRecognizer.write(Recording.getByteArray());
        }
        Recording.stop();
        speechWsRecognizer.stop();
    }

}