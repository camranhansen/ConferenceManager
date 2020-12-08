package csc.zerofoureightnine.conferencemanager.gateway.sql.entities;

import csc.zerofoureightnine.conferencemanager.datacollection.RuntimeStats;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.EnumMap;
import java.util.Map;

@Entity
@Table(name = "runtimeData")
public class RuntimeData implements Identifiable<String>{
    @Id
    private String id;

    @Column(name = "statMap")
    private EnumMap<RuntimeStats, Integer> statMap;

    public String getId() {
        return id;
    }

    public void setId(String id){ this.id = id;}

    public int getStatValue(RuntimeStats runtimeStats) { return statMap.get(runtimeStats); }

    public void setStatValue(RuntimeStats runtimeStats, Integer num) { statMap.put(runtimeStats, num); }

    /*
    public void setStatValue(RuntimeStats runtimeStats, Instant time) {
        statMap.put(runtimeStats, num);
    }*/

    public Map<RuntimeStats, Integer> getStatMap() {
        return statMap;
    }

}
