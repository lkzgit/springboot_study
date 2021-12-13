package com.demo.consumer.result;


public class StatusCode {

    /**
     * 请求成功
     */
    public static final String SUCCESSCODE = "000000";
    public static final String SUCCESSCODEMSG = "请求成功";
    public static final String UPDATEPASSWORD = "请修改初始密码";
    public static final String NOROLRMENU = "未分配菜单权限";
    public static final String ISUSEING = "此菜单正在使用中";
    public static final String MENUERROR = "子菜单不能作为根节点";
    public static final String MENUMSG = "名称重复";
    public static final String REPAATMSG = "重复提交";
    public static final String REPAATMSGEXCEPT = "验证重复提交时出现未知异常";

    /**
     * 系统执行出错
     */
    public static final String SERVICEERROR = "99999";
    public static final String SERVICEERRORMSG = "操作失败";
    public static final String EXCLEERRORMSG = "excle导入添加失败";


    /**
     * 重复提交
     */
    public static final String REPEATSUBMISSION = "B0100";
    public static final String REPEATSUBMISSIONMSG = "不允许重复提交，请稍后再试";

    /**
     * 用户不存在
     */
    public static final String NOUSERCOODE = "B0000";
    public static final String NOUSERMSG = "用户不存在";
    public static final String PAWWMSG = "密码错误";
    public static final String ODLPAWWMSG = "输入的旧密码不一致";
    public static final String USERNAME = "用户名已存在";
    public static final String PASSWORDDATE = "密码不能为纯数字,且长度大于6位";
    /**
     * 访问
     */
    public static final String TOKENDEATHCOOD = "406";
    public static final String TOKENDEATHMSG = "token过期";
    public static final String UNAUTHORIZEDCOOD = "405";
    public static final String UNAUTHORIZED = "没有权限(Unauthorized)";
    public static final String SINGTOKENCOOD = "407";
    public static final String SINGTOKENMSG = "token解密失败";


    public static final String RESULTNULLCOOD = "D11111";
    public static final String RESULTNULLCOODMSG = "没有找到符合条件的数据！";

    public static final String NODATACOOD = "D00110";
    public static final String NODATACOODMSG = "传入参数缺失！";
    public static final String NOFORMNAME = "缺少表名！";

    /**
     * 核对参数
     */
    public static final String CHECKPARMENTCOOD = "B0101";
    public static final String CHECKPARMENTMSG = "请核对参数";
    public static final String PARAMTERERR = "请填写英文字符";



    public static final String FAILCODE = "111111";
    public static final String FAILCODEMSG = "请求失败";

    public static final String RESULTNULL = "001100";
    public static final String RESULTNULLMSG = "查询结果为空";

    public static final String PARACODE = "000111";
    public static final String PARACODEMSG = "请求参数缺失";

    public static final String GROUPNULLCODE = "111000";
    public static final String GROUPNULLCODEMSG = "你还没有MDT团队";

    public static final String CON_NULL_CODE = "100001";
    public static final String CON_NULL_CODE_MSG = "您当前没有参加的会诊";

    public static final String CONSULTATION = "consultation:select_con";
    public static final String DELALLKEY = "consultation:all_key";


    public static final String COMPLETE = "已完成";
    public static final String INCOMPLETE = "未填写结论";
    public static final String NOSPEAK = "未发言";

    public static final Integer ZERO_RE = -1;
    public static final Integer ZERO = 0;
    public static final Integer ONE = 1;
    public static final Integer TWO = 2;
    public static final Integer THREE = 3;
    public static final Integer TEN = 10;


    /**
     * 导入公共字段
     */
    public static final String ExcelYes = "是";
    public static final String ExcelNO = "否";

    public static final String HASCHILD = "CH0001";
    public static final String HASCHILD_MSG = "需要先删除子节点！";

}
