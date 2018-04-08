package com.bd.java.db;

import java.util.*;

public class ArrayListLeftJoinImpl {
    public static void main(String[] args) {
        //���������
        DataDto dataDto1 = new DataDto(1L, "1");
        DataDto dataDto3 = new DataDto(3L, "3");
        DataDto dataDto6 = new DataDto(6L, "6");
        DataDto[] dataDtoArray = new DataDto[]{dataDto1, dataDto3, dataDto6};
        List<DataDto> dataDtoList = Arrays.asList(dataDtoArray);
        //����HashMap���ٶ�λԪ�ص�����
        Map<Long, DataDto> dataDtoMap = new HashMap<>();
        for (int i = 0; i < dataDtoList.size(); i++) {
            DataDto dataDto = dataDtoList.get(i);
            dataDtoMap.put(dataDto.getId(), dataDto);
        }
        //�������ұ�
        Long[] idArray = new Long[]{1L, 2L, 3L, 4L, 5L, 6L};
        List<Long> idList = Arrays.asList(idArray);
        //���صĽ����
        List<String> retVal = new ArrayList<>();
        for (Long id : idList) {
            DataDto dataDto = dataDtoMap.get(id);
            if (dataDto == null) {
                //����id���ұ���û�ж�Ӧֵ
                retVal.add("-");
            } else {
                retVal.add(dataDto.getValue());
            }
        }
        System.out.println(retVal);
    }
}
