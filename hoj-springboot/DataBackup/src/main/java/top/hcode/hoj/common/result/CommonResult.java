package top.hcode.hoj.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public class CommonResult<T> {

    private Integer status; // 状态码

    private T data; // 返回的数据

    private String msg; // 自定义信息

    public CommonResult() {

    }

    /**
     * 成功的结果
     *
     * @param data 返回结果
     * @param msg  返回信息
     */
    public static <T> CommonResult<T> successResponse(T data, String msg) {
        return new CommonResult<>(ResultStatus.SUCCESS.getStatus(), data, msg);
    }

    /**
     * 成功的结果
     *
     * @param data 返回结果
     */
    public static <T> CommonResult<T> successResponse(T data) {
        return new CommonResult<T>(ResultStatus.SUCCESS.getStatus(), data, "success");
    }

    /**
     * 成功的结果
     *
     * @param msg 返回信息
     */
    public static <T> CommonResult<T> successResponse(String msg) {
        return new CommonResult<T>(ResultStatus.SUCCESS.getStatus(), null, msg);
    }

    /**
     * 成功的结果
     */
    public static <T> CommonResult<T> successResponse() {
        return new CommonResult<T>(ResultStatus.SUCCESS.getStatus(), null, "success");
    }

    /**
     * 失败的结果，无异常
     *
     * @param msg 返回信息
     */
    public static <T> CommonResult<T> errorResponse(String msg) {
        return new CommonResult<T>(ResultStatus.FAIL.getStatus(), null, msg);
    }

    public static <T> CommonResult<T> errorResponse(ResultStatus resultStatus) {
        return new CommonResult<T>(resultStatus.getStatus(), null, resultStatus.getDescription());
    }

    public static <T> CommonResult<T> errorResponse(String msg, ResultStatus resultStatus) {
        return new CommonResult<T>(resultStatus.getStatus(), null, msg);
    }

    public static <T> CommonResult<T> errorResponse(String msg, Integer status) {
        return new CommonResult<T>(status, null, msg);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}