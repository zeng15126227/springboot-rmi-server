package com.example.springbootrmiserver.Service.impl;

import com.example.springbootrmiserver.Service.ShellService;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShellServiceImpl implements ShellService {
    @Override
    public String exec(int type, String param, String zh) {
        SimpleDateFormat df = new SimpleDateFormat("YYYYMMdd");
        String date = df.format(new Date());

        List<String> strList = new ArrayList<String>();
        Process process;
        String ret = "";
        if (type == 0) {
            try {
                process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", String.format("sh /data1/ubd_nl/extract/submit_task.sh %s >> /data1/ubd_nl/extract/log/%s_%s.log", param,zh,date)}, null, null);
                Class<?> clazz = Class.forName("java.lang.UNIXProcess");
                Field field = clazz.getDeclaredField("pid");
                field.setAccessible(true);
                ret = String.valueOf(field.get(process));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else if (type == 1) {
            try {
                process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", String.format("ps -aux|grep %s|grep -v grep", param)}, null, null);
                InputStreamReader ir = new InputStreamReader(process.getInputStream());
                LineNumberReader input = new LineNumberReader(ir);
                String line;
                process.waitFor();
                while ((line = input.readLine()) != null) {
                    strList.add(line);
                }
                ret = strList.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else {
            //TODO 参数异常
        }
        return ret;
    }
}
