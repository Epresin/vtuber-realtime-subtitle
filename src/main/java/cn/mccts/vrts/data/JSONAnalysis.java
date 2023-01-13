package cn.mccts.vrts.data;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

public class JSONAnalysis {
    public static String getResult(String string){
        if (string!=null){
            JSONObject jsonObject= JSONUtil.parseObj(string);
            String resultString=jsonObject.getStr("result");
            if (resultString!=null){
                JSONObject resultJson=JSONUtil.parseObj(resultString);
                String text=resultJson.getStr("voice_text_str");
                if (text!=null){
                    return text;
                }
            }
        }
        return "";
    }
}
