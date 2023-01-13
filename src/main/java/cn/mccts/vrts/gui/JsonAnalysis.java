package cn.mccts.vrts.gui;

import com.alibaba.fastjson2.JSONObject;

public class JsonAnalysis {

    public static String getResult(String string){
        if (string!=null){
            JSONObject jsonObject=JSONObject.parseObject(string);
            String resultString=jsonObject.getString("result");
            if (resultString!=null){
                JSONObject resultJson=JSONObject.parseObject(resultString);
                String text=resultJson.getString("voice_text_str");
                if (text!=null){
                    return text;
                }
            }
        }
        return "";
    }
}
