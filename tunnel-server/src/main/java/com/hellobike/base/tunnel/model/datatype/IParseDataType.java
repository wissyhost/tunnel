package com.hellobike.base.tunnel.model.datatype;

/**
 * @author wissy 2020-01-22
 */
public interface IParseDataType {
    /**
     * 将PGDataType解析成Object 类型
     *
     * @param pgDataType
     * @return
     */
    public Object parse(PGDataType pgDataType, String value);
}
