//package csc.zerofoureightnine.conferencemanager.gateway.sql.entities;
//
//import csc.zerofoureightnine.conferencemanager.datacollection.RuntimeStats;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import java.time.Instant;
//import java.util.EnumMap;
//import java.util.Map;
//
//@Entity
//@Table(name = "runtimeData")
//public class RuntimeData implements Identifiable<String>{
//    @Id
//    private String id;
//
//    @Column(name = "statMap") // TODO: Probably do away with the map and store each stat/enum individually since it works better for non-Integers
//    private EnumMap<RuntimeStats, Object> statMap;
//
//    public RuntimeData() {
//        statMap = new EnumMap<RuntimeStats, Object>(RuntimeStats.class);
//        for (RuntimeStats x: RuntimeStats.values()) { statMap.put(x, 0); }
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id){ this.id = id;}
//
//    public Integer getStatValue(RuntimeStats runtimeStats) { return (Integer) statMap.get(runtimeStats); }
//
//    public void setStatValue(RuntimeStats runtimeStats, Integer num) { statMap.put(runtimeStats, num); }
//
//    public Instant getInstantValue(RuntimeStats runtimeStats) { return (Instant) statMap.get(runtimeStats); }
//
//    /*
//    public void setStatValue(RuntimeStats runtimeStats, Instant time) {
//        statMap.put(runtimeStats, num);
//    }*/
//
//    public Map<RuntimeStats, Object> getStatMap() {
//        return statMap;
//    }
//
//}
