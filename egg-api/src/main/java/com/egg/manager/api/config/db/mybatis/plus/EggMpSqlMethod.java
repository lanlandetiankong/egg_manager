package com.egg.manager.api.config.db.mybatis.plus;

public enum EggMpSqlMethod {
    /**
     * 根据id伪删除
     */
    FAKE_DELETE_BY_ID("fakeDeleteById", "根据id伪删除", "<script>\nupdate %s set %s = %d where %s = #{fid}\n</script>"),

    ;

    private final String method;
    private final String desc;
    private final String sql;

    EggMpSqlMethod(String method, String desc, String sql) {
        this.method = method;
        this.desc = desc;
        this.sql = sql;
    }

    public String getMethod() {
        return this.method;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getSql() {
        return this.sql;
    }
}
