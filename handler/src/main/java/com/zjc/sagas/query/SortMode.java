package com.zjc.sagas.query;

/**
* 排序
* Created by AutoGenerate on 18-11-26 下午2:54 .
*/
public enum SortMode {

    ASC("ASC"), DESC("DESC");

    private final String mode;

    SortMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }


    public static SortMode getSortMode(String mode) {
        SortMode[] sortModes = values();
        for (SortMode sortMode : sortModes) {
            if (sortMode.getMode().equals(mode)) {
                return sortMode;
            }
        }

        return null;
    }

}

