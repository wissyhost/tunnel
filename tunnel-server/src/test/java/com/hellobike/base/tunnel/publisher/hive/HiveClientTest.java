/*
 * Copyright 2018 Shanghai Junzheng Network Technology Co.,Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain CONFIG_NAME copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hellobike.base.tunnel.publisher.hive;

import com.hellobike.base.tunnel.model.ColumnData;
import com.hellobike.base.tunnel.model.Event;
import com.hellobike.base.tunnel.model.EventType;
import com.hellobike.base.tunnel.model.datatype.PGDataType;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author machunxiao create at 2018-12-03
 */
public class HiveClientTest {

    @Test
    public void test_insert() {
        HiveConfig config = new HiveConfig();
        config.setHiveUrl("jdbc:hive2://10.111.20.161:10000/default;ssl=false;");
        HiveClient hiveClient = new HiveClient(config);
        hiveClient.insert(null, newEvent());
    }

    @Test
    public void test_find() {

        Map<String, String> data = new HashMap<>();

        data.put("k1", "v1");
        data.put("k2", "v2");
        data.put("k3", "v3");

        StringBuffer out = new StringBuffer();
        data.forEach((k, v) -> out.append(k).append("=").append("'").append(v).append("',"));
        out.setCharAt(out.length() - 1, ' ');

        System.out.println(out);
    }

    private Event newEvent() {
        Event e = new Event();
        e.setTable("test1");
        e.setEventType(EventType.INSERT);
        e.setSchema("default");
        e.getDataList().add(new ColumnData("user_id", PGDataType.TEXT, "1001"));
        e.getDataList().add(new ColumnData("device_id", PGDataType.TEXT, "20001"));
        e.getDataList().add(new ColumnData("price", PGDataType.DOUBLE_PRECISION, "20001.0"));
        e.getDataList().add(new ColumnData("sales", PGDataType.INTEGER, "10"));
        return e;
    }

}
