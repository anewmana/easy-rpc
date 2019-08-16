package deserialize;

import com.alibaba.fastjson.JSON;

public class JsonDeserializer implements Deserializer {

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> cls) {
        if(bytes == null || bytes.length == 0){
            return null;
        }
        return JSON.parseObject(bytes, cls);
    }
}
