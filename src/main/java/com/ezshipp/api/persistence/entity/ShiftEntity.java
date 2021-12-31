package com.ezshipp.api.persistence.entity;

import java.sql.Time;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "shift",  catalog = "")
@Setter
@Getter
public class ShiftEntity {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "name", length = 50)
    private String name;
    @Column(name = "shift_time_start", nullable = false)
    private Time shiftTimeStart;
    @Column(name = "shift_time_end", nullable = false)
    private Time shiftTimeEnd;
    @Column(name = "is_deleted")
    private boolean deleted;

    @OneToMany(mappedBy = "shiftByShiftId")
    private Collection<DriverDetailEntity> driversById;

}
