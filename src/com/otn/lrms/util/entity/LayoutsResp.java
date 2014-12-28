
package com.otn.lrms.util.entity;

import java.util.ArrayList;
import java.util.List;

public class LayoutsResp {

    private String id;

    private String name;

    private int cols;

    private int rows;

    private Layout layout;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public static class Layout {
        List<LayoutInfo> layoutInfo = new ArrayList<LayoutInfo>();

        public List<LayoutInfo> getLayoutInfo() {
            return layoutInfo;
        }

        public void setLayoutInfo(List<LayoutInfo> layoutInfo) {
            this.layoutInfo = layoutInfo;
        }

        public static class LayoutInfo {

            private String id;

            private String name;

            private String type;

            private String status;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

        }
    }

}
