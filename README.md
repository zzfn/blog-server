# jello
## sql函数

> `MYOYNN`
 ``` sql
 CREATE OR REPLACE FUNCTION MYOYNN(v_param VARCHAR2) 
 RETURN clob
 IS
   v_value clob;
 BEGIN
   SELECT CONTENT INTO v_value FROM T_ARTICLE WHERE ID = v_param;
 --   dbms_output.put_line(v_value);
   RETURN v_value;
 END;
```
  