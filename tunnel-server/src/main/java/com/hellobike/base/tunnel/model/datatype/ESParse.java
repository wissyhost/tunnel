package com.hellobike.base.tunnel.model.datatype;

import com.hellobike.base.tunnel.TunnelServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;

public class ESParse implements IParseDataType {
    private static final Logger         /**/ log = LoggerFactory.getLogger(ESParse.class);
    static SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSX");
    static SimpleDateFormat s3 = new SimpleDateFormat("yyyy-MM-dd");
    static DateTimeFormatter s4 = DateTimeFormatter.ofPattern("HH:mm:ssX");


    @Override
    public Object parse(PGDataType pgDataType, String value) {
        if (null == value) {
            return null;
        }
        switch (pgDataType) {
            case SMALLINT:
            case INTEGER:
                try {
                    return Integer.parseInt(value);
                }catch (NumberFormatException e){
                    log.error("NumberFormatException Integer err value:{}",value);
                }
            case BIGINT:
                try {
                    return Long.parseLong(value);
                }catch (NumberFormatException e){
                    log.error("NumberFormatException Long err value:{}",value);
                }
            case NUMERIC:
            case DOUBLE_PRECISION:
                try {
                    return Double.parseDouble(value);
                }catch (NumberFormatException e){
                    log.error("NumberFormatException Double err value:{}",value);
                }
            case REAL:
                try {
                    return Float.parseFloat(value);
                }catch (NumberFormatException e){
                    log.error("NumberFormatException Float err value:{}",value);
                }
            case CHARACTER:
            case CHARACTER_VARYING:
            case TEXT:
                return value;
            case BOOLEAN:
                return Boolean.parseBoolean(value);
            case TIMESTAMP_WITHOUT_TIME_ZONE:
                try {
                    return s1.parse(value);
                } catch (ParseException|NumberFormatException e) {
                    log.error("parse timestamp without time zone datatype err value:{} pattern:{}",value,s1.toPattern());
                    return null;
                }
            case TIMESTAMP_WITH_TIME_ZONE:
                try {
                    return s2.parse(value);
                } catch (ParseException|NumberFormatException e) {
                    log.error("parse timestamp with time zone datatype err value:{} pattern:{}",value,s2.toPattern());
                    return null;
                }
            case DATE:
                try {
                    return s3.parse(value);
                } catch (ParseException|NumberFormatException e) {
                    log.error("parse date datatype err value:{} pattern:{}",value,s3.toPattern());
                    return null;
                }

            case TIME_WITHOUT_TIME_ZONE:
                try {
                    return LocalTime.parse(value, DateTimeFormatter.ISO_LOCAL_TIME);
                } catch (Exception e) {
                    log.error("parse time without time zone datatype err value:{} pattern:Time ISO_LOCAL_TIME",value);
                    return null;
                }
            case TIME_WITH_TIME_ZONE:
                try {
                    return LocalTime.parse(value,s4);
                } catch (Exception e) {
                    log.error("parse time with time zone datatype err value:{} pattern:Time default",value);
                    return null;
                }

            case INTERVAL:
            case MONEY:
            case POINT:
            case LINE:
            case LSEG:
            case BOX:
            case PATH:
            case POLYGON:
            case CIRCLE:
                return value;
            default:
                return value;
        }
    }
}
