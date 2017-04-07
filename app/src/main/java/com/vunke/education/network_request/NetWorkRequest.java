package com.vunke.education.network_request;

/**
 * Created by zhuxi on 2017/3/29.
 */
public class NetWorkRequest {
    /**
     * 基础地址
     */
    public static final String BaseUrl = "http://124.232.136.239:8089/ed";

    /**
     * 首页地址
     */
    public static final String HOME_DATE = "/intf/index.shtml";

    /**
     * 根据info_id查询单个详细信息
     */
    public static final String  FIND_BYI_ID_INFO = "/intf/findByiIdInfo.shtml";
    /**
     * 收费内容点击量接口
     */
    public static final String SETTLEMENT = "/intf/Settlement.shtml";
    /**
     * 排行榜查询接口
     */
    public static final String SELECT_RANKINGS = "/kjmgr/intf/selectAllRanking.shtml";

    /**
     *推荐位点击量接口
     */
    public static final String STATISTICES_RBIT = "/kjmgr/intf/StatisticsRbit.shtml";

    /**
     *相关视频查询接口
     */
    public static final String INFO_VIDEO_QUERY = "/kjmgr/intf/InfoVideoQuery.shtml";
}
