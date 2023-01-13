package cn.mccts.vrts.record;

import lombok.extern.slf4j.Slf4j;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

@Slf4j
public class Recording {
    private static final float RATE = 16000;
    //编码格式PCM
    private static final AudioFormat.Encoding ENCODING = AudioFormat.Encoding.PCM_SIGNED;
    //帧大小 16
    private static final int SAMPLE_SIZE = 16;
    //是否大端
    private static final boolean BIG_ENDIAN = false;
    //通道数
    private static final int CHANNELS = 1;  //单通道
    private static AudioFormat audioFormat = new AudioFormat(ENCODING, RATE, SAMPLE_SIZE, CHANNELS, (SAMPLE_SIZE / 8) * CHANNELS,
            RATE, BIG_ENDIAN);
    private static TargetDataLine targetDataLine;

    static {
        try {
            targetDataLine = AudioSystem.getTargetDataLine(audioFormat);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    //启动麦克风
    public static void start() throws LineUnavailableException {
        targetDataLine.open();
        targetDataLine.start();
    }
    //关闭麦克风

    public static void stop() {
        targetDataLine.stop();
    }

    //传一个字符数组过去，为前40ms录制的1280byte数据
    public static byte[] getByteArray() {
        byte[] bytes = new byte[1280];
        targetDataLine.read(bytes, 0, bytes.length);
        return bytes;
    }

}
