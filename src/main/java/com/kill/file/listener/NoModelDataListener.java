package com.kill.file.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用map读取Excel 文件
 * @author kill
 */
public class NoModelDataListener extends AnalysisEventListener<Map<Integer, String>> {
    private Map<Integer, String> heads = new HashMap<>();
    private final List<Map<String, Object>> finalData = new ArrayList<>();

    public List<Map<String, Object>> getFinalData() {
        return finalData;
    }

    public Map<Integer, String> getHeads() {
        return heads;
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        heads = headMap;
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        Map<String,Object> row = new HashMap<>();
        this.heads.forEach((index, title) -> row.put(title,data.get(index)));
        finalData.add(row);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
    }
}