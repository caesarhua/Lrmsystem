
package com.otn.lrms.util.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 自选座位的过滤条件
 * 
 * @author wWX173427
 * @version [版本号, 2013-5-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class FiltersInfo {

    private List<Buildings> buildings = new ArrayList<Buildings>();

    private List<Rooms> rooms = new ArrayList<Rooms>();

    private int hours = 0;

    private List<String> dates = new ArrayList<String>();

    public static class Buildings {
        private int id;

        private String name;

        private int floor;

        public int getFloor() {
            return floor;
        }

        public void setFloor(int floor) {
            this.floor = floor;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public static class Rooms {
        private int id;

        private String name;

        private int buildId;

        private int floor;

        public int getFloor() {
            return floor;
        }

        public void setFloor(int floor) {
            this.floor = floor;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getBuildId() {
            return buildId;
        }

        public void setBuildId(int buildId) {
            this.buildId = buildId;
        }

    }

    public List<Buildings> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Buildings> buildings) {
        this.buildings = buildings;
    }

    public List<Rooms> getRooms() {
        return rooms;
    }

    public void setRooms(List<Rooms> rooms) {
        this.rooms = rooms;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

}
