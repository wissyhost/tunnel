package com.hellobike.base.tunnel.model.datatype;

import java.io.Serializable;

public enum PGDataType implements Serializable {
    SMALLINT,
    INTEGER,
    BIGINT,
    NUMERIC,
    REAL,
    DOUBLE_PRECISION,
    CHARACTER,
    CHARACTER_VARYING,
    TEXT,
    TIMESTAMP_WITHOUT_TIME_ZONE,
    TIMESTAMP_WITH_TIME_ZONE,
    DATE,
    TIME_WITHOUT_TIME_ZONE,
    TIME_WITH_TIME_ZONE,
    INTERVAL,
    MONEY,
    POINT,
    LINE,
    LSEG,
    BOX,
    PATH,
    POLYGON,
    CIRCLE,
    BOOLEAN;

    public static PGDataType parse(String dataType) {
        switch (dataType.replace(" ","_").replace("[", "").replace("]", "")) {
            case "smallint":
                return SMALLINT;
            case "integer":
                return INTEGER;
            case "bigint":
                return BIGINT;
            case "numeric":
                return NUMERIC;
            case "real":
                return REAL;
            case "double_precision":
                return DOUBLE_PRECISION;
            case "character":
                return CHARACTER;
            case "character_varying":
                return CHARACTER_VARYING;
            case "text":
                return TEXT;
            case "timestamp_without_time_zone":
                return TIMESTAMP_WITHOUT_TIME_ZONE;
            case "timestamp_with_time_zone":
                return TIMESTAMP_WITH_TIME_ZONE;
            case "date":
                return DATE;
            case "time_without_time_zone":
                return TIME_WITHOUT_TIME_ZONE;
            case "time_with_time_zone":
                return TIME_WITH_TIME_ZONE;
            case "interval":
                return INTERVAL;
            case "money":
                return MONEY;
            case "point":
                return POINT;
            case "line":
                return LINE;
            case "lseg":
                return LSEG;
            case "box":
                return BOX;
            case "path":
                return PATH;
            case "polygon":
                return POLYGON;
            case "circle":
                return CIRCLE;
            case "boolean":
                return BOOLEAN;
            default:
                return TEXT;
        }
    }
}
