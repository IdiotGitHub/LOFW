package com.xiaoxu.response;

/**
 * @author Administrator
 */
public class CommontReturnType {
    //表名对应请求的返回处理结果 "success"或"fail"
    private String status;
    //如果status=success，则data内返回前端所需要的json数据
    //如果status=fail，则打他内使用通用的错误码格式
    private Object data;

    //定义一个通用的创建方法
    //如果此创建方法不带status则表明没有错误，如果create方法带有status则直接调用第二个create来创建CommentReturnType
    public static CommontReturnType create(Object result){
        return CommontReturnType.create(result,"success");
    }

    public static CommontReturnType create(Object result, String status) {
        CommontReturnType type = new CommontReturnType();
        type.setData(result);
        type.setStatus(status);
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
