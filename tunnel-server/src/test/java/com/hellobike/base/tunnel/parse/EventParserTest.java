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
package com.hellobike.base.tunnel.parse;

import com.hellobike.base.tunnel.model.ColumnData;
import com.hellobike.base.tunnel.model.InvokeContext;
import com.hellobike.base.tunnel.model.datatype.IParseDataType;
import com.hellobike.base.tunnel.model.datatype.PGDataType;
import com.hellobike.base.tunnel.store.MemStore;
import org.junit.Test;
import org.mockito.Mockito;
import com.hellobike.base.tunnel.model.datatype.ESParse;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author machunxiao 2018-11-07
 */
public class EventParserTest {

    @Test
    public void test_parse() {

        EventParser parser = new EventParser();
        String serverId = "server-id1";
        InvokeContext ctx = new InvokeContext();
        ctx.setSlotName("test_slot");
        ctx.setServerId(serverId);
        ctx.setLsn(1000L);

        String msg = "begin 12345";

        ctx.setMessage(msg);
        parser.parse(ctx);

        msg = "commit 12345";
        ctx.setMessage(msg);
        parser.parse(ctx);

        msg = "table public.test_logic_table: INSERT: pk[integer]:1 name[character varying]:'previous value'";
        ctx.setMessage(msg);
        MemStore mock = Mockito.mock(MemStore.class);
        Mockito.doNothing().when(mock).store(Mockito.any());
        parser.setMemStore(mock);

        parser.parse(ctx);

        msg = "table public.test_logic_table: DELETE: (no-tuple-data)";
        ctx.setMessage(msg);
        parser.parse(ctx);

        msg = "table public.test: UPDATE: id[bigint]:2121 update_on[timestamp without time zone]:'2019-06-18 16:09:23.656' name[character varying]:'' email[character varying]:null";
        ctx.setMessage(msg);
        parser.parse(ctx);
        assertEquals("", ctx.getEvent().getDataList().get(2).getValue());
        assertEquals(null, ctx.getEvent().getDataList().get(3).getValue());

        msg = "table public.test: UPDATE: id[bigint]:2121 update_on[timestamp without time zone]:'2019-06-18 16:09:23.656' email[character varying]:null name[character varying]:''";
        ctx.setMessage(msg);
        parser.parse(ctx);
        assertEquals("", ctx.getEvent().getDataList().get(3).getValue());

        msg = "table public.test: UPDATE: id[bigint]:2121 update_on[timestamp without time zone]:'2019-06-18 16:09:23.656' email[character varying]:null name[character varying]:'''test_name'";
        ctx.setMessage(msg);
        parser.parse(ctx);
        assertEquals("''test_name", ctx.getEvent().getDataList().get(3).getValue());

        msg = "table public.test: UPDATE: id[bigint]:2121 update_on[timestamp without time zone]:'2019-06-18 16:09:23.656' email[character varying]:null name[character varying]:'test_''name'";
        ctx.setMessage(msg);
        parser.parse(ctx);
        assertEquals("test_''name", ctx.getEvent().getDataList().get(3).getValue());

        msg = "table public.test: UPDATE: id[bigint]:2121 update_on[timestamp without time zone]:'2019-06-18 16:09:23.656' email[character varying]:null name[character varying]:'test_name'''";
        ctx.setMessage(msg);
        parser.parse(ctx);
        assertEquals("test_name''", ctx.getEvent().getDataList().get(3).getValue());

        msg = "table public.test: UPDATE: id[bigint]:2121 update_on[timestamp without time zone]:'2019-06-18 16:09:23.656' email[character varying]:null name[character varying]:'''test_name'''";
        ctx.setMessage(msg);
        parser.parse(ctx);
        assertEquals("''test_name''", ctx.getEvent().getDataList().get(3).getValue());

        msg = "table public.test: UPDATE: id[bigint]:2121 update_on[timestamp without time zone]:'2019-06-18 16:09:23.656' email[character varying]:null name[character varying]:'''test''_name'''";
        ctx.setMessage(msg);
        parser.parse(ctx);
        assertEquals("''test''_name''", ctx.getEvent().getDataList().get(3).getValue());

        msg = "table public.test: UPDATE: id[bigint]:2121 update_on[timestamp without time zone]:'2019-06-18 16:09:23.656' email[character varying]:null name[character varying]:'''test'' _name'''";
        ctx.setMessage(msg);
        parser.parse(ctx);
        assertEquals("''test'' _name''", ctx.getEvent().getDataList().get(3).getValue());

        msg = "table public.test: INSERT: id[bigint]:2121 update_on[timestamp without time zone]:'2019-06-18 16:09:23.656' email[character varying]:null created_on[timestamp without time zone]:'2019-05-29 13:59:43.930871'";
        ctx.setMessage(msg);
        parser.parse(ctx);
        assertEquals("2019-05-29 13:59:43.930871", ctx.getEvent().getDataList().get(3).getValue());
        // 全部的数据类型
        msg = "table public.test_pg_datatype: UPDATE: t_1[smallint]:1 t_2[integer]:1 t_3[bigint]:null t_4[numeric]:null t_5[numeric]:null t_6[real]:null t_7[double precision]:null t_8[integer]:1 t_9[bigint]:1 t_10[character]:null t_11[character]:null t_12[character varying]:null t_13[character varying]:null t_14[text]:null t_15[timestamp without time zone]:'2020-01-22 13:40:58' t_16[timestamp with time zone]:'2020-01-22 13:41:06.453+00' t_17[date]:'2020-01-22' t_18[time without time zone]:'11:01:01' t_19[time with time zone]:'11:00:01+00' t_20[interval[]]:null t_21[boolean]:true t_22[money]:null t_23[point]:null t_24[line]:null t_25[lseg]:null t_26[box]:null t_27[path]:null t_28[polygon]:null t_29[circle]:null";
        ctx.setMessage(msg);
        parser.parse(ctx);
        IParseDataType parse = new ESParse();
        List<ColumnData> dataList = ctx.getEvent().getDataList();
        for (ColumnData data : dataList) {
            PGDataType dataType = data.getDataType();
            System.out.println(parse.parse(dataType, data.getValue()));
            System.out.println(data.getDataType() + "\t" + dataType + "\t" + data.getValue());
        }
    }

}
