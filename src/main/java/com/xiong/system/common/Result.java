package com.xiong.system.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xsy
 * @date 2022/9/28
 * description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    //响应状态码 200表示成功 其他表示失败
    private Integer code;
    //提示信息
    private String msg;

    private T data; //数据

    private Map<String,Object> map=new HashMap<>();

    public static <T> Result<T> success( T data){
        Result<T> result = new Result<T>();
        result.setCode(200);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error( String msg){
        Result<T> result = new Result<T>();
        result.setCode(300);
        result.setMsg(msg);
        return result;
    }

    public Result<T> add(String key,Object data){
        this.map.put(key,data);
        return this;
    }

    public static <T> Result<T> ok(){
        Result<T> result = new Result<>();
        result.setCode(200);
        return result;
    }
}
