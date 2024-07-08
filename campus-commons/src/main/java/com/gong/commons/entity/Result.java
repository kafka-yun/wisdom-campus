package com.gong.commons.entity;

import com.gong.commons.enums.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Result<T> {

    public Integer code;

    public String massage;

    public T data;

    public static Result success(){
        Result result = new Result();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMassage(ResultCodeEnum.SUCCESS.getDesc());
        return result;
    }
    public static <T>Result success(T data){
        Result result = new Result();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMassage(ResultCodeEnum.SUCCESS.getDesc());
        result.setData(data);
        return result;
    }
    public static Result fail(){
        Result result = new Result();
        result.setCode(ResultCodeEnum.FAIL.getCode());
        result.setMassage(ResultCodeEnum.FAIL.getDesc());
        return result;
    }
    public static <T>Result fail(T data){
        Result result = new Result();
        result.setCode(ResultCodeEnum.FAIL.getCode());
        result.setMassage(ResultCodeEnum.FAIL.getDesc());
        result.setData(data);
        return result;
    }

}
